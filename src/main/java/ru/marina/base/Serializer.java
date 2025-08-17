package ru.marina.base;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.marina.model.MusicBand;
import javax.naming.NoPermissionException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;


/**
 * The Serializer class provides methods to serialize a HashSet of MusicBand objects and write them to a file.
 * It uses the OpenCSV library to convert the MusicBand objects to CSV format and writes them to the file.
 */
public class Serializer {
    public static void serialize(HashSet<MusicBand> musicBands,
                                 String fileName                ) throws IOException,
                                                                         NoPermissionException
    {
        Path path = Paths.get(fileName);
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
        }
        if (!Files.isRegularFile(path)) {
            throw new IOException("Not a file!");
        }
        if (!Files.isWritable(path)) {
            throw new NoPermissionException("File isn't writable!");
        }
        if (!Files.isReadable(path)) {
            throw new NoPermissionException("File isn't readable!");
        }


        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
            OutputStreamWriter writer = new OutputStreamWriter(bos);
            StatefulBeanToCsv<MusicBand> beanToCsv = new StatefulBeanToCsvBuilder<MusicBand>(writer).build();
            beanToCsv.write(musicBands.stream());
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
        }
    }
}
