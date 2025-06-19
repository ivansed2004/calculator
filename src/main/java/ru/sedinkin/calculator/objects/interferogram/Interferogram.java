package ru.sedinkin.calculator.objects.interferogram;

import ru.sedinkin.calculator.core.MathObject;
import ru.sedinkin.calculator.objects.spline.SUnit;
import ru.sedinkin.calculator.objects.spline.Spline;

import java.util.List;

public class Interferogram extends MathObject {

    private final IUnit[][] IUNITS;

    public Interferogram( Spline sbf ) {
        IUnit[][] fullUnits = derive( sbf );
        this.IUNITS = getCombinedPartialSums( fullUnits );
    }

    private IUnit[][] getCombinedPartialSums(IUnit[][] fullUnits ) {
        IUnit[][] newUnits = new IUnit[4][ fullUnits[0].length ];
        newUnits[0] = combinePartialSumsFrom( fullUnits, new int[]{0} );
        newUnits[1] = combinePartialSumsFrom( fullUnits, new int[]{1, 2} );
        newUnits[2] = combinePartialSumsFrom( fullUnits, new int[]{3, 4, 5} );
        newUnits[3] = combinePartialSumsFrom( fullUnits, new int[]{6, 7, 8, 9} );
        return newUnits;
    }

    private IUnit[] combinePartialSumsFrom(IUnit[][] units, int[] psn ) {
        int size = units[0].length;
        IUnit[] newUnit = new IUnit[size];
        for ( int i = 0; i < size; i++ ) {
            double newAmp = 0;
            for ( int n : psn ) {
                newAmp += Math.round( 1000.0*units[n][i].getA() ) / 1000.0;
            }
            newUnit[i] = new IUnit( Math.round( 1000.0*newAmp ) / 1000.0,
                    units[psn[0]][i].getC(), units[psn[0]][i].getN(), units[psn[0]][i].isSIGMA() );
        }
        return newUnit;
    }

