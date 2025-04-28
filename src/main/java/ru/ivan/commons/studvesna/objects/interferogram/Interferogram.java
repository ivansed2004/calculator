package ru.ivan.commons.studvesna.objects.interferogram;

import ru.ivan.commons.studvesna.api.MathObject;

import java.util.List;

public class Interferogram extends MathObject {

    private final Unit[][] UNITS;

    private final List<String> PATTERNS;

    public Interferogram( Unit[][] units, List<String> patterns ) {
        this.UNITS = getCombinedPartialSums( units );
        this.PATTERNS = patterns;
    }

    private Unit[][] getCombinedPartialSums( Unit[][] units ) {
        Unit[][] newUnits = new Unit[4][ units[0].length ];
        newUnits[0] = combinePartialSumsFrom( units, new int[]{0} );
        newUnits[1] = combinePartialSumsFrom( units, new int[]{1, 2} );
        newUnits[2] = combinePartialSumsFrom( units, new int[]{3, 4, 5} );
        newUnits[3] = combinePartialSumsFrom( units, new int[]{6, 7, 8, 9} );
        return newUnits;
    }

    private Unit[] combinePartialSumsFrom( Unit[][] units, int[] psn ) {
        int size = units[0].length;
        Unit[] newUnit = new Unit[size];
        for ( int i = 0; i < size; i++ ) {
            double newAmp = 0;
            for ( int n : psn ) {
                newAmp += Math.round( 1000.0*units[n][i].getA() ) / 1000.0;
            }
            newUnit[i] = new Unit( newAmp,
                    units[psn[0]][i].getC(), units[psn[0]][i].getN(), units[psn[0]][i].isSIGMA() );
        }
        return newUnit;
    }

    @Override
    public double getValue( double arg ) {
        return  evaluate( 0, arg ) / Math.pow(arg, 4) +
                evaluate( 1, arg ) / Math.pow(arg, 3) +
                evaluate( 2, arg ) / Math.pow(arg, 2) +
                evaluate( 3, arg ) / Math.pow(arg, 1);
    }

    public void addPattern( String pattern ) {
        this.PATTERNS.add( pattern );
    }

    @Override
    public List<String> getPatterns() {
        return PATTERNS;
    }

    public double evaluate( int i, double arg ) {
        double value = 0.0d;
        if ( !UNITS[i][0].isSIGMA() ) {
            for ( int j = 0; j < UNITS[0].length; j++ ) {
                value += UNITS[i][j].getA() * Math.cos(UNITS[i][j].getC() * arg);
            }
        } else {
            for ( int j = 0; j < UNITS[0].length; j++ ) {
                value += UNITS[i][j].getA() * Math.sin(UNITS[i][j].getC() * arg);
            }
        }
        return value;
    }

    public Unit[][] getUNITS() {
        return UNITS;
    }

}
