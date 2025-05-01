package ru.ivan.calculator.objects.spline;

import ru.ivan.calculator.api.MathObject;

import java.util.ArrayList;
import java.util.List;

public class SplineBasedFunction extends MathObject {

    private final List<Spline> SPLINES;

    public SplineBasedFunction( List<Spline> splines ) {
        this.SPLINES = splines;
    }

    public List<Double> getIntLimits() {
        List<Double> intLimits = new ArrayList<>();
        intLimits.add(SPLINES.get(0).getSTART());
        for ( Spline s : SPLINES ) {
            intLimits.add( s.getEND() );
        }
        return intLimits;
    }

    @Override
    public double getValue( double arg ) {
        double value = 0.0d;
        for ( Spline spline : SPLINES ) {
            value += spline.getValue(arg);
        }
        return value;
    }

    public List<Spline> getSPLINES() {
        return SPLINES;
    }

}