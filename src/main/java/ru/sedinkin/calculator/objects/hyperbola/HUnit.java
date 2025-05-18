package ru.sedinkin.calculator.objects.hyperbola;

import ru.sedinkin.calculator.core.MathObject;

public class HUnit extends MathObject {

    private final double A;

    private final byte N;

    public HUnit( double A, byte n ) {
        this.A = A;
        this.N = n;
    }

    @Override
    public double getValue( double arg ) {
        return A / Math.pow( arg, N );
    }

    public double getA() {
        return A;
    }

    public byte getN() {
        return N;
    }

    public String toString() {
        return ( N > 1 ) ? String.format( "%.3f/x^%d", A, N ) : String.format( "%.3f/x", A);
    }

}
