package ru.sedinkin.calculator.samplers;

import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;
import java.util.HashMap;
import java.util.Map;

public class HInterferogramSampler extends InterferogramSampler {

    @Override
    public Map<Double, Double> perform( MathObject input, Map<String, Object> metadata ) {
        Interferogram interferogram = (Interferogram) input;

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
