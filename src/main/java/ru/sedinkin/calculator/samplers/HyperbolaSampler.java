package ru.sedinkin.calculator.samplers;

import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.core.Sampler;

import java.util.HashMap;
import java.util.Map;

public class HyperbolaSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, Map<String, Object> metadata ) {

        double start = (double) metadata.get("start");
        double end = (double) metadata.get("end");
        double period = (double) metadata.get("period");

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
