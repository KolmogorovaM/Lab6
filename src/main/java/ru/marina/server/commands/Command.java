package ru.marina.server.commands;

import ru.marina.base.Response;
import ru.marina.exceptions.ExitException;
import ru.marina.exceptions.NotEnoughArgsException;
import ru.marina.exceptions.WrongArgsException;

import javax.naming.NoPermissionException;
import java.io.IOException;
import java.util.Scanner;

/**
* The Command interface represents a command that can be executed.
*/
public interface Command {
    /**
    * Executes the command with the given arguments.
    *
    * @param args           the arguments for the command
    * @param fromExecute    indicates whether the command is executed from the execute method
    * @param executeScanner the scanner used for input during execution
    * @throws NoPermissionException    if the command requires special permissions
    * @throws IOException              if an I/O error occurs during execution
    * @throws WrongArgsException       if the command is executed with wrong arguments
    * @throws NotEnoughArgsException   if there are not enough arguments for the command
    * @throws ExitException            if the command is an exit command
    */
    Response execute(String[] args,
                     boolean fromExecute,
                     Scanner executeScanner) throws NoPermissionException,
                                                IOException,
                                                WrongArgsException,
                                                NotEnoughArgsException,
                                                ExitException;

    /**
    * Gets the description of the command.
    *
    * @return the description of the command
    */
    String getDescription();
}
