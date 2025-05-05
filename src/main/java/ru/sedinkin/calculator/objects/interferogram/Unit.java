package ru.sedinkin.calculator.objects.interferogram;

import ru.sedinkin.calculator.api.MathObject;

public class Unit extends MathObject {

    private final double A;

    private final double C;

    private final byte N;

    private final boolean SIGMA;

    public Unit( double A, double C, byte n, boolean sigma ) {
        this.A = A;
        this.C = C;
        this.N = n;
        this.SIGMA = sigma;
    }

    public double getValue( double arg ) {
        return ( SIGMA ) ?
                A*Math.sin(C*arg) /Math.pow(arg, N) :
                A*Math.cos(C*arg) /Math.pow(arg, N);
    }

    public double getA() {
        return A;
    }

    public double getC() {
        return C;
    }

    public byte getN() {
        return N;
    }

    public boolean isSIGMA() {
        return SIGMA;
    }

    public String toString() {
        return ( SIGMA ) ? String.format("%fsin(%fx)/x^%d", A, C, N) : String.format("%fcos(%fx)/x^%d", A, C, N);
    }

}
