package ru.ivan.commons.studvesna.interferogram.partialSum;

import ru.ivan.commons.studvesna.interferogram.Interferogram;

import java.util.HashMap;
import java.util.Map;

public class PartialSumUtils {

    // Keep it always private!
    private PartialSumUtils () {}

    public static Map<Double, Double> performSampling( Interferogram interferogram, double start, double end, double period, int[] ints ) {

        Map<Double, Double> results = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int i = 0; i < count; i++ ) {
            results.put( arg, interferogram.evaluate(ints, arg) );
            arg += period;
        }
        results.put( end, interferogram.evaluate(ints, end) );

        return results;

    }

}
