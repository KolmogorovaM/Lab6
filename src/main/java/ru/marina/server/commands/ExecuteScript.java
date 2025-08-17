
package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CommandExecutor;
import ru.marina.exceptions.ExitException;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Represents a command that executes a script file.
 * The script file contains a series of commands to be executed line by line.
 * This class implements the Command interface.
 */
public class ExecuteScript implements Command, Serializable {
    private final CommandExecutor commandExecutor;
    private static final ArrayList<Integer> recursionHistory = new ArrayList<>();


    public ExecuteScript(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public Response execute(String[] args,
                            boolean fromExecute,
                            Scanner executeScanner) throws WrongArgsException,
                                                       NotEnoughArgsException,
                                                       IOException
    {
        if (args.length < 2) {
            throw new NotEnoughArgsException("Command requires \"path\" argument");
        }
        Path path = Paths.get(args[1]);
        recursionHistory.add(args[1].hashCode());
        String scriptFileName = args[1];

        try {
            // check file permissions
            if (!Files.exists(path)) {
                throw new FileNotFoundException("File " + path + " not found");
            }
            if (!Files.isReadable(path)) {
                throw new NoPermissionException("Cannot read file.");
            }
            if (!Files.isWritable(path)) {
                throw new NoPermissionException("Cannot write to file.");
            }
        } catch (FileNotFoundException e) {
            return new Response(Status.REQUEST_ERROR, "File " + path + " not found.");
        } catch (NoPermissionException e) {
            return new Response(
                    Status.REQUEST_ERROR,
                    "No enough permissions to " + path + " - " + e.getMessage()
            );
        }

        try (Scanner scanner = new Scanner(new FileInputStream(scriptFileName))) {
            System.out.println("Running " + path);
            runThrough(scanner);
            recursionHistory.clear();
        } catch (IOException e) {
            throw new IOException("Failed to deserialize the priority queue from file: " + scriptFileName, e);
        }
        return new Response(Status.OK, "Script has been executed.");
    }

    private void runThrough(Scanner scanner) throws IOException {
        while (scanner.hasNextLine()) {
            String currLine = scanner.nextLine();
            if (currLine == null) {
                return;
            }
            String[] args = currLine.trim()
                                    .toLowerCase()
                                    .split(" ");

            Command command = commandExecutor.getCommand(args[0]);

            if (command == null) {
                System.out.println(args[0] + " is not a command. Try again.");
                continue;
            }

            try {
                if (command.getClass() == ExecuteScript.class) {
                    if (ExecuteScript.recursionHistory.contains(args[1].hashCode())) {
                        System.out.println("Recursion! Command skipped!");
                        return;
                    }
                    ExecuteScript.recursionHistory.add(args[0].hashCode());
                }
                command.execute(args, true, scanner);


            } catch (NotEnoughArgsException |  NoPermissionException | WrongArgsException | IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (ExitException e) {
                break;
            }
        }
    }

    @Override
    public String getDescription() {
        return "Runs commands from a file, line by line";
    }

}
