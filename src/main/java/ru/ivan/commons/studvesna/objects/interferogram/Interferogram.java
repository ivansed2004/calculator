package ru.ivan.commons.studvesna.objects.interferogram;

import java.util.Arrays;
import java.util.Objects;

public class Interferogram {

    private final Unit[][] UNITS;

    private final Unit[][] COMBINED_UNITS;

    private final String MATRIX;

    // Immediately or on demand?
    public Interferogram( Unit[][] results ) {
        this.UNITS = results;
        this.COMBINED_UNITS = getCombinedPartialSums();
        this.MATRIX = calculateMatrix();
    }

    private String calculateMatrix() {
        String result = "";
        for ( Unit[] units : UNITS ) {
            result = result.concat(
                    Arrays.toString(
                            Arrays.stream(units)
                                    .map( u -> String.format("%.3f", u.getA()) )
                                    .toArray()
                    )
            );
            result = result.concat("\n");
        }
        return result;
    }

    private Unit[][] getCombinedPartialSums() {
        Unit[][] newUnits = new Unit[4][ UNITS[0].length ];
        newUnits[0] = combinePartialSumsFrom( new int[]{0} );
        newUnits[1] = combinePartialSumsFrom( new int[]{1, 2} );
        newUnits[2] = combinePartialSumsFrom( new int[]{3, 4, 5} );
        newUnits[3] = combinePartialSumsFrom( new int[]{6, 7, 8, 9} );
        return newUnits;
    }

    private Unit[] combinePartialSumsFrom( int[] psn ) {
        int size = UNITS[0].length;
        Unit[] newUnit = new Unit[size];
        for ( int i = 0; i < size; i++ ) {
            double newAmp = 0;
            for ( int n : psn ) {
                newAmp += Math.round( 1000.0*UNITS[n][i].getA() ) / 1000.0;
            }
            newUnit[i] = new Unit( newAmp,
                    UNITS[psn[0]][i].getC(), UNITS[psn[0]][i].getN(), UNITS[psn[0]][i].isSIGMA() );
        }
        return newUnit;
    }

    public Double getValue(Double arg, String mode) {
        double value = 0.0d;

        for ( int i = 0; i < 10; i++ ) {
            if (Objects.equals(mode, "default")) {
                if ( i == 0 ) {
                    value += evaluate( i, arg, "default" ) / Math.pow(arg, 4);
                } else if ( i <= 2 ) {
                    value += evaluate( i, arg, "default" ) / Math.pow(arg, 3);
                } else if ( i <= 5) {
                    value += evaluate( i, arg, "default" ) / Math.pow(arg, 2);
                } else {
                    value += evaluate( i, arg, "default" ) / Math.pow(arg, 1);
                }
            } else {
                value += evaluate( i, arg, "combined" ) / Math.pow(arg, 4);
                value += evaluate( i, arg, "combined" ) / Math.pow(arg, 3);
                value += evaluate( i, arg, "combined" ) / Math.pow(arg, 2);
                value += evaluate( i, arg, "combined" ) / Math.pow(arg, 1);
            }
        }

        return value;
    }

    public double evaluate( int i, double arg, String mode ) {
        double v = 0.0d;
        if ( Objects.equals(mode, "default") ) {
            if ( !UNITS[i][0].isSIGMA() ) {
                for ( int j = 0; j < UNITS[0].length; j++ ) {
                    v += UNITS[i][j].getA() * Math.cos(UNITS[i][j].getC() * arg);
                }
            } else {
                for ( int j = 0; j < UNITS[0].length; j++ ) {
                    v += UNITS[i][j].getA() * Math.sin(UNITS[i][j].getC() * arg);
                }
            }
        } else if ( Objects.equals(mode, "combined") ) {
            if ( !COMBINED_UNITS[i][0].isSIGMA() ) {
                for ( int j = 0; j < COMBINED_UNITS[0].length; j++ ) {
                    v += COMBINED_UNITS[i][j].getA() * Math.cos(COMBINED_UNITS[i][j].getC() * arg);
                }
            } else {
                for ( int j = 0; j < COMBINED_UNITS[0].length; j++ ) {
                    v += COMBINED_UNITS[i][j].getA() * Math.sin(COMBINED_UNITS[i][j].getC() * arg);
                }
            }
        }
        return v;
    }

    public Unit[][] getUNITS() {
        return UNITS;
    }

    public Unit[][] getCOMBINED_UNITS() {
        return COMBINED_UNITS;
    }

    @Override
    public String toString() {
        return MATRIX;
    }

}
