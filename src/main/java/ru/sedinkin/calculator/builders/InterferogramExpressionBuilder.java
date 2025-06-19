package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.core.ExpressionBuilder;
import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;
import ru.sedinkin.calculator.objects.interferogram.IUnit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface InterferogramExpressionBuilder extends ExpressionBuilder {

    default List<String> perform(MathObject input, Map<String, Object> metadata ) {
        return Arrays.stream( ((Interferogram) input).getIUNITS() )
                .map(this::buildPartialSumExpression)
                .collect(Collectors.toList());
    }

    default String buildPartialSumExpression( IUnit[] units ) {
        IUnit[] nonNullUnits = cutNullUnits(units);
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

    default IUnit[] cutNullUnits(IUnit[] units ) {
        return Arrays.stream(units).filter(unit -> {
            double amp = unit.getA();
            return ( Math.abs(amp) >= 0.001 );
        } ).toArray( IUnit[]::new );
    }

    String buildTrigSum( IUnit[] units );

}
