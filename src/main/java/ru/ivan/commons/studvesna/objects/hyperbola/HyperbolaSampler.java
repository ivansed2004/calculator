package ru.ivan.commons.studvesna.objects.hyperbola;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import ru.ivan.commons.studvesna.api.MathObject;
import ru.ivan.commons.studvesna.api.Sampler;

import java.util.HashMap;
import java.util.Map;

public class HyperbolaSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, ActionMetadata metadata ) {

        double start = (double) metadata.getMetadata().get("start");
        double end = (double) metadata.getMetadata().get("end");
        double period = (double) metadata.getMetadata().get("period");

        Map<Double, Double> samples = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int i = 0; i < count; i++ ) {
            samples.put(arg, input.getValue(arg));
            arg += period;
        }
        samples.put(end, input.getValue(end));

        return samples;

    }

}
