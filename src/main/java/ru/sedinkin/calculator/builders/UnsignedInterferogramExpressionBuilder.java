package ru.sedinkin.calculator.builders;

import ru.sedinkin.calculator.objects.interferogram.IUnit;

public class UnsignedInterferogramExpressionBuilder implements InterferogramExpressionBuilder {

    @Override
    public String buildTrigSum( IUnit[] units ) {
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