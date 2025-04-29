package ru.ivan.commons.studvesna.objects.hyperbola;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

public class HyperbolaUtils {

    private HyperbolaUtils() {}

    public static Map<Double, Double> performSampling(Hyperbola hyperbola, double start, double end, double period ) {
        Map<Double, Double> samples = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int i = 0; i < count; i++ ) {
            samples.put(arg, hyperbola.getValue(arg));
            arg += period;
        }
        samples.put(end, hyperbola.getValue(end));

        return samples;
    }

    public static void persist( Map<Double, Double> samples, String path, String fileName, double start, double end,
                                double period ) {
        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating hyperbola_discrete file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {

            double count = Math.ceil((end - start) / period);
            double arg = start;
            for ( int i = 0; i < count; i++ ) {
                fw.write( String.format( "%.3f", arg ) + "\t" + String.format( "%.8f", samples.get(arg) ) );
                fw.write("\n");
                arg += period;
            }
            fw.write( String.format( "%.3f", end ) + "\t" + String.format( "%.8f", samples.get(end) ) );
            fw.write("\n");

        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }
    }

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
