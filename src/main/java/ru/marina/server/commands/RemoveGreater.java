
package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import java.io.Serializable;
import java.util.Scanner;

/**
 * The RemoveGreater class implements the Command interface and represents a command
 * that removes all elements greater than the one given.
 */
public class RemoveGreater implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs a RemoveGreater object with the specified CLIController.
     *
     * @param controller the CLIController object to be used
     */
    public RemoveGreater(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the RemoveGreater command with the given arguments.
     *
     * @param args           the arguments for the command
     * @param fromExecute    a boolean indicating whether the command is executed from the execute method
     * @param executeScanner the Scanner object used for input during execution
     * @throws NotEnoughArgsException if there are not enough arguments provided
     * @throws WrongArgsException     if the arguments provided are invalid
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
        MusicBand mainMusicBand;
        try {
            mainMusicBand = controller.getMusicBandByID(Long.parseLong(args[1]));
        } catch (IllegalArgumentException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        controller.getMusicBands()
                  .removeIf(flat -> mainMusicBand.compareTo(flat) > 0);

        return new Response(Status.OK,"Elements removed successfully.");
    }

    /**
     * Returns the description of the RemoveGreater command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Removes all elements greater than the one given";
    }
}

