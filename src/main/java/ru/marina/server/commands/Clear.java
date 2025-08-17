package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The Clear class represents a command that clears the collection of MusicBands.
 * It implements the Command interface.
 */
public class Clear implements Command, Serializable {

    private final HashSet<MusicBand> collection;

    /**
     * Constructs a Clear object with the specified CLIController.
     * @param controller the CLIController object
     */
    public Clear(CLIController controller) {
        this.collection = controller.getMusicBands();
    }

    /**
     * Executes the Clear command.
     * If the collection is already empty, it prints a message and returns.
     * Otherwise, it clears the collection and prints a message.
     * @param args the command arguments
     * @param fromExecute a boolean indicating if the command is executed from the execute method
     * @param executeScanner the Scanner object used for input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        if (collection.isEmpty()) {
            return new Response(Status.OK, "The collection is already empty.");
        }
        collection.clear();
        return new Response(Status.OK,"The collection has been cleared.");
    }

    /**
     * Gets the description of the Clear command.
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Clears the collection";
    }
}

