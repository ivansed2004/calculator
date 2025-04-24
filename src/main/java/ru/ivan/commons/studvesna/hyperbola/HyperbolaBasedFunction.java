package ru.ivan.commons.studvesna.hyperbola;

import ru.ivan.commons.studvesna.interferogram.Interferogram;
import ru.ivan.commons.studvesna.interferogram.partialSum.PartialSumUtils;

public class HyperbolaBasedFunction {

    private final double[] AMPLITUDES;

    private final String EXPRESSION;

    public HyperbolaBasedFunction( Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram );
        this.EXPRESSION = buildExpression();
    }

    private String buildExpression() {
        return String.format( "%f/x^3 + %f/x^2 + %f/x", AMPLITUDES[0], AMPLITUDES[1], AMPLITUDES[2] );
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram ) {
        double[] results = new double[3];

        PartialSumUtils
                .performSampling( interferogram, 0.00417126, 0.00417126, 0.00417126, 0.0, 1.0, new int[]{1, 2} )
                .values().stream().map( v -> Math.round( v*1000 ) / 1000.0 ).max( Double::compareTo ).get();
        PartialSumUtils
                .performSampling( interferogram, 0.0, 1.6288795, 0.0043934, 0.0043934/4, 0.0043934/256, new int[]{3, 4, 5} )
                .values().stream().map( v -> Math.round( v*1000 ) / 1000.0 ).max( Double::compareTo ).get();
        PartialSumUtils
                .performSampling( interferogram, 0.0010991, 1.6288795, 0.00439641, 0.00439641/1024, 0.00439641/(1024*16), new int[]{6, 7, 8, 9} )
                .values().stream().map( v -> Math.round( v*1000 ) / 1000.0 ).max( Double::compareTo ).get();

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
