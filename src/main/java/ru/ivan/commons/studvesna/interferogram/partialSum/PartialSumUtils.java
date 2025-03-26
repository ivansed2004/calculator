package ru.ivan.commons.studvesna.interferogram.partialSum;

import ru.ivan.commons.studvesna.interferogram.Interferogram;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ivan.commons.studvesna.file.FileUtils.PS_DIRECTORY;
import static ru.ivan.commons.studvesna.file.FileUtils.SEP;

public class PartialSumUtils {

    public static List<Map<Double, Double>> performSampling( Interferogram interferogram, double start, double end, double period ) {

        List<Map<Double, Double>> results = new ArrayList<>();

        for ( int k = 0; k < 10; k++ ) {
            Map<Double, Double> samples = new HashMap<>();

            double count = Math.ceil((end - start) / period);

            double arg = start;
            for ( int i = 0; i < count; i++ ) {
                samples.put( arg, interferogram.evaluate(k, arg) );
                arg += period;
            }
            samples.put( end, interferogram.evaluate(k, arg) );

            results.add(samples);
        }

        return results;

    }

    public static void persist( List<Map<Double, Double>> unitSamples, String path, String fileName, double start, double end,
                                double period ) {

        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating interferogram_discrete file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + PS_DIRECTORY + SEP + fileName) ) {

            for ( Map<Double, Double> samples : unitSamples ) {
                double count = Math.ceil((end - start) / period);
                double arg = start;
                for ( int i = 0; i < count; i++ ) {
                    fw.write( String.format( "%.3f", arg ) + "\t" + String.format( "%.8f", samples.get(arg) ) );
                    fw.write("\n");
                    arg += period;
                }
                fw.write( String.format( "%.3f", end ) + "\t" + String.format( "%.8f", samples.get(end) ) );
                fw.write("\n");
            }

        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }

    }

}
