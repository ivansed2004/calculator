package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.interfaces.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;
import ru.sedinkin.calculator.objects.interferogram.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnsignedInterferogramExpressionBuilder extends SignedInterferogramExpressionBuilder {

    @Override
    public List<String> perform( MathObject input, Map<String, Object> metadata ) {
        return Arrays.stream( ((Interferogram) input).getUNITS() )
                .map(this::buildPartialSumExpression)
                .collect(Collectors.toList());
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

    private Unit[] cutNullUnits( Unit[] units ) {
        return Arrays.stream(units).filter( unit -> {
            double amp = unit.getA();
            return ( Math.abs(amp) >= 0.001 );
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
                result = result.concat(s);
            } else {
                result = result.concat(" + ").concat(s);
            }
        }

        return result;
    }

}
