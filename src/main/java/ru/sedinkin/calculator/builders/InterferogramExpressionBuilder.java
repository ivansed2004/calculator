package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.core.ExpressionBuilder;
import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;
import ru.sedinkin.calculator.objects.interferogram.Unit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface InterferogramExpressionBuilder extends ExpressionBuilder {

    default List<String> perform(MathObject input, Map<String, Object> metadata ) {
        return Arrays.stream( ((Interferogram) input).getUNITS() )
                .map(this::buildPartialSumExpression)
                .collect(Collectors.toList());
    }

    default String buildPartialSumExpression( Unit[] units ) {
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

    default Unit[] cutNullUnits( Unit[] units ) {
        return Arrays.stream(units).filter(unit -> {
            double amp = unit.getA();
            return ( Math.abs(amp) >= 0.001 );
        } ).toArray( Unit[]::new );
    }

    String buildTrigSum( Unit[] units );

}
