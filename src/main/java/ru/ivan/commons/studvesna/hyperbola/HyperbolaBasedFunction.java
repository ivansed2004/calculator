package ru.ivan.commons.studvesna.hyperbola;

import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.PartialSumUtils;

public class HyperbolaBasedFunction {

    private final double[] AMPLITUDES;

    private final String EXPRESSION;

    public HyperbolaBasedFunction( Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram, 5 );
        this.EXPRESSION = buildExpression();
    }

    public double getValue( double arg ) {
        return AMPLITUDES[0]/Math.pow(arg, 4) +
                AMPLITUDES[1]/Math.pow(arg, 3) +
                AMPLITUDES[2]/Math.pow(arg, 2) +
                AMPLITUDES[3]/Math.pow(arg, 1);
    }

    private String buildExpression() {
        String pattern = "%f/x^4 + %f/x^3 + %f/x^2 + %f/x";
        return String.format( pattern, AMPLITUDES[0], AMPLITUDES[1], AMPLITUDES[2], AMPLITUDES[3] );
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram, int a ) {
        double t = 0.1628895;
        double s1 = 0.0667401 + a*t;
        double s2 = 0.0709894 + a*t;
        double e1 = 0.0942738 + a*t;
        double e2 = 0.0985998 + a*t;
        double period = 0.00001;

        double[] results = new double[4];

        for ( int i = 0; i < 4; i++ ) {
            double res1 = PartialSumUtils.performSampling( interferogram, s1, e1, period, i )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            double res2 = PartialSumUtils.performSampling( interferogram, s2, e2, period, i )
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
