package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.MusicBand;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The Show class is a command implementation that displays all collection items.
 */
public class Show implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs a Show object with the specified CLIController.
     *
     * @param controller the CLIController object
     */
    public Show(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the Show command.
     *
     * @param args           the command arguments
     * @param fromExecute    a boolean indicating if the command is executed from the execute method
     * @param executeScanner the Scanner object used for input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        return new Response(
                Status.OK,
                String.valueOf(controller.getMusicBands()
                                         .stream()
                                         .sorted(Comparator.comparingInt(MusicBand::getNumberOfParticipants))
                                         .collect(Collectors.toList()))
        );
    }

    /**
     * Gets the description of the Show command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Shows all collection items";
    }
}