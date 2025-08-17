package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A command that calculates and displays the sum of the number of participants in all music bands.
 */
public class SumOfNumberOfParticipants implements Command, Serializable {

    private final HashSet<MusicBand> collection;

    /**
     * Constructs a SumOfNumberOfParticipants command with the specified CLIController.
     *
     * @param controller the CLIController object that manages the collection of music bands
     */
    public SumOfNumberOfParticipants(CLIController controller) {
        this.collection = controller.getMusicBands();
    }

    /**
     * Executes the SumOfNumberOfParticipants command.
     *
     * @param args           the command arguments
     * @param fromExecute    a flag indicating whether the command is executed from the execute method
     * @param executeScanner the Scanner object used for user input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        int sum = collection.stream()
                            .mapToInt(MusicBand::getNumberOfParticipants)
                            .sum();

        return new Response(Status.OK,"The sum of the number of participants in all bands is " + sum);
    }

    /**
     * Gets the description of the SumOfNumberOfParticipants command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Shows the sum of the number of participants in all bands";
    }
}
