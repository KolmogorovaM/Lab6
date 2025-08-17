package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.exceptions.ExitException;
import ru.marina.model.Status;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * The Exit class represents a command that allows the user to exit the program without saving the changes to the file.
 */
public class Exit implements Command, Serializable {
    /**
     * Executes the exit command.
     *
     * @param args           the command arguments
     * @param fromExecute    indicates whether the command is executed from the main execute method
     * @param executeScanner the scanner used for user input during execution
     * @throws IOException    if an I/O error occurs
     * @throws ExitException  if the exit command is thrown to terminate the program
     */
    @Override
    public Response execute(String[] args,
                            boolean fromExecute,
                            Scanner executeScanner) throws IOException,
                                                       ExitException
    {
        return new Response(Status.OK, "Shutting the application...");
    }

    /**
     * Gets the description of the exit command.
     *
     * @return the description of the exit command
     */
    @Override
    public String getDescription() {
        return "Exits the program without saving the changes to the file.";
    }
}
