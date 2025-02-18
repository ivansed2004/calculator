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

import static ru.ivan.commons.studvesna.utils.FileUtils.*;

public class Runner {

    public static void main(String[] args) {

        JFrame frame = new JFrame("File Chooser Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select files you wish");
        fileChooser.setMultiSelectionEnabled(true);

        int userSelection = fileChooser.showOpenDialog(frame);

        File[] filesToOpen;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            filesToOpen = fileChooser.getSelectedFiles();
            for (File file : filesToOpen) {
                System.out.println(file.getAbsolutePath());
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