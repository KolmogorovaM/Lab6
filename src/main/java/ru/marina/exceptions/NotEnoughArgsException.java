package ru.marina.exceptions;

/**
 * Thrown when the program is invoked with insufficient command-line arguments.
 */
public class NotEnoughArgsException extends Exception {
    /**
     * Constructs a new {@code NotEnoughArgsException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public NotEnoughArgsException(String message) {
        super(message);
    }

}