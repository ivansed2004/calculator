package ru.ivan.calculator.builders;

import ru.ivan.calculator.api.ExpressionBuilder;
import ru.ivan.calculator.api.MathObject;
import ru.ivan.calculator.objects.Hyperbola;

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
