package ru.ivan.commons.studvesna.interferogram;

import java.util.Arrays;

public class ExpressionBuilder {

    private final Unit[][] UNITS;

    public ExpressionBuilder( Unit[][] units ) {
        this.UNITS = units;
    }

    public String[] build() {
        return Arrays.stream(UNITS)
                .map(this::buildPartialSumExpression)
                .toArray( String[]::new );
    }

    private String buildPartialSumExpression( Unit[] units ) {
        Unit[] nonNullUnits = cutNullUnits(units);
        String result = "";
        if ( nonNullUnits.length > 0 ) {
            String s = buildTrigSum(nonNullUnits);
            int n = nonNullUnits[0].getN();
            if (n == 1) {
                result = String.format("(%s)/x", s);
            } else {
                result = String.format("(%s)/x^%d", s, n);
            }
        }
        return result;
    }

    // Fix the case with Math.sin(arg) and Math.cos(arg)
    private Unit[] cutNullUnits( Unit[] units ) {

        return Arrays.stream(units).filter( unit -> {
            double amp = unit.getA();
            double arg = unit.getC();
            return ( Math.abs(amp) >= 0.001 ) &&
                    ((unit.isSIGMA() && Math.sin(arg) != 0) || (!unit.isSIGMA() && Math.cos(arg) != 0) );
        } ).toArray( Unit[]::new );

    }

    private String buildTrigSum( Unit[] units ) {
        String result = "";

        for ( int i = 0; i < units.length; i++ ) {
            double A = units[i].getA();
            String s = String.format(
                    "%.3f%s(%.3fx)",
                    Math.abs(A),
                    (units[i].isSIGMA()) ? "sin" : "cos",
                    units[i].getC()
            );

            if (i == 0) {
                result = result.concat( (A < 0) ? "-" : "" ).concat(s);
            } else {
                result = result.concat( (A > 0) ? " + " : " - " ).concat(s);
            }
        }

        return result;
    }

}