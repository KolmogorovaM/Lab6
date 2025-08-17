package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.client.InputManager;
import ru.marina.model.MusicBand;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

/**
 * Represents a command that adds an element to the collection if it is smaller than the smallest element of the sorted array.
 */
public class AddIfMin implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs an AddIfMin object with the specified CLIController.
     *
     * @param controller the CLIController object
     */
    public AddIfMin(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Gets the description of the command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Adds element if it is smaller than the smallest element of sorted array";
    }

    /**
     * Executes the command with the specified arguments.
     *
     * @param args           the command arguments
     * @param fromExecute    true if the command is executed from the execute method, false otherwise
     * @param executeScanner the scanner to use for input if the command is executed from the execute method
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

        Optional<MusicBand> maxBand = controller.getMusicBands()
                .stream()
                .min(Comparator.comparingInt(MusicBand::getNumberOfParticipants));

        boolean addable = maxBand.map(
                band -> musicBand.getNumberOfParticipants() < band.getNumberOfParticipants()
        ).orElse(true);
        if (addable) {
            controller.addMusicBand(musicBand);
            return new Response(Status.OK, "Object was added to the collection");
        } else {
            return new Response(Status.OK, "Object was not added to the collection. It is not the minimum");
        }
    }
}