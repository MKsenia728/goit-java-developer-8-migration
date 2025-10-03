package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
    private final Path path;

    public FileReader(Path path) {
        this.path = path;
    }

    public String readSQLFromFile() {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + path, e);
        }
    }
}
