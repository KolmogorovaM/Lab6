package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The Info class represents a command that shows information about a collection.
 * It implements the Command interface.
 */
public class Info implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs an Info object with the specified CLIController.
     * @param controller the CLIController object
     */
    public Info(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the Info command with the given arguments.
     * @param args the command arguments
     * @param fromExecute a boolean indicating if the command is executed from the execute method
     * @param executeScanner the Scanner object used for input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        HashSet<MusicBand> musicBands = controller.getMusicBands();

        String result = "";
        result += "\tInformation about collection:" + System.lineSeparator();
        result += "Created at " + controller.getCreationDate() + System.lineSeparator();
        result += "Collection type is " + musicBands.getClass().getSimpleName() + System.lineSeparator();
        result += "Amount of items stored in - " + musicBands.size();

        return new Response(Status.OK, result);
    }

    /**
     * Returns a description of the Info command.
     * @return the description of the command
     */
    @Override
    public String getDescription(){
        return "Shows info about collection";
    }
}

