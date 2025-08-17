package ru.marina.model;

import com.opencsv.bean.CsvBindByName;

/**
 * The Label class represents a label in the music industry.
 * It contains information about the label's name, number of bands, and sales.
 */
public class Label implements java.io.Serializable {
    @CsvBindByName(column = "label_name")
    private String name;

    @CsvBindByName(column = "label_bands")
    private Long bands;

    @CsvBindByName(column = "label_sales", required = true)
    private double sales;

    /**
     * Default constructor for the Label class.
     */
    public Label() {}

    /**
     * Constructor for the Label class.
     * @param name The name of the label.
     * @param bands The number of bands associated with the label.
     * @param sales The sales of the label.
     */
    public Label(String name, Long bands, double sales) {
        this.name = name;
        this.bands = bands;
        this.sales = sales;
    }

    /**
     * Get the name of the label.
     * @return The name of the label.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the label.
     * @param name The name of the label.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the number of bands associated with the label.
     * @return The number of bands associated with the label.
     */
    public Long getBands() {
        return bands;
    }

    /**
     * Set the number of bands associated with the label.
     * @param bands The number of bands associated with the label.
     */
    public void setBands(Long bands) {
        this.bands = bands;
    }

    /**
     * Get the sales of the label.
     * @return The sales of the label.
     */
    public double getSales() {
        return sales;
    }

    /**
     * Set the sales of the label.
     * @param sales The sales of the label.
     * @throws IllegalArgumentException if the sales is less than or equal to 0.
     */
    public void setSales(double sales) {
        if (sales <= 0) {
            throw new IllegalArgumentException("Sales should be greater than 0");
        }
        this.sales = sales;
    }

    /**
     * Returns a string representation of the Label object.
     * @return A string representation of the Label object.
     */
    @Override
    public String toString(){
        return "{\n" +
                "\t\t\t" + "\u001B[33m\"name\"\u001B[0m: " + "\u001B[32m\"" + name + "\"\u001B[0m,\n" +
                "\t\t\t" + "\u001B[33m\"bands\"\u001B[0m: " + "\u001B[36m" + bands + "\u001B[0m,\n" +
                "\t\t\t" + "\u001B[33m\"sales\"\u001B[0m: " + "\u001B[36m" + sales + "\u001B[0m,\n" +
                "\t\t}";
    }
}