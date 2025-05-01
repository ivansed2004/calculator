package ru.ivan.calculator.printers;

import ru.ivan.calculator.api.Printer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.ivan.calculator.environment.Environment.SEP;

public class InterferogramPrinter implements Printer {

    @Override
    public void perform( List<String> input, Map<String, Object> metadata ) {

        String path = (String) metadata.get("path");
        String fileName = (String) metadata.get("fileName");

        try {
            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );
        } catch (IOException ex) {
            System.out.println("Error occurred while creating interferogram_analytical file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {
            for (int i = 0; i < input.size(); i++) {
                if ( !Objects.equals(input.get(i), "") ) {
                    if ( i != input.size()-1 ) {
                        fw.write( String.format("%s +", input.get(i)) );
                    } else {
                        fw.write( String.format("%s", input.get(i)) );
                    }
                    fw.write("\n\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }

    }

}
