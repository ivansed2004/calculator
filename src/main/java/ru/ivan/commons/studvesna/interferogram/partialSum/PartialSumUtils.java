package ru.ivan.commons.studvesna.interferogram.partialSum;

import ru.ivan.commons.studvesna.interferogram.Interferogram;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

public class PartialSumUtils {

    // Keep it always private!
    private PartialSumUtils () {}

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
            samples.put( end, interferogram.evaluate(k, end) );

            results.add(samples);
        }

        return results;

    }

}
