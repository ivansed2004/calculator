package ru.sedinkin.calculator.samplers;

import ru.sedinkin.calculator.api.MathObject;
import ru.sedinkin.calculator.api.Sampler;
import ru.sedinkin.calculator.objects.spline.Spline;
import ru.sedinkin.calculator.objects.spline.SplineBasedFunction;

import java.util.HashMap;
import java.util.Map;

public class SplineSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, Map<String, Object> metadata ) {

        SplineBasedFunction function = (SplineBasedFunction) input;
        double period = (double) metadata.get("period");

        Map<Double, Double> results = new HashMap<>();

        int temp = 0;
        for ( Spline spline: function.getSPLINES() ) {
            double count = (spline.getEND() - spline.getSTART()) / period;

            double arg = spline.getSTART();
            for ( int i = 0; i < count; i++ ) {
                results.put( arg, spline.getValue(arg) );
                arg += period;
            }
            if ( temp == function.getSPLINES().size() - 1 ) {
                arg = spline.getEND();
                results.put( arg, spline.getValue(arg) );
            }

            temp++;
        }

        return results;
    }

}
