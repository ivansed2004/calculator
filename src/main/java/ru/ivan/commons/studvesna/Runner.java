package ru.ivan.commons.studvesna;

import ru.ivan.commons.studvesna.builders.HyperbolaExpressionBuilder;
import ru.ivan.commons.studvesna.builders.InterferogramExpressionBuilder;
import ru.ivan.commons.studvesna.objects.Hyperbola;
import ru.ivan.commons.studvesna.objects.Interferogram;
import ru.ivan.commons.studvesna.objects.SplineBasedFunction;
import ru.ivan.commons.studvesna.objects.splines.*;
import ru.ivan.commons.studvesna.persisters.HyperbolaPersister;
import ru.ivan.commons.studvesna.persisters.InterferogramPersister;
import ru.ivan.commons.studvesna.persisters.SplinePersister;
import ru.ivan.commons.studvesna.printers.HyperbolaPrinter;
import ru.ivan.commons.studvesna.printers.InterferogramPrinter;
import ru.ivan.commons.studvesna.samplers.HyperbolaSampler;
import ru.ivan.commons.studvesna.samplers.InterferogramSampler;
import ru.ivan.commons.studvesna.samplers.SplineSampler;

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
        selectTargetDirectory();
        System.out.println(TARGET_DIRECTORY);
        start( selectSourceFiles() );
    }

    private static void start( File[] filesToOpen ) {
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
                String hyperbolaDiscreteFilename = String.format("hyperbola_discrete%d.dat", fileNum);
                String hyperbolaAnalyticalFilename = String.format("hyperbola_analytical%d.txt", fileNum);

                SplineBasedFunction sbf = getSplineBasedFunction( file, 20 );
                getPersistedSpectrum( sbf, targetPath, spectrumDiscreteFilename );

                Interferogram interferogram = getInterferogram( sbf );
                getPersistedInterferogram( interferogram, targetPath, interferogramDiscreteFilename );
                getPrintedInterferogram( interferogram, targetPath, interferogramAnalyticalFilename );

                Hyperbola hyperbola = getHyperbola( interferogram );
                getPersistedHyperbola( hyperbola, targetPath, hyperbolaDiscreteFilename );
                getPrintedHyperbola( hyperbola, targetPath, hyperbolaAnalyticalFilename );

                System.out.printf("\nThe directory for source file №%d has been generated.\n", fileNum);
            }

            System.out.println("Do you wish to terminate the program?");
            if ( Objects.equals(scn.nextLine(), "y") ) {
                scn.close();
                System.exit(0);
            }
        }
    }

    private static void selectTargetDirectory() throws Exception {
        setSystemLookAndFeel();

        Frame frame = new Frame("Source files issue");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            TARGET_DIRECTORY = fileChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private static File[] selectSourceFiles() {
        Frame frame = new Frame("Source files issue");
        FileDialog fileDialog = new FileDialog( frame, "Select source files", FileDialog.LOAD );
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);
        File[] filesToOpen = fileDialog.getFiles();
        frame.dispose();
        return filesToOpen;
    }

    public static void getPrintedHyperbola( Hyperbola hyperbola, String targetPath, String fileName ) {
        HyperbolaExpressionBuilder builder = new HyperbolaExpressionBuilder();
        HyperbolaPrinter printer = new HyperbolaPrinter();

        List<String> stringsToPrint = builder.perform( hyperbola, Map::of );
        printer.perform( stringsToPrint, () -> Map.of("path", targetPath, "fileName", fileName) );
    }

    public static void getPersistedHyperbola( Hyperbola hyperbola, String targetPath, String fileName ) {
        HyperbolaSampler sampler = new HyperbolaSampler();
        HyperbolaPersister persister = new HyperbolaPersister();

        Map<Double, Double> samples = sampler
                .perform( hyperbola, () -> Map.of("start", 1429.155, "end", 4000.092, "period", 1.929)  );
        persister.perform( samples, () -> Map.of("path", targetPath, "fileName", fileName) );
    }

    public static Hyperbola getHyperbola( Interferogram interferogram ) {
        return new Hyperbola( interferogram );
    }

    public static void getPrintedInterferogram( Interferogram interferogram, String targetPath, String fileName ) {
        InterferogramExpressionBuilder builder = new InterferogramExpressionBuilder();
        InterferogramPrinter printer = new InterferogramPrinter();

        List<String> stringsToPrint = builder.perform( interferogram, Map::of );
        printer.perform( stringsToPrint, () -> Map.of("path", targetPath, "fileName", fileName) );
    }

    public static void getPersistedInterferogram( Interferogram interferogram, String targetPath, String fileName ) {
        InterferogramSampler sampler = new InterferogramSampler();
        InterferogramPersister persister = new InterferogramPersister();

        Map<Double, Double> samples = sampler
                .perform( interferogram, () -> Map.of("start", 1429.155, "end", 4000.092, "period", 1.929) );
        persister.perform( samples, () -> Map.of("path", targetPath, "fileName", fileName) );
    }

    public static Interferogram getInterferogram( SplineBasedFunction sbf ) {
        return new Interferogram( sbf );
    }

    public static void getPersistedSpectrum( SplineBasedFunction sbf, String targetPath, String fileName ) {
        SplineSampler sampler = new SplineSampler();
        SplinePersister persister = new SplinePersister();

        Map<Double, Double> samples = sampler
                .perform( sbf, () -> Map.of( "period", 1.929 ) );
        persister.perform( samples, () -> Map.of("path", targetPath, "fileName", fileName) );
    }

    public static SplineBasedFunction getSplineBasedFunction( File file, int period ) {
        return SplineEquationResolver.resolve( file, period );
    }

}