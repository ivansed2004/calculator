package ru.ivan.commons.studvesna.hyperbola;

import ru.ivan.commons.studvesna.interferogram.Interferogram;

public class HyperbolaBasedFunction {

    private final double[] AMPLITUDES;

    private final String EXPRESSION;

    public HyperbolaBasedFunction( Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram );
        this.EXPRESSION = buildExpression();
    }

    private String buildExpression() {
        return "";
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram ) {
        double[] results = new double[3];
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
