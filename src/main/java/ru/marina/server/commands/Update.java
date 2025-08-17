package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.client.InputManager;
import ru.marina.model.MusicBand;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import java.io.Serializable;
import java.util.Scanner;

/**
 * The Update class represents a command that updates an element with the specified ID.
 * It implements the Command interface.
 */
public class Update implements Command, Serializable {

    private final CLIController controller;

    /**
     * Constructs an Update object with the specified CLIController.
     *
     * @param controller the CLIController object to be used for updating elements
     */
    public Update(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the update command with the given arguments.
     *
     * @param args           the arguments for the update command
     * @param fromExecute    a boolean indicating whether the command is executed from the execute method
     * @param executeScanner the scanner object to be used for input if executed from the execute method
     * @throws WrongArgsException     if the arguments are invalid
     * @throws NotEnoughArgsException if there are not enough arguments
     */
    @Override
    public Response execute(String[] args,
                            boolean fromExecute,
                            Scanner executeScanner) throws WrongArgsException,
                                                       NotEnoughArgsException
    {
        if (args.length < 2) {
            throw new NotEnoughArgsException("ID is required");
        }
        MusicBand musicBandById;
        try {
            musicBandById = controller.getMusicBandByID(Long.parseLong(args[1]));
        } catch (NumberFormatException e) {
            throw new WrongArgsException("You need to supply an ID, which is an UUID");
        }

        Scanner scanner;
        if (fromExecute) {
            scanner = executeScanner;
            InputManager inManager = new InputManager(scanner);
            inManager.describeMusicBand(musicBandById);
        } else {
            MusicBand musicBand = controller.getExtraMusicBand();
            controller.setMusicBandById(Long.parseLong(args[1]), musicBand);
        }
        return new Response(Status.OK, "Object by ID has been updated");
    }

    /**
     * Returns the description of the update command.
     *
     * @return the description of the update command
     */
    @Override
    public String getDescription() {
        return "Updates element with ID specified";
    }
}
