package ru.marina.server.base;

import java.io.File;

public class FileValidator {

    /**
     * Checks if a file exists and has write access.
     *
     * @param filePath the path of the file to be checked
     * @throws IllegalArgumentException if the file does not exist or does not have write access
     */
    public static void checkFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        if (!file.canWrite()) {
            throw new IllegalArgumentException("No write access to file: " + filePath);
        }
    }
}
