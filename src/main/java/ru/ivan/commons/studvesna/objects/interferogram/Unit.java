package ru.ivan.commons.studvesna.objects.interferogram;

public class Unit {

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
        return SIGMA ? String.format("%fsin(%fx)/x^%d", A, C, N) : String.format("%fcos(%fx)/x^%d", A, C, N);
    }

}
