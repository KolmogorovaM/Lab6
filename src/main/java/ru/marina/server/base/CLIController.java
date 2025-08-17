package ru.marina.server.base;

import ru.marina.base.Deserializer;
import ru.marina.exceptions.NoFileException;
import ru.marina.model.MusicBand;
import ru.marina.exceptions.WrongArgsException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * The CLIController class represents a controller for a command-line interface that manages a collection of flats.
 * It stores the name of the file containing the flats, a priority queue of flats, a scanner for user input, and the creation date of the flats.
 */
public class CLIController {

    private String fileName;
    private MusicBand musicBand;
    private HashSet<MusicBand> musicBands;
    private final Scanner SCANNER;
    private Date creationDate;
    private final String[] args;


    /**
     * Constructs a CLIController object with the given command-line arguments.
     */
    public CLIController(String[] args) {
        this.SCANNER = new Scanner(System.in);
        this.args = args;
    }

    /**
     * Loads the collection from file.
     *
     * @throws IOException if there was an error reading the file
     * @throws NoFileException if the file is not provided
     */
    public void loadCollection() throws IOException, NoFileException {
        if (args.length < 1) {
            throw new NoFileException("No file name provided.");
        } else {
            fileName = args[0];
        }

        // Check if file exists and has write access
        while (true) {
            try {
                FileValidator.checkFile(fileName);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: You don't have permission to access the file or it doesn't exist.");
                System.out.print("Please enter another file name: ");
                fileName = SCANNER.nextLine();
                FileValidator.checkFile(this.fileName);
            }
        }

        // Deserialize the file and store the data in a priority queue
        while (true) {
            try {
                musicBands = Deserializer.deserialize(fileName);
                break;
            } catch (IOException e) {
                System.out.println("Error: Unable to read the file.");
                System.out.print("Please enter another file name: ");
                fileName = SCANNER.nextLine();
                musicBands = Deserializer.deserialize(fileName);
                fileName = SCANNER.nextLine();
                musicBands = Deserializer.deserialize(fileName);
            }
        }

        if (!musicBands.isEmpty()) {
            // go through all creation dates and find the oldest one
            creationDate = new Date(Long.MAX_VALUE);
            for (MusicBand musicBand : musicBands) {
                if (musicBand.getCreationDate().before(creationDate)) {
                    creationDate = musicBand.getCreationDate();
                }
            }
        }
    }

    /**
     * Adds a flat to the priority queue of flats.
     *
     * @param musicBand the flat to add
     */
    public void addMusicBand(MusicBand musicBand) {
        musicBands.add(musicBand);
    }


    /**
     * Returns the name of the file containing the flats.
     *
     * @return the name of the file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the name of the file containing the flats.
     *
     * @param fileName the name of the file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setExtraMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public MusicBand getExtraMusicBand() {
        return musicBand;
    }

    /**
     * Returns the priority queue of flats.
     *
     * @return the priority queue of flats
     */
    public HashSet<MusicBand> getMusicBands() {
        return musicBands;
    }

    /**
     * Returns the flat with the given ID, or null if there is no such flat.
     *
     * @param id the ID of the flat to find
     * @return the flat with the given ID, or null if there is no such flat
     */
    public MusicBand getMusicBandByID(Long id) throws WrongArgsException {
        for (MusicBand flat : musicBands) {
            if (Objects.equals(id.toString(), flat.getId().toString())) {
                return flat;
            }
        }
        throw new WrongArgsException("There is no element with such ID");
    }

    public void setMusicBandById(Long id, MusicBand newBand) throws WrongArgsException {
        MusicBand existingBand = getMusicBandByID(id);
        existingBand.setName(newBand.getName());
        existingBand.setCoordinates(newBand.getCoordinates());
        existingBand.setCreationDate(String.valueOf(newBand.getCreationDate()));
        existingBand.setNumberOfParticipants(newBand.getNumberOfParticipants());
        existingBand.setAlbumsCount(newBand.getAlbumsCount());
        existingBand.setGenre(newBand.getMusicGenre());
        existingBand.setLabel(newBand.getLabel());
    }

    /**
     * Removes the flat with the given ID from the priority queue of flats.
     *
     * @param id the ID of the flat to remove
     */
    public void removeMusicBandByID(Long id) throws WrongArgsException {
        musicBands.remove(getMusicBandByID(id));
    }

    /**
     * Returns the scanner used for user input.
     *
     * @return the scanner used for user input
     */
    public Scanner getScanner() {
        return SCANNER;
    }

    /**
     * Returns the creation date of the flats in the format "dd.MM.yyyy HH:mm:ss".
     *
     * @return the creation date of the flats
     */
    public String getCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return creationDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter);
    }

}



