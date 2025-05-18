package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.core.ExpressionBuilder;
import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.Hyperbola;

import java.util.List;
import java.util.Map;

public class HyperbolaExpressionBuilder implements ExpressionBuilder {

    @Override
    public List<String> perform( MathObject input, Map<String, Object> metadata ) {
        double[] amp = ((Hyperbola) input).getAMPLITUDES();
        return List.of(
                String.format( "%f/x^4 + %f/x^3 + %f/x^2 + %f/x", amp[0], amp[1], amp[2], amp[3] )
        );
    }

}
