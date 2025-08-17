/**
 * A command that saves a priority queue of flats to a file using Serializer class.
 */
package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;
import ru.marina.server.base.CLIController;
import ru.marina.server.base.FileValidator;
import ru.marina.base.Serializer;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Save implements Command, Serializable {

    private final CLIController controller;

    /**
     * Constructs a Save command with the given CLIController.
     *
     * @param controller an object that holds every argument needed
     */
    public Save(CLIController controller) {
        this.controller = controller;
    }

    /**
     * Executes the Save command by serializing the priority queue of flats to a file.
     * If there is an error writing to the file, the user will be prompted to enter a new file name.
     *
     * @param args           the arguments to execute the command with
     * @param fromExecute    a flag indicating whether the command is executed from the main execute loop
     * @param executeScanner a Scanner object for reading user input during execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        while (true) {
            try {
                FileValidator.checkFile(controller.getFileName());
                Serializer.serialize(controller.getMusicBands(), controller.getFileName());
                return new Response(Status.OK, "Successfully saved collection to a file!");
            } catch (NoPermissionException | IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                System.out.print("Enter a new file name: ");
                controller.setFileName(controller.getScanner().nextLine());
            }
        }
    }

    /**
     * Gets the description of the Save command.
     *
     * @return the description of the command
     */
    @Override
    public String getDescription() {
        return "Saves collection to a file";
    }
}