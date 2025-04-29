package ru.ivan.commons.studvesna.objects.hyperbola;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import ru.ivan.commons.studvesna.api.ExpressionBuilder;
import ru.ivan.commons.studvesna.api.MathObject;

import java.util.List;

public class HyperbolaExpressionBuilder implements ExpressionBuilder {

    @Override
    public List<String> perform( MathObject input, ActionMetadata metadata ) {
        double[] amp = ((Hyperbola) input).getAMPLITUDES();
        return List.of(
                String.format( "%f/x^4 + %f/x^3 + %f/x^2 + %f/x", amp[0], amp[1], amp[2], amp[3] )
        );
    }

}
