package ru.marina.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvRecurse;
import ru.marina.base.DateConverter;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a music band.
 */
public class MusicBand implements Comparable<MusicBand>, Serializable {
    @CsvBindByName(column = "ID", required = true)
    private Long id;  // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @CsvBindByName(column = "name", required = true)
    private String name;  // Поле не может быть null, Строка не может быть пустой

    @CsvRecurse
    private Coordinates coordinates;  // Поле не может быть null

    @CsvCustomBindByName(column = "creation_date", required = true, converter = DateConverter.class)
    private Date creationDate;  // Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @CsvBindByName(column = "numberOfParticipants", required = true)
    private int numberOfParticipants;  // Значение поля должно быть больше 0

    @CsvBindByName(column = "albumsCount", required = true)
    private int albumsCount;  // Значение поля должно быть больше 0

    @CsvBindByName(column = "genre", required = true)
    private MusicGenre genre;  // Поле может быть null

    @CsvRecurse
    private Label label;  // Поле не может быть null

    /**
     * Default constructor for MusicBand.
     * Sets the ID and creation date automatically.
     */
    public MusicBand() {
        setId();
        setCreationDate();
    }

    /**
     * Constructor for MusicBand with specified parameters.
     * Sets the ID, name, coordinates, creation date, number of participants, albums count, genre, and label.
     * @param name the name of the music band
     * @param coordinates the coordinates of the music band
     * @param numberOfParticipants the number of participants in the music band
     * @param albumsCount the number of albums of the music band
     * @param genre the genre of the music band
     * @param label the label of the music band
     */
    public MusicBand(String name,
                     Coordinates coordinates,
                     int numberOfParticipants,
                     int albumsCount,
                     MusicGenre genre,
                     Label label)
    {
        setId();
        setName(name);
        setCoordinates(coordinates);
        setCreationDate();
        setNumberOfParticipants(numberOfParticipants);
        setAlbumsCount(albumsCount);
        setGenre(genre);
        setLabel(label);
    }

    /**
     * Gets the ID of the music band.
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the name of the music band.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the coordinates of the music band.
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the creation date of the music band.
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the number of participants in the music band.
     * @return the number of participants
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * Gets the number of albums of the music band.
     * @return the number of albums
     */
    public int getAlbumsCount() {
        return albumsCount;
    }

    /**
     * Gets the genre of the music band.
     * @return the genre
     */
    public MusicGenre getMusicGenre() {
        return genre;
    }

    /**
     * Gets the label of the music band.
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    private void setId() {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    /**
     * Sets the name of the music band.
     * @param name the name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Flat name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Sets the coordinates of the music band.
     * @param coordinates the coordinates to set
     * @throws IllegalArgumentException if the coordinates are null
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    private void setCreationDate() {
        this.creationDate = new Date();
    }

    public void setCreationDate(String date) {
        this.creationDate = new Date(Long.parseLong(date));
    }

    /**
     * Sets the number of participants in the music band.
     * @param numberOfParticipants the number of participants to set
     * @throws IllegalArgumentException if the number of participants is less than or equal to 0
     */
    public void setNumberOfParticipants(int numberOfParticipants) {
        if (numberOfParticipants <= 0) {
            throw new IllegalArgumentException("Area must be greater than 0");
        }
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Sets the number of albums of the music band.
     * @param albumsCount the number of albums to set
     * @throws IllegalArgumentException if the number of albums is less than or equal to 0
     */
    public void setAlbumsCount(int albumsCount) {
        if (albumsCount <= 0) {
            throw new IllegalArgumentException("Number of rooms must be greater than 0");
        }
        this.albumsCount = albumsCount;
    }

    /**
     * Sets the genre of the music band.
     * @param genre the genre to set
     */
    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Sets the label of the music band.
     * @param label the label to set
     * @throws IllegalArgumentException if the label is null
     */
    public void setLabel(Label label) {
        if (label == null) {
            throw new IllegalArgumentException("Transport cannot be null");
        }
        this.label = label;
    }

    /**
     * Returns a string representation of the music band.
     * @return the string representation
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String result = "\n\t{\n";
        result += "\t\t" + "\u001B[33m\"id\"\u001B[0m: " + "\u001B[36m" + id + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"name\"\u001B[0m: " + "\u001B[32m\"" + name + "\"\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"coordinates\"\u001B[0m: " + coordinates + ",\n";
        result += "\t\t" + "\u001B[33m\"creationDate\"\u001B[0m: " + "\u001B[35m\"" + creationDate.toInstant().atZone(ZoneId.systemDefault()).format(formatter) + "\"\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"numberOfParticipants\"\u001B[0m: " + "\u001B[36m" + numberOfParticipants + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"albumsCount\"\u001B[0m: " + "\u001B[36m" + albumsCount + "\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"genre\"\u001B[0m: " + "\u001B[32m\"" + genre + "\"\u001B[0m,\n";
        result += "\t\t" + "\u001B[33m\"label\"\u001B[0m: " + label + "\u001B[0m,\n";
        result += "\t" + "}\n";
        return result;
    }

    /**
     * Compares this music band to another music band based on their creation dates.
     * @param other the other music band to compare to
     * @return a negative integer, zero, or a positive integer as this music band is less than, equal to, or greater than the other music band
     */
    @Override
    public int compareTo(MusicBand other) {
        return this.creationDate.compareTo(other.creationDate);
    }
}
