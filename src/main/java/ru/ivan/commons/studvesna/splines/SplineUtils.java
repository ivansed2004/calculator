package ru.ivan.commons.studvesna.splines;

import static ru.ivan.commons.studvesna.utils.FileUtils.SEP;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SplineUtils {

    private SplineUtils() {}

    public static void performSampling( double period, List<Spline> splines ) {

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

    public static void persist( String path, String fileName, List<Spline> splines ) {

        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating 'interferogram.dat' file...: " + ex.getMessage());
        }

        File file = new File( path + SEP + fileName );

        try ( PrintWriter writer = new PrintWriter( new FileWriter( file ) ) ) {
            for ( Spline s : splines ) {
                List<Double> wn = s.getWaveNumbers();
                List<Double> its = s.getIntensities();
                for ( int i = 0; i < wn.size(); i++ ) {
                    writer.println( String.format( "%.3f", wn.get(i) ) + "\t" + String.format( "%.8f", its.get(i) ) );
                }
            }

        } catch ( IOException ex ) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }

    }

}
