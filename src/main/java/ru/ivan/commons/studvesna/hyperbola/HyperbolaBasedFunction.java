package ru.ivan.commons.studvesna.hyperbola;

import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.partialSum.PartialSumUtils;

public class HyperbolaBasedFunction {

    private final double[] AMPLITUDES;

    private final String EXPRESSION;

    public HyperbolaBasedFunction( Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram, 5 );
        this.EXPRESSION = buildExpression();
    }

    private String buildExpression() {
        String pattern = "%f/x^4 + " +
                         "%f/x^3 + %f/x^3 + " +
                         "%f/x^2 + %f/x^2 + %f/x^2 + " +
                         "%f/x + %f/x + %f/x + %f/x";

        return String.format( pattern, AMPLITUDES[0], AMPLITUDES[1], AMPLITUDES[2], AMPLITUDES[3], AMPLITUDES[4],
                AMPLITUDES[5], AMPLITUDES[6], AMPLITUDES[7], AMPLITUDES[8], AMPLITUDES[9] );
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram, int a ) {
        double t = 0.1628895;
        double s1 = 0.0667401 + a*t;
        double s2 = 0.0709894 + a*t;
        double e1 = 0.0942738 + a*t;
        double e2 = 0.0985998 + a*t;
        double period = 0.0001;

        double[] results = new double[10];

        for ( int i = 0; i < 10; i++ ) {
            double res1 = PartialSumUtils.performSampling( interferogram, s1, e1, period, new int[]{i} )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            double res2 = PartialSumUtils.performSampling( interferogram, s2, e2, period, new int[]{i} )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            results[i] = Math.max( res1, res2 );
        }

        return results;
    }

    public double[] getAMPLITUDES() {
        return AMPLITUDES;
    }

    // Make it configurable (possibility to choose full or short expression to display)
    public String toString() {
        return EXPRESSION;
    }

}
