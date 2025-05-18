package ru.sedinkin.calculator.persisters;

import ru.sedinkin.calculator.core.Persister;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.sedinkin.calculator.environment.Environment.SEP;

public class InterferogramPersister implements Persister {

    @Override
    public void perform( Map<Double, Double> input, Map<String, Object> metadata ) {

        String path = (String) metadata.get("path");
        String fileName = (String) metadata.get("fileName");

        try {
            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );
        } catch (IOException ex) {
            System.out.println("Error occurred while creating interferogram_discrete file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {
            List<Double> args = input.keySet().stream().sorted().collect(Collectors.toList());
            for ( double arg : args ) {
                fw.write( String.format( "%.3f", arg ) + "\t" + String.format( "%.8f", input.get(arg) ) );
                fw.write("\n");
            }
        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }
    }

}
