package ru.ivan.commons.studvesna.interferogram;

import java.util.Arrays;

public class Interferogram {

    private final Unit[][] UNITS;

    private final String MATRIX;

    private final String[] MAX_AMPLITUDES;

    // Immediately or on demand?
    public Interferogram( Unit[][] results ) {
        this.UNITS = results;
        this.MATRIX = calculateMatrix();
        this.MAX_AMPLITUDES = calculateMaxAmplitudes();
    }

    private String calculateMatrix() {
        String result = "";
        for ( Unit[] units : UNITS ) {
            result = result.concat(
                    Arrays.toString(
                            Arrays.stream(units)
                                    .map( u -> String.format("%.3f", Math.round( u.getA()*1000 ) / 1000.0) )
                                    .toArray()
                    )
            );
            result = result.concat("\n");
        }
        return result;
    }

    private String[] calculateMaxAmplitudes() {
        String[] result = new String[10];
        int i = 0;
        for ( Unit[] units : UNITS ) {
            Double value = Arrays.stream(units)
                    .map( u -> Math.abs(Math.round( u.getA()*1000.0 ) / 1000.0) )
                    .reduce(0.0, Double::sum);
            result[i] = String.format("%.8f", value);
            i++;
        }
        return result;
    }

    public double evaluate( int[] ints, double arg ) {
        double v = 0.0d;
        for ( int i : ints ) {
            if ( !UNITS[i][0].isSIGMA() ) {
                for ( int j = 0; j < UNITS[0].length; j++ ) {
                    v += UNITS[i][j].getA() * Math.cos(UNITS[i][j].getC() * arg);
                }
            } else {
                for ( int j = 0; j < UNITS[0].length; j++ ) {
                    v += UNITS[i][j].getA() * Math.sin(UNITS[i][j].getC() * arg);
                }
            }
        }
        return v;
    }

    public Unit[][] getUNITS() {
        return UNITS;
    }

    public String[] getMAX_AMPLITUDES() {
        return MAX_AMPLITUDES;
    }

    public Double getValue(Double arg) {
        double value = 0.0d;

        value += evaluate( new int[]{0}, arg ) / Math.pow(arg, 4);
        value += evaluate( new int[]{1, 2}, arg ) / Math.pow(arg, 3);
        value += evaluate( new int[]{3, 4, 5}, arg ) / Math.pow(arg, 2);
        value += evaluate( new int[]{6, 7, 8, 9}, arg ) / Math.pow(arg, 1);

        return value;
    }

    @Override
    public String toString() {
        return MATRIX;
    }

}