    // Optimize!!!
    private IUnit[][] derive(Spline sbf ) {
        List<SUnit> splines = sbf.getSUNITS();
        List<Double> intLimits = sbf.getIntLimits();
        int SIZE = splines.size();

        IUnit[][] fullUnits = new IUnit[10][SIZE+1];

        fullUnits[0][0] = new IUnit(6*splines.get(0).getA3(), intLimits.get(0), (byte) 4, false);
        fullUnits[0][SIZE] = new IUnit(-6*splines.get(SIZE-1).getA3(), intLimits.get(SIZE), (byte) 4, false);

        fullUnits[1][0] = new IUnit(6*splines.get(0).getA3()*intLimits.get(0), intLimits.get(0), (byte) 3, true);
        fullUnits[1][SIZE] = new IUnit(-6*splines.get(SIZE-1).getA3()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 3, true);
        fullUnits[2][0] = new IUnit(2*splines.get(0).getA2(), intLimits.get(0), (byte) 3, true);
        fullUnits[2][SIZE] = new IUnit(-2*splines.get(SIZE-1).getA2(), intLimits.get(SIZE), (byte) 3, true);

        fullUnits[3][0] = new IUnit(-3*splines.get(0).getA3()*Math.pow(intLimits.get(0), 2), intLimits.get(0), (byte) 2, false);
        fullUnits[3][SIZE] = new IUnit(3*splines.get(SIZE-1).getA3()*Math.pow(intLimits.get(SIZE), 2), intLimits.get(SIZE), (byte) 2, false);
        fullUnits[4][0] = new IUnit(-2*splines.get(0).getA2()*intLimits.get(0), intLimits.get(0), (byte) 2, false);
        fullUnits[4][SIZE] = new IUnit(2*splines.get(SIZE-1).getA2()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 2, false);
        fullUnits[5][0] = new IUnit(-splines.get(0).getA1(), intLimits.get(0), (byte) 2, false);
        fullUnits[5][SIZE] = new IUnit(splines.get(SIZE-1).getA1(), intLimits.get(SIZE), (byte) 2, false);

        fullUnits[6][0] = new IUnit(-splines.get(0).getA3()*Math.pow(intLimits.get(0), 3), intLimits.get(0), (byte) 1, true);
        fullUnits[6][SIZE] = new IUnit(splines.get(SIZE-1).getA3()*Math.pow(intLimits.get(SIZE), 3), intLimits.get(SIZE), (byte) 1, true);
        fullUnits[7][0] = new IUnit(-splines.get(0).getA2()*Math.pow(intLimits.get(0), 2), intLimits.get(0), (byte) 1, true);
        fullUnits[7][SIZE] = new IUnit(splines.get(SIZE-1).getA2()*Math.pow(intLimits.get(SIZE), 2), intLimits.get(SIZE), (byte) 1, true);
        fullUnits[8][0] = new IUnit(-splines.get(0).getA1()*intLimits.get(0), intLimits.get(0), (byte) 1, true);
        fullUnits[8][SIZE] = new IUnit(splines.get(SIZE-1).getA1()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 1, true);
        fullUnits[9][0] = new IUnit(-splines.get(0).getA0(), intLimits.get(0), (byte) 1, true);
        fullUnits[9][SIZE] = new IUnit(splines.get(SIZE-1).getA0(), intLimits.get(SIZE), (byte) 1, true);
        for ( int i = 1; i < SIZE; i++ ) {

            fullUnits[0][i] = new IUnit(6*(splines.get(i).getA3() - splines.get(i-1).getA3()), intLimits.get(i), (byte) 4, false);
            fullUnits[1][i] = new IUnit(6*(splines.get(i).getA3() - splines.get(i-1).getA3())*intLimits.get(i), intLimits.get(i), (byte) 3, true);
            fullUnits[2][i] = new IUnit(2*(splines.get(i).getA2() - splines.get(i-1).getA2()), intLimits.get(i), (byte) 3, true);
            fullUnits[3][i] = new IUnit(-3*(splines.get(i).getA3() - splines.get(i-1).getA3())*Math.pow(intLimits.get(i), 2), intLimits.get(i), (byte) 2, false);
            fullUnits[4][i] = new IUnit(-2*(splines.get(i).getA2() - splines.get(i-1).getA2())*intLimits.get(i), intLimits.get(i), (byte) 2, false);
            fullUnits[5][i] = new IUnit(-(splines.get(i).getA1() - splines.get(i-1).getA1()), intLimits.get(i), (byte) 2, false);
            fullUnits[6][i] = new IUnit(-(splines.get(i).getA3() - splines.get(i-1).getA3())*Math.pow(intLimits.get(i), 3), intLimits.get(i), (byte) 1, true);
            fullUnits[7][i] = new IUnit(-(splines.get(i).getA2() - splines.get(i-1).getA2())*Math.pow(intLimits.get(i), 2), intLimits.get(i), (byte) 1, true);
            fullUnits[8][i] = new IUnit(-(splines.get(i).getA1() - splines.get(i-1).getA1())*intLimits.get(i), intLimits.get(i), (byte) 1, true);
            fullUnits[9][i] = new IUnit(-(splines.get(i).getA0() - splines.get(i-1).getA0()), intLimits.get(i), (byte) 1, true);

        }

        return fullUnits;
    }

    @Override
    public double getValue( double arg ) {
        return  evaluate( 0, arg ) / Math.pow(arg, 4) +
                evaluate( 1, arg ) / Math.pow(arg, 3) +
                evaluate( 2, arg ) / Math.pow(arg, 2) +
                evaluate( 3, arg ) / Math.pow(arg, 1);
    }

    public double evaluate( int i, double arg ) {
        double value = 0.0d;
        if ( !IUNITS[i][0].isSIGMA() ) {
            for (int j = 0; j < IUNITS[0].length; j++ ) {
                value += IUNITS[i][j].getA() * Math.cos(IUNITS[i][j].getC() * arg);
            }
        } else {
            for (int j = 0; j < IUNITS[0].length; j++ ) {
                value += IUNITS[i][j].getA() * Math.sin(IUNITS[i][j].getC() * arg);
            }
        }
        return value;
    }

    public IUnit[][] getIUNITS() {
        return IUNITS;
    }

}
