package ru.ivan.commons.studvesna.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtils {

    public static String SEP = File.separator;

    public static String TARGET_DIRECTORY = "";

    private FileUtils() {}

    public static void trash( String path ) {

        try {
            Path directory = Paths.get(path);

            if (Files.exists(directory)) {
                Files.walkFileTree(directory, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

        } catch (IOException ex) {
            System.out.println("Error occurred while directory trashing...: " + ex.getMessage());
        }

    }

}
