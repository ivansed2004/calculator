package ru.ivan.commons.studvesna.hyperbola;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

public class HyperbolaUtils {

    private HyperbolaUtils() {}

    public static void printExpression( String expressions, String path, String fileName ) {

        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating hyperbola_analytical file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {
            fw.write( expressions );
        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }

    }

}
