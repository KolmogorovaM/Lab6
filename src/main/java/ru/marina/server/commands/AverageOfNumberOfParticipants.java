package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Represents a command that calculates and displays the average number of participants in all music bands.
 */
public class AverageOfNumberOfParticipants implements Command, Serializable {

    private final HashSet<MusicBand> collection;

    /**
     * Constructs a new AverageOfNumberOfParticipants command with the specified CLIController.
     *
     * @param controller the CLIController instance
     */
    public AverageOfNumberOfParticipants(CLIController controller) {
        this.collection = controller.getMusicBands();
    }

    /**
     * Executes the AverageOfNumberOfParticipants command.
     *
     * @param args           the command arguments
     * @param fromExecute    true if the command is executed from the execute method, false otherwise
     * @param executeScanner the Scanner instance for reading user input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        double average = collection.stream()
                                   .mapToInt(MusicBand::getNumberOfParticipants)
                                   .average()
                                   .orElse(0);

        return new Response(Status.OK,"The average of the number of participants in all bands is " + average);
    }

    /**
     * Gets the description of the AverageOfNumberOfParticipants command.
     *
     * @return the command description
     */
    @Override
    public String getDescription() {
        return "Shows the average of the number of participants in all bands";
    }
}
