package ru.ivan.commons.studvesna.hyperbola;

import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.partialSum.PartialSumUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HyperbolaBasedFunction {

    private final double[] AMPLITUDES;

    private final String FULL_EXPRESSION;

    private final String SHORT_EXPRESSION;

    public HyperbolaBasedFunction( Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram, 5 );
        this.FULL_EXPRESSION = buildFullExpression();
        this.SHORT_EXPRESSION = buildShortExpression();
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram, int a ) {

        double t = 0.1628895;
        double s1 = 0.0667401 + a*t;
        double s2 = 0.0709894 + a*t;
        double e1 = 0.0942738 + a*t;
        double e2 = 0.0985998 + a*t;
        double period = 0.0001;

        List<Map<Double, Double>> unitsSamples1 = PartialSumUtils.performSampling( interferogram, s1, e1, period );

        List<Double> ld1 = unitsSamples1
                .parallelStream()
                .map( Map::values )
                .map( collection -> collection.stream().map( Math::abs ).max( Double::compareTo ).get())
                .collect(Collectors.toList());

        List<Map<Double, Double>> unitsSamples2 = PartialSumUtils.performSampling( interferogram, s2, e2, period );

        List<Double> ld2 = unitsSamples2
                .parallelStream()
                .map( Map::values )
                .map( collection -> collection.stream().map( Math::abs ).max( Double::compareTo ).get())
                .collect(Collectors.toList());

        double[] resultValues = new double[10];

        for ( int i = 0; i < resultValues.length; i++ ) {
            resultValues[i] = Math.max( ld1.get(i), ld2.get(i) );
        }

        return resultValues;

    }

    private String buildFullExpression() {
        String pattern = "%f/x^4 + " +
                         "%f/x^3 + %f/x^3 + " +
                         "%f/x^2 + %f/x^2 + %f/x^2 + " +
                         "%f/x + %f/x + %f/x + %f/x";

        return String.format( pattern, AMPLITUDES[0], AMPLITUDES[1], AMPLITUDES[2], AMPLITUDES[3], AMPLITUDES[4],
                AMPLITUDES[5], AMPLITUDES[6], AMPLITUDES[7], AMPLITUDES[8], AMPLITUDES[9] );
    }

    private String buildShortExpression() {
        String pattern = "%f/x^4 + %f/x^3 + %f/x^2 + %f/x";
        double X4C = AMPLITUDES[0];
        double X3C = AMPLITUDES[1] + AMPLITUDES[2];
        double X2C = AMPLITUDES[3] + AMPLITUDES[4] + AMPLITUDES[5];
        double X1C = AMPLITUDES[6] + AMPLITUDES[7] + AMPLITUDES[8] + AMPLITUDES[9];

        return String.format( pattern, X4C, X3C, X2C, X1C );
    }

    public double[] getAMPLITUDES() {
        return AMPLITUDES;
    }

    // Make it configurable (possibility to choose full or short expression to display)
    public String toString() {
        return "Full expression: \n" + FULL_EXPRESSION + "\n" + "Short expression: \n" + SHORT_EXPRESSION;
    }

}
