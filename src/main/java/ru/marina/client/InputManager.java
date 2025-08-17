package ru.marina.client;

import ru.marina.model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The InputManager class is responsible for handling user input and populating a MusicBand object with the entered information.
 */
public class InputManager {
    private final Scanner scanner;
    
    /**
     * Constructs an InputManager object with the specified Scanner.
     * @param scanner the Scanner object to be used for input
     */
    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    /**
     * Prompts the user to enter information about a MusicBand object and sets the corresponding fields of the provided MusicBand object.
     * @param musicBand the MusicBand object to be populated with the entered information
     */
    public void describeMusicBand(MusicBand musicBand) {
        System.out.println("Please enter the following information:");
        System.out.print("Name: ");
        musicBand.setName(readNonEmptyString());

        System.out.print("X coordinate (less than or equal to 314): ");
        int x = readCoordinate();

        System.out.print("Y coordinate (less than or equal to 314): ");
        int y = readCoordinate();

        musicBand.setCoordinates(new Coordinates(x, y));

        System.out.print("Number of participants: ");
        musicBand.setNumberOfParticipants(readPositiveInt());

        System.out.print("Albums count: ");
        musicBand.setAlbumsCount(readPositiveInt());

        System.out.print("Genre (BLUES, POP, POST_PUNK) or (1, 2, 3): ");
        musicBand.setGenre(readMusicGenre());

        System.out.print("Label name (or enter nothing): ");
        String name = readNullableString();

        System.out.print("Bands (or enter nothing): ");
        Long bands = readNullableLong();

        System.out.print("Sales (greater than 0): ");
        double sales = readPositiveDouble();

        musicBand.setLabel(new Label(name, bands, sales));

    }

    /**
     * Reads a non-empty string from the user.
     * @return the non-empty string entered by the user
     */
    private String readNonEmptyString() {
        String input = this.scanner.nextLine().trim();
        while (input.isEmpty() | input.contains(",")) {
            System.out.print("Please enter a name (it shouldn't contain commas): ");
            input = this.scanner.nextLine().trim();
        }
        return input;
    }

    /**
     * Reads a coordinate value from the user.
     * @return the coordinate value entered by the user
     */
    private int readCoordinate() {
        int coordinate = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                coordinate = this.scanner.nextInt();
                if (coordinate > 314) {
                    System.out.print("Coordinate can't be greater than 314. Please enter again: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid integer: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return coordinate;
    }

    /**
     * Reads a positive double value from the user.
     * @return the positive double value entered by the user
     */
    private double readPositiveDouble() {
        double value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = this.scanner.nextDouble();
                if (value <= 0) {
                    System.out.print("Please enter a positive double: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid double: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }

    /**
     * Reads a nullable long value from the user.
     * @return the nullable long value entered by the user, or null if no value was entered
     */
    private Long readNullableLong() {
        Long value = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                String input = this.scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    value = Long.parseLong(input);
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid long: ");
            }
        }
        return value;
    }

    /**
     * Reads a MusicGenre value from the user.
     * @return the MusicGenre value entered by the user
     */
    private MusicGenre readMusicGenre() {
        String input = this.scanner.nextLine().toUpperCase().trim();
        MusicGenre musicGenre = null;
        boolean validInput = false;
        while (!validInput) {
            try {
                if (input.matches("\\d+")) { // check if input is a number
                    int index = Integer.parseInt(input);
                    try {
                        musicGenre = MusicGenre.values()[index - 1]; // use the index to get the enum value
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    musicGenre = Enum.valueOf(MusicGenre.class, input); // use the enum name directly
                }
                validInput = true;
            } catch (IllegalArgumentException e) {
                System.out.print("Please enter correct genre (BLUES, POP, POST_PUNK) or (1, 2, 3): ");
                input = this.scanner.nextLine().toUpperCase().trim();
            }
        }
        return musicGenre;
    }

    /**
     * Reads a nullable string from the user.
     * @return the nullable string entered by the user, or null if no value was entered
     */
    private String readNullableString() {
        String input = this.scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        while (input.contains(",")) {
            System.out.print("Please enter a name (it shouldn't contain commas): ");
            input = this.scanner.nextLine().trim();
        }
        return input;
    }

    /**
     * Reads a positive integer value from the user.
     * @return the positive integer value entered by the user
     */
    private int readPositiveInt() {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                value = this.scanner.nextInt();
                if (value <= 0) {
                    System.out.print("You have to enter a positive integer. Please enter again: ");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid positive integer: ");
                this.scanner.nextLine();
            }
        }
        this.scanner.nextLine(); // consume the newline character
        return value;
    }
}
