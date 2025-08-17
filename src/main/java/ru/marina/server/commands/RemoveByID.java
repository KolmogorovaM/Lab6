/**
 * The RemoveByID class is a command implementation that removes an element from the CLIController
 * by its specified ID.
 */
package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import java.io.Serializable;
import java.util.Scanner;

/**
 * The RemoveByID class is a command implementation that removes an element from the CLIController
 * by its specified ID.
 */
public class RemoveByID implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs a RemoveByID object with the specified CLIController.
     *
     * @param controller the CLIController object to be used for removing elements
     */
    public RemoveByID(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the remove by ID command.
     *
     * @param args           the command arguments
     * @param fromExecute    a flag indicating if the command is executed from the execute method
     * @param executeScanner the scanner object used for input during execution
     * @throws NotEnoughArgsException if the ID argument is missing
     * @throws WrongArgsException    if the ID argument is not a valid UUID
     */
    @Override
    public Response execute(String[] args,
                            boolean fromExecute,
                            Scanner executeScanner) throws NotEnoughArgsException,
                                                       WrongArgsException
    {
        if (args.length < 2) {
            throw new NotEnoughArgsException("ID is required");
        }
        try {
            controller.removeMusicBandByID(Long.parseLong(args[1]));
            return new Response(Status.OK, "Removing successful");
        } catch (NumberFormatException e) {
            throw new WrongArgsException("You need to supply an ID, which is a Long");
        }
    }

    /**
     * Gets the description of the remove by ID command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Removes element with ID specified";
    }
}