package ru.marina.exceptions;

/**
 * The ExitException class represents an exception that is thrown when the program
 * needs to exit safely.
 */
public class ExitException extends RuntimeException {

    /**
     * Constructs a new ExitException with a default error message.
     */
    public ExitException() {
        super("Program has safely exited.");
    }
}
