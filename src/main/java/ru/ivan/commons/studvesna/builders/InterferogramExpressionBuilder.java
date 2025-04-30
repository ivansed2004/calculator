package ru.ivan.commons.studvesna.builders;

import ru.ivan.commons.studvesna.api.ExpressionBuilder;
import ru.ivan.commons.studvesna.api.MathObject;
import ru.ivan.commons.studvesna.objects.interferogram.Interferogram;
import ru.ivan.commons.studvesna.objects.interferogram.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InterferogramExpressionBuilder implements ExpressionBuilder {

    public List<String> perform( MathObject input, Map<String, Object> metadata ) {
        return Arrays.stream( ((Interferogram) input).getUNITS() )
                .map(this::buildPartialSumExpression)
                .collect(Collectors.toList());
    }

    private String buildPartialSumExpression( Unit[] units ) {
        String result = "";
        if ( units.length > 0 ) {
            String s = buildTrigSum(units);
            int n = units[0].getN();
            if (n == 1) {
                result = String.format("(%s)/x", s);
            } else {
                result = String.format("(%s)/x^%d", s, n);
            }
        }
        return result;
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