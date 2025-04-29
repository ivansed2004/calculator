package ru.ivan.commons.studvesna.samplers;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import ru.ivan.commons.studvesna.api.MathObject;
import ru.ivan.commons.studvesna.api.Sampler;
import ru.ivan.commons.studvesna.objects.spline.Spline;
import ru.ivan.commons.studvesna.objects.spline.SplineBasedFunction;

import java.util.HashMap;
import java.util.Map;

public class SplineSampler implements Sampler {

    @Override
    public Map<Double, Double> perform( MathObject input, ActionMetadata metadata ) {

        SplineBasedFunction function = (SplineBasedFunction) input;
        double period = (double) metadata.getMetadata().get("period");

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
