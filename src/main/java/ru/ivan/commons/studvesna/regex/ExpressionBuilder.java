package ru.ivan.commons.studvesna.regex;

import ru.ivan.commons.studvesna.interferogram.Unit;
import java.util.Arrays;

public class ExpressionBuilder {

    private final Unit[][] UNITS;

    public ExpressionBuilder( Unit[][] units ) {
        this.UNITS = units;
    }

    public String[] buildExpression() {
        return (String[]) Arrays.stream(UNITS)
                .map(this::buildPartialSumExpression)
                .toArray();
    }

    private String buildPartialSumExpression( Unit[] units ) {
        Unit[] nonNullUnits = cutNullUnits(units);
        String result = "";
        if ( nonNullUnits.length > 0 ) {
            String s = buildTrigSum(nonNullUnits);
            int n = nonNullUnits[0].getN();
            result = String.format("(%s)/x^%d", s, n);
        }
        return result;
    }

    private Unit[] cutNullUnits( Unit[] units ) {

        return (Unit[]) Arrays.stream(units).filter( unit -> {
            double amp = unit.getA();
            double arg = unit.getC();
            return (amp >= 0.001) &&
                    ((unit.isSIGMA() && Math.sin(arg) != 0) || (!unit.isSIGMA() && Math.cos(arg) != 0) );
        } ).toArray();

    }

    private String buildTrigSum( Unit[] units ) {
        String result = "";

        for ( int i = 0; i < units.length; i++ ) {
            // consider use of the toString() value of Unit class
            String s = String.format(
                    "%.3f%s(%.3fx)",
                    units[i].getA(),
                    (units[i].isSIGMA()) ? "sin" : "cos",
                    units[i].getC()
            );

            if (i == 0) {
                result = result.concat(s);
            } else {
                result = result.concat(" + ").concat(s);
            }
        }

        return result;
    }

}
