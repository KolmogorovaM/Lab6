package ru.marina.client;

import ru.marina.base.Request;
import ru.marina.base.Response;
import ru.marina.exceptions.NoSuchCommandException;
import ru.marina.model.MusicBand;
import ru.marina.model.Status;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Logger;

public class Client {
    private final InetAddress HOST;
    private final int PORT;
    private final Scanner SCANNER = new Scanner(System.in);
    private final InputManager inputManager = new InputManager(SCANNER);
    private static final int RETRY_DELAY = 5000;
    private static final int BUFFER_SIZE = 4096;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int RESPONSE_TIMEOUT = 10000;
    private final String[] COMMANDS = new String[]{
            "help",
            "info",
            "show",
            "add",
            "update",
            "remove_by_id",
            "clear",
            "execute_script",
            "exit",
            "add_if_max",
            "add_if_min",
            "remove_greater",
            "sum_of_number_of_participants",
            "average_of_number_of_participants",
            "print_unique_label",
    };

    private final Logger logger = Logger.getLogger(Client.class.getName());
    private SocketChannel channel;
    private Selector selector;
    private final int MAX_RECONNECTION_ATTEMPTS = 3;

    public Client(InetAddress host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public void launch() {
        try {
            selector = Selector.open();

            while (true) {
                try {
                    System.out.print("\u001B[36mEnter command: \u001B[0m");
                    String input = SCANNER.nextLine().trim();

                    if (input.equalsIgnoreCase("exit")) {
                        logger.info("Closing connection with server...");
                        closeConnection();
                        logger.info("Shutting down the application");
                        break;
                    }

                    if (input.isEmpty()) {
                        continue;
                    }
                    String[] args = input.split("\\s+");
                    Request request;
                    String command = args[0];
                    if (!Arrays.asList(COMMANDS).contains(command)) {
                        throw new NoSuchCommandException("Unknown command: " + command);
                    } else if (command.equals("add")
                            || command.equals("add_if_max")
                            || command.equals("add_if_min")
                            || command.equals("update")) {
                        MusicBand musicBand = new MusicBand();
                        inputManager.describeMusicBand(musicBand);
                        request = new Request(args, musicBand);
                    } else {
                        request = new Request(args);
                    }

                    for (int attempt = 0; attempt < MAX_RECONNECTION_ATTEMPTS; attempt++) {
                        try {
                            if (!isConnected()) {
                                connectToServer();
                            }

                            sendRequest(request);
                            Response response = receiveResponse();
                            if (response.getStatus() == Status.OK) {
                                logger.info("Status " + response.getStatus());
                                System.out.println(response.getMessage());
                            } else if (response.getStatus() == Status.CONNECTION_ERROR) {
                                throw new ConnectException();
                            } else {
                                logger.warning(String.valueOf(response.getMessage()));
                            }
                            break;
                        } catch (ConnectException e) {
                            logger.warning("Connection failed: " + e.getMessage());
                            if (attempt < MAX_RECONNECTION_ATTEMPTS - 1) {
                                logger.info("Retrying in " + (RETRY_DELAY/1000) + " second(s)...");
                                Thread.sleep(RETRY_DELAY);
                            }
                        } catch (Exception e) {
                            logger.warning("Error: " + e.getMessage());
                            closeConnection();
                            break;
                        }
                    }
                } catch (NoSuchCommandException e) {
                    logger.warning(e.getMessage());
                    logger.info("Type \"help\" to see the list of commands");
                }
            }
        } catch (NoSuchCommandException e) {
            logger.warning(e.getMessage());
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            closeConnection();
            closeSelector();
        }
    }

    private boolean isConnected() {
        return channel != null && channel.isConnected();
    }

    private void connectToServer() throws IOException {
        logger.info("Connecting to server...");
        channel = SocketChannel.open();
        logger.info("SocketChannel is now open...");
        channel.configureBlocking(false);
        logger.info("The channel has started working in non-blocking mode");
        channel.connect(new InetSocketAddress(HOST, PORT));
        logger.info("Selector is now open");
        channel.register(selector, SelectionKey.OP_CONNECT);

        long startTime = System.currentTimeMillis();
        while (true) {
            if (selector.select(CONNECT_TIMEOUT) > 0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (key.isConnectable()) {
                        if (channel.finishConnect()) {
                            channel.register(selector, SelectionKey.OP_READ);
                            return;
                        }
                    }
                }
            }

            if (System.currentTimeMillis() - startTime > CONNECT_TIMEOUT) {
                throw new SocketTimeoutException("Connection timeout");
            }
        }
    }

    private void sendRequest(Request request) throws IOException {
        logger.info("Sending request...");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(request);
        }

        ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private Response receiveResponse() throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        logger.info("Receiving response...");
        long startTime = System.currentTimeMillis();
        while (true) {
            int ready = selector.select(RESPONSE_TIMEOUT);
            if (ready == 0) {
                if (System.currentTimeMillis() - startTime > RESPONSE_TIMEOUT) {
                    throw new SocketTimeoutException("Response timeout");
                }
                continue;
            }

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isReadable()) {
                    int bytesRead = channel.read(buffer);
                    if (bytesRead == -1) {
                        throw new EOFException("Connection closed by server");
                    }

                    if (bytesRead > 0) {
                        buffer.flip();
                        baos.write(buffer.array(), 0, buffer.limit());
                        buffer.clear();
                    }

                    if (baos.size() > 0) {
                        try {
                            return (Response) deserializeResponse(baos.toByteArray());
                        } catch (EOFException | StreamCorruptedException e) {
                            // Не все данные получены, продолжаем чтение
                        }
                    }
                }
            }

            if (System.currentTimeMillis() - startTime > RESPONSE_TIMEOUT) {
                throw new SocketTimeoutException("Response timeout");
            }
        }
    }

    private Object deserializeResponse(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return ois.readObject();
        }
    }


    private void closeConnection() {
        logger.info("Closing connection...");
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                logger.warning("Error closing connection: " + e.getMessage());
            }
            channel = null;
        }
    }

    private void closeSelector() {
        logger.info("Closing selector...");
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                System.err.println("Error closing selector: " + e.getMessage());
            }
        }
    }
}