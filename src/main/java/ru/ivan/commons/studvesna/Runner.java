package ru.ivan.commons.studvesna;

import ru.ivan.commons.studvesna.hyperbola.HyperbolaBasedFunction;
import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.InterferogramUtils;
import ru.ivan.commons.studvesna.splines.Spline;
import ru.ivan.commons.studvesna.splines.SplineEquationResolver;
import ru.ivan.commons.studvesna.splines.SplineUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static ru.ivan.commons.studvesna.environment.Environment.*;

public class Runner {

    public static void main(String[] args) throws Exception {
        getTargetDirectory();
        System.out.println(TARGET_DIRECTORY);
        generateOutput();
    }

    private static void getTargetDirectory() throws Exception {
        setSystemLookAndFeel();

        Frame frame = new Frame("Source files issue");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            TARGET_DIRECTORY = fileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private static void generateOutput() {
        Frame frame = new Frame("Source files issue");
        FileDialog fileDialog = new FileDialog( frame, "Select source files", FileDialog.LOAD );
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);

        File[] filesToOpen = fileDialog.getFiles();
        Scanner scn = new Scanner( System.in );
        while (true) {
            System.out.println("Start generating...");
            for ( File file : filesToOpen ) {

                int fileNum = Integer.parseInt( file.getName().split("\\.")[0] );
                String targetPath = TARGET_DIRECTORY + SEP + String.format("%d", fileNum);

                if ( Files.exists(Paths.get(targetPath)) ) {
                    continue;
                }

                String spectrumDiscreteFilename = String.format("spectrum_discrete%d.dat", fileNum);
                String interferogramDiscreteFilename = String.format("interferogram_discrete%d.dat", fileNum);
                String interferogramAnalyticalFilename = String.format("interferogram_analytical%d.txt", fileNum);

                List<Spline> splines = getApproximatedSpectrum( file, targetPath, spectrumDiscreteFilename );
                Interferogram interferogram = getApproximatedInterferogram( splines, targetPath, interferogramDiscreteFilename );
                getAnalyticalFunctionPrinted( interferogram, targetPath, interferogramAnalyticalFilename );
                getHyperbolaExpressions( interferogram );

                System.out.printf("The directory for source file №%d has been generated.\n", fileNum);

            }

            System.out.println("Do you wish to terminate the program?");
            if ( Objects.equals(scn.nextLine(), "y") ) {
                scn.close();
                System.exit(0);
            }

            frame.dispose();
        }
    }

    private static void getHyperbolaExpressions( Interferogram interferogram ) {
        HyperbolaBasedFunction hbf = new HyperbolaBasedFunction( interferogram );
        System.out.println( hbf );
    }

    private static void getAnalyticalFunctionPrinted( Interferogram interferogram, String targetPath, String fileName ) {
        String[] strings = InterferogramUtils.toStrings(interferogram);
        InterferogramUtils.printInterferogram( strings, targetPath, fileName );
    }

    // Fix the algorithms of analytical function strings forming
    private static Interferogram getApproximatedInterferogram( List<Spline> splines, String targetPath, String fileName ) {
        double start = 1429.155;
        double end = 4000.092;
        double period = 1.929;

        Interferogram interferogram = InterferogramUtils.defineInterferogram(splines);
        Map<Double, Double> samples = InterferogramUtils.performSampling( interferogram, start, end, period );
        InterferogramUtils.persist( samples, targetPath, fileName, start, end, period );

        return interferogram;
    }

    private static List<Spline> getApproximatedSpectrum( File file, String targetPath, String fileName ) {
        List<Spline> splines = SplineEquationResolver.resolve( file, 20 );
        SplineUtils.performSampling( splines, 1.929 );
        SplineUtils.persist( splines, targetPath, fileName );

        return splines;
    }

}