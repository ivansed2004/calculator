package ru.ivan.commons.studvesna;

import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.InterferogramUtils;
import ru.ivan.commons.studvesna.splines.Spline;
import ru.ivan.commons.studvesna.splines.SplineEquationResolver;
import ru.ivan.commons.studvesna.splines.SplineUtils;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static ru.ivan.commons.studvesna.utils.FileUtils.*;

public class Runner {

    public static void main(String[] args) {

        Scanner scn = new Scanner( System.in );

        TARGET_DIRECTORY = "/home/ivan/Desktop/Студвесна 2024/Часть 2/target";

        while (true) {

            JFrame frame = new JFrame("File Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 480);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select files you wish");
            fileChooser.setMultiSelectionEnabled(true);

            int userSelection = fileChooser.showOpenDialog(frame);

            File[] filesToOpen;
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                filesToOpen = fileChooser.getSelectedFiles();
                System.out.println("Start generating...");
                for (File file : filesToOpen) {

                    int fileNum = Integer.parseInt( file.getName().split("\\.")[0] );
                    String targetPath = TARGET_DIRECTORY + SEP + String.format("%d", fileNum);

                    String spectrumDiscreteFilename = String.format("spectrum_discrete%d.dat", fileNum);
                    String interferogramDiscreteFilename = String.format("interferogram_discrete%d.dat", fileNum);
                    String interferogramAnalyticalFilename = String.format("interferogram_analyticla%d.txt", fileNum);

                    List<Spline> splines = getApproximatedSpectrum( file, targetPath, spectrumDiscreteFilename );
                    Interferogram interferogram = getApproximatedInterferogram( splines, targetPath, interferogramDiscreteFilename );
                    getAnalyticalFunctionPrinted( interferogram, targetPath, interferogramAnalyticalFilename );

                }
            }

            System.out.println("Do you wish to terminate the program?");
            if ( Objects.equals(scn.nextLine(), "exit") ) {
                trash( TARGET_DIRECTORY );
                System.exit(0);
            }

        }

    }

    // Fix the spectrum printing
    private static List<Spline> getApproximatedSpectrum( File file, String targetPath, String fileName ) {

        List<Spline> splines = SplineEquationResolver.resolve( file, 20 );
        SplineUtils.performSampling( splines, 1.929 );
        SplineUtils.persist( splines, targetPath, fileName );

        return splines;

    }

    private static Interferogram getApproximatedInterferogram( List<Spline> splines, String targetPath, String fileName ) {

        double start = 1429.155;
        double end = 4000.092;
        double period = 1.929;

        Interferogram interferogram = InterferogramUtils.defineInterferogram(splines);
        Map<Double, Double> samples = InterferogramUtils.performSampling( interferogram, start, end, period );
        InterferogramUtils.persist( samples, targetPath, fileName, start, end, period );

        return interferogram;

    }

    private static void getAnalyticalFunctionPrinted( Interferogram interferogram, String targetPath, String fileName ) {

        String[] strings = InterferogramUtils.toStrings(interferogram);
        InterferogramUtils.printInterferogram( strings, targetPath, fileName );

    }

}