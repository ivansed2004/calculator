package ru.ivan.commons.studvesna.samplers;

import ru.ivan.commons.studvesna.api.MathObject;
import ru.ivan.commons.studvesna.api.Sampler;
import ru.ivan.commons.studvesna.objects.interferogram.Interferogram;

import java.util.HashMap;
import java.util.Map;

// Architecture violation!
public class InterferogramSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, Map<String, Object> metadata ) {
        return sampleForHyperbola( (Interferogram) input, metadata );
    }

    private static Map<Double, Double> sampleByDefault( Interferogram interferogram, Map<String, Object> metadata ) {
        double start = (double) metadata.get("start");
        double end = (double) metadata.get("end");
        double period = (double) metadata.get("period");

        Map<Double, Double> samples = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int i = 0; i < count; i++ ) {
            samples.put(arg, interferogram.getValue(arg));
            arg += period;
        }
        samples.put(end, interferogram.getValue(end));

        return samples;
    }

    private static Map<Double, Double> sampleForHyperbola( Interferogram interferogram, Map<String, Object> metadata ) {
        double start = (double) metadata.get("start");
        double end = (double) metadata.get("end");
        double period = (double) metadata.get("period");
        int i = (int) metadata.get("i");

        Map<Double, Double> results = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int k = 0; k < count; k++ ) {
            results.put( arg, interferogram.evaluate(i, arg) );
            arg += period;
        }
        results.put( end, interferogram.evaluate(i, end) );

        return results;
    }

}
