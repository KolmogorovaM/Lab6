package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.server.base.CLIController;
import ru.marina.client.InputManager;
import ru.marina.model.*;

import java.io.Serializable;
import java.util.Scanner;

/**
 * The Add class represents a command that adds an element to the collection.
 * It implements the Command interface.
 */
public class Add implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs an Add object with the specified CLIController.
     *
     * @param controller the CLIController object
     */
    public Add(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the add command.
     *
     * @param args           the command arguments
     * @param fromExecute    a boolean indicating if the command is executed from the execute method
     * @param executeScanner the scanner used for executing the command
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        Scanner scanner;
        MusicBand musicBand;
        if (fromExecute) {
            scanner = executeScanner;
            InputManager inManager = new InputManager(scanner);
            musicBand = new MusicBand();
            inManager.describeMusicBand(musicBand);
        } else {
            musicBand = controller.getExtraMusicBand();
        }

        controller.addMusicBand(musicBand);
        return new Response(Status.OK,"Element added successfully");
    }

    /**
     * Gets the description of the add command.
     *
     * @return the description of the add command
     */
    @Override
    public String getDescription() {
        return "Adds an element to the collection";
    }
}
