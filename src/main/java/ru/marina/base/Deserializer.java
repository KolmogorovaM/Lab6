package ru.marina.base;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import ru.marina.model.MusicBand;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * The Deserializer class provides a method to deserialize a CSV file into a HashSet of MusicBand objects.
 */
public class Deserializer {

    /**
     * Deserializes a CSV file into a HashSet of MusicBand objects.
     *
     * @param fileName the name of the CSV file to deserialize
     * @return a HashSet of MusicBand objects representing the data in the CSV file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static HashSet<MusicBand> deserialize(String fileName) throws IOException {
        HashSet<MusicBand> queue = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<MusicBand> csv = new CsvToBeanBuilder<MusicBand>(csvReader).withType(MusicBand.class).build();
            queue = new HashSet<>(csv.parse());

            System.out.println(queue.size() + " item(s) loaded from file " + fileName);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        } catch (Throwable e){
            System.out.println("An error occurred while reading file. Data not loaded.");
        }

        return queue;
    }

}