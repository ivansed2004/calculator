package ru.ivan.commons.studvesna.objects;

import ru.ivan.commons.studvesna.api.MathObject;

public class Spline extends MathObject {

    private final double START;

    private final double END;

    private double A0;

    private double A1;

    private double A2;

    private double A3;

    public Spline( double start, double end, double a0, double a1, double a2, double a3 ) {
        this.START = start;
        this.END = end;
        this.A0 = a0;
        this.A1 = a1;
        this.A2 = a2;
        this.A3 = a3;
    }

    public Spline(Double start, Double end) {
        this( start, end, 0d, 0d, 0d, 0d );
    }

    public double getValue( double arg ) {
        if ( !(arg >= START && arg <= END) ) {
            return 0.0d;
        }
        return A3*Math.pow(arg, 3) + A2*Math.pow(arg, 2) + A1*Math.pow(arg, 1) + A0;
    }

    public double getSTART() {
        return START;
    }

    public double getEND() {
        return END;
    }

    public double getA0() {
        return A0;
    }

    public void setA0(double a0) {
        this.A0 = a0;
    }

    public double getA1() {
        return A1;
    }

    public void setA1(double a1) {
        this.A1 = a1;
    }

    public double getA2() {
        return A2;
    }

    public void setA2(double a2) {
        this.A2 = a2;
    }

    public double getA3() {
        return A3;
    }

    public void setA3(double a3) {
        this.A3 = a3;
    }

    @Override
    public String toString() {
        return String.format( "%.3fx^3 + %.3fx^2 + %.3fx + %.3f, [%.3f; %.3f]", A3, A2, A1, A0, START, END );
    }

}