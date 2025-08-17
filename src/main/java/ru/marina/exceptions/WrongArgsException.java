package ru.marina.exceptions;

/**
 * This exception is thrown when incorrect arguments are passed to a method or constructor.
 */
public class WrongArgsException extends Exception {

    /**
     * Constructs a new WrongArgsException with the specified detail message.
     *
     * @param message the detail message
     */
    public WrongArgsException(String message) {
        super(message);
    }
}
