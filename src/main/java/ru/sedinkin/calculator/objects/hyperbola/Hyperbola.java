package ru.sedinkin.calculator.objects.hyperbola;

import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;

import java.util.Arrays;
import java.util.OptionalDouble;

public class Hyperbola extends MathObject {

    private final HUnit[] HUNITS;

    public Hyperbola( Interferogram interferogram ) {
        this.HUNITS = Arrays.stream( interferogram.getUNITS() )
                .flatMap( Arrays::stream )
                .map( unit -> {
                    double A = Math.abs(unit.getA());
                    byte n = unit.getN();
                    return new HUnit( A, n );
                } )
                .toArray( HUnit[]::new );
    }

    @Override
    public double getValue( double arg ) {
        OptionalDouble optional = Arrays.stream( HUNITS )
                .mapToDouble( HUnit::getA )
                .reduce( Double::sum );
        return optional.isPresent() ? optional.getAsDouble() : 0;
    }

    public HUnit[] getHUNITS() {
        return HUNITS;
    }
}
