package ru.marina.server.base;

import ru.marina.base.Response;
import ru.marina.server.commands.*;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class represents a command executor that is used to execute different commands
 */
public class CommandExecutor {
    HashMap<String, Command> commands = new HashMap<>();
    CLIController controller;

    /**
     * Constructs a new instance of the CommandExecutor
     * @param controller the command line interface controller to be used to execute the commands
     */
    public CommandExecutor(CLIController controller) {
        this.controller = controller;
        commands.put("help", new Help(commands));
        commands.put("info", new Info(controller));
        commands.put("show", new Show(controller));
        commands.put("add", new Add(controller));
        commands.put("update", new Update(controller));
        commands.put("remove_by_id", new RemoveByID(controller));
        commands.put("clear", new Clear(controller));
        commands.put("save", new Save(controller));
        commands.put("execute_script", new ExecuteScript(this));
        commands.put("exit", new Exit());
        commands.put("add_if_max", new AddIfMax(controller));
        commands.put("add_if_min", new AddIfMin(controller));
        commands.put("remove_greater", new RemoveGreater(controller));
        commands.put("sum_of_number_of_participants", new SumOfNumberOfParticipants(controller));
        commands.put("average_of_number_of_participants", new AverageOfNumberOfParticipants(controller));
        commands.put("print_unique_label", new PrintUniqueLabel(controller));
    }

    /**
     * Starts the interactive mode for the user to input commands
     */
    public Response handleCommand(String[] line) throws NotEnoughArgsException,
                                                        NoPermissionException,
                                                        WrongArgsException,
                                                        IOException
    {
        return commands.get(line[0]).execute(line, false, null);

    }

    /**
     * Returns the command object for the given command name
     * @param commandName the name of the command to get
     * @return the command object for the given command name
     */
    public Command getCommand(String commandName) {
        if(!commands.containsKey(commandName)) return null; // check if command exist
        return commands.get(commandName);
    }
}
