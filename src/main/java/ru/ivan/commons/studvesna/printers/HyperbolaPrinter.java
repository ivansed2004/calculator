package ru.ivan.commons.studvesna.printers;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import ru.ivan.commons.studvesna.api.Printer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

public class HyperbolaPrinter implements Printer {

    @Override
    public void perform( List<String> input, ActionMetadata metadata ) {

        String path = (String) metadata.getMetadata().get("path");
        String fileName = (String) metadata.getMetadata().get("fileName");

        try {
            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );
        } catch (IOException ex) {
            System.out.println("Error occurred while creating hyperbola_analytical file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {
            fw.write( input.get(0) );
        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }

    }

}
