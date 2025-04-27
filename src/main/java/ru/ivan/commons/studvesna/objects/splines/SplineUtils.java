package ru.ivan.commons.studvesna.objects.splines;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

public class SplineUtils {

    // Keep it always private!
    private SplineUtils() {}

    public static Map<Double, Double> performSampling( SplineBasedFunction function, double period ) {
        Map<Double, Double> results = new HashMap<>();

        int temp = 0;
        for ( Spline spline: function.getSPLINES() ) {
            double count = (spline.getEND() - spline.getSTART()) / period;

            double arg = spline.getSTART();
            for ( int i = 0; i < count; i++ ) {
                results.put( arg, spline.getValue(arg) );
                arg += period;
            }
            if ( temp == function.getSPLINES().size() - 1 ) {
                arg = spline.getEND();
                results.put( arg, spline.getValue(arg) );
            }

            temp++;
        }

        return results;
    }

    public static void persist( Map<Double, Double> samples, String path, String fileName ) {

        try {
            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );
        } catch (IOException ex) {
            System.out.println("Error occurred while creating spectrum_discrete file. : " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter( path + SEP + fileName ) ) {
            List<Double> args = samples.keySet().stream().sorted().collect(Collectors.toList());
            for ( double arg : args ) {
                fw.write( String.format( "%.3f", arg ) + "\t" + String.format( "%.8f", samples.get(arg) ) );
                fw.write("\n");
            }
        } catch ( IOException ex ) {
            System.out.println("Error occurred while writing into the file. " + ex.getMessage());
        }

    }

}
