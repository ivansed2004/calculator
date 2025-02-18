package ru.ivan.commons.studvesna;

import ru.ivan.commons.studvesna.splines.*;
import ru.ivan.commons.studvesna.interferogram.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static ru.ivan.commons.studvesna.utils.FileUtils.*;

public class Runner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the source folder FULL path: ");
        SOURCE_FOLDER = scanner.nextLine();
        System.out.println("Enter the target folder FULL path: ");
        TARGET_FOLDER = scanner.nextLine();

        while (true) {

            System.out.println("Start generating...");

            trash( TARGET_FOLDER );

            try (DirectoryStream<Path> stream = Files.newDirectoryStream( Path.of( SOURCE_FOLDER ) )) {

                int n = 0;
                for ( Path file : stream ) {
                    if ( Files.isRegularFile( file ) ) {
                        List<Spline> splines = getApproximatedSpectrum( n+1 );
                        Interferogram interferogram = getApproximatedInterferogram( splines, n+1 );
                        System.out.printf("Result folder №%d has been generated.\n", n+1);
                        n++;
                    }
                }
                System.out.println("Generating stopped.");

                System.out.println( "Press any key to continue." );
                scanner.nextLine();

            } catch ( IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    private static List<Spline> getApproximatedSpectrum( int fileNum ) {

        String sourcePath = SOURCE_FOLDER + SEP + String.format("%d.dat", fileNum);
        String targetPath = TARGET_FOLDER + SEP + String.format("%d", fileNum);

        List<Spline> splines = SplineEquationResolver.resolve( 20, sourcePath );
        SplineUtils.performSampling( 1.929, splines );
        SplineUtils.persist( targetPath, String.format( "spectrum%d.dat", fileNum), splines );

        return splines;

    }

    private static Interferogram getApproximatedInterferogram( List<Spline> splines, int fileNum ) {

        double start = 1429.155;
        double end = 4000.092;
        double period = 1.929;

        String targetPath = TARGET_FOLDER + SEP + String.format("%d", fileNum);

        Interferogram interferogram = InterferogramUtils.defineInterferogram(splines);

        Map<Double, Double> samples = InterferogramUtils
                .performSampling(interferogram, start, end, period);
        InterferogramUtils.persist( targetPath, String.format("interferogram%d.dat", fileNum), samples, start, end, period );

        String[] strings = InterferogramUtils.toStrings(interferogram);
        InterferogramUtils.printInterferogram( strings, targetPath, String.format("analytical_interferogram%d.txt", fileNum) );

        return interferogram;

    }

}