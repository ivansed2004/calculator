package ru.sedinkin.calculator.samplers;

import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.core.Sampler;
import ru.sedinkin.calculator.objects.spline.SUnit;
import ru.sedinkin.calculator.objects.spline.Spline;

import java.util.HashMap;
import java.util.Map;

public class SplineSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, Map<String, Object> metadata ) {

        Spline function = (Spline) input;
        double period = (double) metadata.get("period");

        Map<Double, Double> results = new HashMap<>();

        int temp = 0;
        for ( SUnit spline: function.getSUNITS() ) {
            double count = (spline.getEND() - spline.getSTART()) / period;

            double arg = spline.getSTART();
            for ( int i = 0; i < count; i++ ) {
                results.put( arg, spline.getValue(arg) );
                arg += period;
            }
            if ( temp == function.getSUNITS().size() - 1 ) {
                arg = spline.getEND();
                results.put( arg, spline.getValue(arg) );
            }

            temp++;
        }

        return results;
    }

}
