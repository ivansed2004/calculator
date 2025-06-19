package ru.sedinkin.calculator.objects.spline;

import ru.sedinkin.calculator.core.MathObject;

import java.util.ArrayList;
import java.util.List;

public class Spline extends MathObject {

    private final List<SUnit> SUNITS;

    public Spline(List<SUnit> splines ) {
        this.SUNITS = splines;
    }

    public List<Double> getIntLimits() {
        List<Double> intLimits = new ArrayList<>();
        intLimits.add(SUNITS.get(0).getSTART());
        for ( SUnit s : SUNITS) {
            intLimits.add( s.getEND() );
        }
        return intLimits;
    }

    @Override
    public double getValue( double arg ) {
        double value = 0.0d;
        for ( SUnit spline : SUNITS) {
            value += spline.getValue(arg);
        }
        return value;
    }

    public List<SUnit> getSUNITS() {
        return SUNITS;
    }

}