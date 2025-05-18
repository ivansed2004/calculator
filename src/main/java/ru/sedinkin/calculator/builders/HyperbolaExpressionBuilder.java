package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.core.ExpressionBuilder;
import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.hyperbola.HUnit;
import ru.sedinkin.calculator.objects.hyperbola.Hyperbola;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HyperbolaExpressionBuilder implements ExpressionBuilder {

    @Override
    public List<String> perform( MathObject input, Map<String, Object> metadata ) {
        HUnit[] hUnits = Arrays.stream(((Hyperbola) input).getHUNITS())
                .filter( hUnit -> Math.abs(hUnit.getA()) >= 0.001 )
                .toArray( HUnit[]::new );
        String result = "";
        for ( int i = 0; i < hUnits.length; i++ ) {
            if (i == 0) {
                result = result.concat( hUnits[i].toString() );
            } else {
                result = result.concat(" + ").concat( hUnits[i].toString() );
            }
        }
        return List.of( result );
    }

}
