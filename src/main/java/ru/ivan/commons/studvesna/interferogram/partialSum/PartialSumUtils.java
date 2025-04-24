package ru.ivan.commons.studvesna.interferogram.partialSum;

import ru.ivan.commons.studvesna.interferogram.Interferogram;

import java.util.HashMap;
import java.util.Map;

public class PartialSumUtils {

    // Keep it always private!
    private PartialSumUtils () {}

    public static Map<Double, Double> performSampling( Interferogram interferogram, double start, double stop,
                                                       double inc, double ext, double areaInc, int[] k ) {
        Map<Double, Double> results = new HashMap<>();

        double center = start;
        while ( center <= stop ) {
            for ( double arg = center-ext; arg <= center+ext; arg += areaInc ) {
                results.put( arg, interferogram.evaluate(k, arg) );
            }
            center += inc;
        }

        return results;
    }

}
