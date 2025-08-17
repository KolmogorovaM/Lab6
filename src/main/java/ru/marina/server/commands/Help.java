package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.model.Status;

import java.io.Serializable;
import java.util.*;

public class Help implements Command, Serializable {
    private final HashMap<String, Command> commands;

    public Help(HashMap<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Executes the Help command.
     *
     * @param args           the command arguments
     * @param fromExecute    indicates whether the command is executed from the main execute method
     * @param executeScanner the scanner used for user input during command execution
     */
    @Override
    public Response execute(String[] args, boolean fromExecute, Scanner executeScanner) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%-35s   %-90s%n", "COMMAND", "DESCRIPTION"));
        result.append("--------------------------------------------------------------------------\n");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            String commandName = entry.getKey();
            String description = entry.getValue().getDescription();
            result.append(String.format("%-35s   %-90s%n", commandName, description));
        }
        return new Response(Status.OK, result.toString());
    }
    @Override
    public String getDescription() {
        return "Shows all command descriptions";
    }
}
