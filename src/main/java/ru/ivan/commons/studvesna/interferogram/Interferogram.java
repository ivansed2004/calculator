package ru.ivan.commons.studvesna.interferogram;

import java.util.List;
import java.util.Objects;

public class Interferogram {

    private final List<Double> LIMITS;

    private final Unit[][] UNITS;

    public Interferogram(List<Double> limits, Unit[][] results) {
        this.LIMITS = limits;
        this.UNITS = results;
    }

    private static double evaluate( Unit[][] units, int i, double arg, String func ) {
        double v = 0.0d;
        for ( int j = 0; j < units[0].length; j++ ) {
            if ( Objects.equals(func, "cos") ) {
                v += units[i][j].getA()*Math.cos(units[i][j].getC()*arg);
            } else {
                v += units[i][j].getA()*Math.sin(units[i][j].getC()*arg);
            }
        }
        return v;
    }

    public List<Double> getLIMITS() {
        return LIMITS;
    }

    public Unit[][] getUNITS() {
        return UNITS;
    }

    public Double getValue(Double arg) {

        double value = 0.0d;

        value += evaluate(UNITS, 0, arg, "cos" ) / Math.pow(arg, 4);
        value += evaluate(UNITS, 1, arg, "sin" ) / Math.pow(arg, 3);
        value += evaluate(UNITS, 2, arg, "sin" ) / Math.pow(arg, 3);
        value += evaluate(UNITS, 3, arg, "cos" ) / Math.pow(arg, 2);
        value += evaluate(UNITS, 4, arg, "cos" ) / Math.pow(arg, 2);
        value += evaluate(UNITS, 5, arg, "cos" ) / Math.pow(arg, 2);
        value += evaluate(UNITS, 6, arg, "sin" ) / arg;
        value += evaluate(UNITS, 7, arg, "sin" ) / arg;
        value += evaluate(UNITS, 8, arg, "sin" ) / arg;
        value += evaluate(UNITS, 9, arg, "sin" ) / arg;

        return value;

    }

}
