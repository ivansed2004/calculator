package ru.ivan.commons.studvesna.splines;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ru.ivan.commons.studvesna.file.FileUtils.SEP;

public class SplineUtils {

    // Keep it always private!
    private SplineUtils() {}

    public static void performSampling( List<Spline> splines, double period ) {

        int temp = 0;
        for ( Spline spline: splines ) {

            double count = (spline.getEnd() - spline.getStart()) / period;

            List<Double> waveNumbers = new ArrayList<>();
            List<Double> intensities = new ArrayList<>();

            double sample = spline.getStart();
            for ( int i = 0; i < count; i++ ) {
                double formula = spline.getA0() + spline.getA1()*sample + spline.getA2()*Math.pow(sample, 2) + spline.getA3()*Math.pow(sample, 3);
                waveNumbers.add( sample );
                intensities.add( formula );
                sample += period;
            }

            if ( temp == splines.size() - 1 ) {
                sample = spline.getEnd();
                double formula = spline.getA0() + spline.getA1()*sample + spline.getA2()*Math.pow(sample, 2) + spline.getA3()*Math.pow(sample, 3);
                waveNumbers.add( sample );
                intensities.add( formula );
            }

            spline.setWaveNumbers( waveNumbers );
            spline.setIntensities( intensities );

            temp++;

        }

    }

    public static void persist( List<Spline> splines, String path, String fileName ) {

        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating spectrum_discrete file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter( path + SEP + fileName ) ) {
            for ( Spline s : splines ) {
                List<Double> wn = s.getWaveNumbers();
                List<Double> its = s.getIntensities();
                for ( int i = 0; i < wn.size(); i++ ) {
                    fw.write( String.format( "%.3f", wn.get(i) ) + "\t" + String.format( "%.8f", its.get(i) ) );
                    fw.write("\n");
                }
            }

        } catch ( IOException ex ) {
            System.out.println("Error occurred while writing into the file. " + ex.getMessage());
        }

    }

}
