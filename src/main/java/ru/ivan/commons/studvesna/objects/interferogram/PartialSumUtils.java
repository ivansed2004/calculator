package ru.ivan.commons.studvesna.objects.interferogram;

import java.util.HashMap;
import java.util.Map;

public class PartialSumUtils {

    // Keep it always private!
    private PartialSumUtils () {}

    public static Map<Double, Double> performSampling( Interferogram interferogram, double start, double end, double period, int i ) {

        Map<Double, Double> results = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int k = 0; k < count; k++ ) {
            results.put( arg, interferogram.evaluate(i, arg, "combined") );
            arg += period;
        }
        results.put( end, interferogram.evaluate(i, end, "combined") );

        return results;

    }

}
