package ru.ivan.commons.studvesna.objects.splines;

import ru.ivan.commons.studvesna.api.MathObject;
import java.util.List;

public class SplineBasedFunction extends MathObject {

    private final List<Spline> SPLINES;

    private final List<String> PATTERNS;

    public SplineBasedFunction( List<Spline> splines, List<String> patterns ) {
        this.SPLINES = splines;
        this.PATTERNS = patterns;
    }

    @Override
    public double getValue( double arg ) {
        double value = 0.0d;
        for ( Spline spline : SPLINES ) {
            value += spline.getValue(arg);
        }
        return value;
    }

    public void addPattern( String pattern ) {
        this.PATTERNS.add( pattern);
    }

    @Override
    public List<String> getPatterns() {
        return PATTERNS;
    }

    public List<Spline> getSPLINES() {
        return SPLINES;
    }

}