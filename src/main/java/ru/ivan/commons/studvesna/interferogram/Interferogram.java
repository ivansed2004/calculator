package ru.ivan.commons.studvesna.interferogram;

public class Interferogram {

    private final Unit[][] UNITS;

    public Interferogram( Unit[][] results ) {
        this.UNITS = results;
    }

    public double evaluate( int i, double arg ) {
        double v = 0.0d;
        for ( int j = 0; j < UNITS[0].length; j++ ) {
            if ( !UNITS[i][0].isSIGMA() ) {
                v += UNITS[i][j].getA()*Math.cos(UNITS[i][j].getC()*arg);
            } else {
                v += UNITS[i][j].getA()*Math.sin(UNITS[i][j].getC()*arg);
            }
        }
        return v;
    }

    public Unit[][] getUNITS() {
        return UNITS;
    }

    public Double getValue(Double arg) {

        double value = 0.0d;

        value += evaluate( 0, arg ) / Math.pow(arg, 4);
        value += evaluate( 1, arg ) / Math.pow(arg, 3);
        value += evaluate( 2, arg ) / Math.pow(arg, 3);
        value += evaluate( 3, arg ) / Math.pow(arg, 2);
        value += evaluate( 4, arg ) / Math.pow(arg, 2);
        value += evaluate( 5, arg ) / Math.pow(arg, 2);
        value += evaluate( 6, arg ) / Math.pow(arg, 1);
        value += evaluate( 7, arg ) / Math.pow(arg, 1);
        value += evaluate( 8, arg ) / Math.pow(arg, 1);
        value += evaluate( 9, arg ) / Math.pow(arg, 1);

        return value;

    }

}
