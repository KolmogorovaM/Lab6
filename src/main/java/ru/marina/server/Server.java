package ru.marina.server;

import ru.marina.base.Request;
import ru.marina.base.Response;
import ru.marina.exceptions.NoFileException;
import ru.marina.model.MusicBand;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.server.base.CommandExecutor;
import ru.marina.server.commands.Command;
import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.*;
import java.util.logging.Logger;

public class Server {
    private final InetSocketAddress address;
    private final CLIController controller;
    private final CommandExecutor commandExecutor;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private volatile boolean running = true;


    public Server(InetSocketAddress address, String filename) {
        this.address = address;
        this.controller = new CLIController(new String[]{filename});
        this.commandExecutor = new CommandExecutor(controller);

        new Thread(this::handleServerCommands).start();

        loadCollection();
    }

    public void initialize() {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            channel.bind(address);
            System.out.println("Server started on port " + address.getPort());
            System.out.print("server> ");
            while (running) {
                try (Socket client = channel.accept().socket();
                     ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {

                    Request request = (Request) in.readObject();
                    Response response = executeCommand(request);
                    out.writeObject(response);

                } catch (Exception e) {
                    logger.warning("Client handling error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.warning("Server exception: " + e.getMessage());
        } finally {
            saveCollection();
        }
    }

    private void loadCollection() {
        try {
            controller.loadCollection();
        } catch (IOException e) {
            logger.warning("Server I/O exception: " + e.getMessage());
        } catch (NoFileException e) {
            logger.warning("File error: " + e.getMessage());
        }
    }

    private Response executeCommand(Request request) {
        try {
            String[] args = request.getInput();
            String command = args[0];
            switch (command) {
                case "add":
                case "add_if_max":
                case "add_if_min":
                case "update":
                    MusicBand musicBand = request.getMusicBand();
                    controller.setExtraMusicBand(musicBand);
            }
            return commandExecutor.handleCommand(args);
        } catch (Exception e) {
            return new Response(Status.REQUEST_ERROR,"Error executing command: " + e.getMessage());
        }
    }

    private void handleServerCommands() {
        try (Scanner scanner = new Scanner(System.in)) {
            logger.info("Server command console started");
            while (running) {
                System.out.print("server> ");
                String input = scanner.nextLine().trim();
                if ("save".equalsIgnoreCase(input)) {
                    saveCollection();
                    logger.info("Collection saved");
                } else if ("exit".equalsIgnoreCase(input)) {
                    running = false;
                    logger.info("Server shutting down...");
                }
            }
        }
    }

    private void saveCollection() {
        try {
            Command saveCommand = commandExecutor.getCommand("save");
            saveCommand.execute(new String[]{}, false, null);
        } catch (Exception e) {
            logger.warning("Error saving collection: " + e.getMessage());
        }
    }
}