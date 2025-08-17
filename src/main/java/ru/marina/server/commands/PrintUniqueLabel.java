package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.model.MusicBand;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

/**
 * The PrintUniqueLabel class is a command that prints the unique values of the house field
 * for all elements in the collection.
 */
public class PrintUniqueLabel implements Command, Serializable {
    private final CLIController controller;

    /**
     * Constructs a PrintUniqueLabel object with the specified CLIController.
     *
     * @param controller the CLIController object to be used
     */
    public PrintUniqueLabel(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the PrintUniqueLabel command.
     *
     * @param args           the command arguments
     * @param fromExecute    a boolean indicating whether the command is executed from the execute method
     * @param executeScanner the Scanner object used for user input during execution
     * @throws NoPermissionException if the user does not have permission to execute the command
     * @throws IOException           if an I/O error occurs
     */
    @Override
    public Response execute(String[] args,
                            boolean fromExecute,
                            Scanner executeScanner) throws NoPermissionException,
                                                       IOException
    {
        StringBuilder sb = new StringBuilder();
        HashSet<MusicBand> musicBands = controller.getMusicBands();
        HashSet<String> uniqueHouses = new HashSet<>();
        for (MusicBand musicBand : musicBands) {
            uniqueHouses.add(musicBand.getLabel().toString());
        }

        sb.append("Unique label values:").append(System.lineSeparator());
        for (String house : uniqueHouses) {
            sb.append(house).append(System.lineSeparator());
        }
        return new Response(Status.OK, sb.toString());
    }

    /**
     * Returns the description of the PrintUniqueLabel command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Prints unique values of the house field for all elements in the collection.";
    }
}
