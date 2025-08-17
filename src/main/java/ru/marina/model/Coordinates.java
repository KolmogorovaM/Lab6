package ru.marina.model;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

/**
 * Represents the coordinates of a point in a two-dimensional space.
 */
public class Coordinates implements Serializable {
    @CsvBindByName(column = "coordinates_x", required = true)
    private double x; // Максимальное значение поля: 314

    @CsvBindByName(column = "coordinates_y", required = true)
    private double y; // Максимальное значение поля: 314

    /**
     * Constructs a new instance of the Coordinates class with default values.
     */
    public Coordinates() {}

    /**
     * Constructs a new instance of the Coordinates class with the specified x and y coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Coordinates(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Gets the x coordinate.
     * @return The x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the y coordinate.
     * @return The y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x coordinate.
     * @param x The x coordinate.
     * @throws IllegalArgumentException if x is greater than 314.
     */
    public void setX(double x) {
        if (x > 314) {
            throw new IllegalArgumentException("x cannot be greater than 314");
        }
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     * @param y The y coordinate.
     * @throws IllegalArgumentException if y is greater than 314.
     */
    public void setY(double y) {
        if (y > 314) {
            throw new IllegalArgumentException("y cannot be greater than 314");
        }
        this.y = y;
    }

    /**
     * Returns a string representation of the Coordinates object.
     * @return The string representation of the Coordinates object.
     */
    @Override
    public String toString() {
        return "{\n" +
                "\t\t\t" + "\u001B[33m\"x\"\u001B[0m: " + "\u001B[36m" + x + "\u001B[0m,\n" +
                "\t\t\t" + "\u001B[33m\"y\"\u001B[0m: " + "\u001B[36m" + y + "\u001B[0m,\n" +
                "\t\t}";
    }
}
