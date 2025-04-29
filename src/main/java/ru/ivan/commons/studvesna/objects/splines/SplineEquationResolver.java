package ru.ivan.commons.studvesna.objects.splines;

import org.apache.commons.math3.linear.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SplineEquationResolver {

    // This method should be public only
    public static SplineBasedFunction resolve( File file, int period ) {

        List<Double> X = new ArrayList<>();
        List<Double> Y = new ArrayList<>();

        try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) ) {

            String line;
            int p = period;
            double lastX = 0d;
            double lastY = 0d;
            while ( ( line = reader.readLine() ) != null ) {

                String[] lines = line.split("\t");
                if ( p == period ) {
                    X.add( Double.valueOf( lines[0] ) );
                    Y.add( Double.valueOf( lines[1] ) );
                    p = 0;
                }

                if ( period != 0 ) {
                    p++;
                }

                lastX = Double.parseDouble( lines[0] );
                lastY = Double.parseDouble( lines[1] );

            }

            X.add( lastX );
            Y.add( lastY );

        } catch (IOException e) {
            System.err.println( "Error occurred while reading the file: " + e.getMessage() );
        }

        double[] vectorX = X.stream()
                .mapToDouble( Double::doubleValue )
                .toArray();
        double[] vectorY = Y.stream()
                .mapToDouble( Double::doubleValue )
                .toArray();

        RealVector solution = resolveSplineEquation( vectorX, vectorY );
        double[] answers = solution.toArray();

        return new SplineBasedFunction( getResultSplines( X, answers ) );

    }

    private static List<Spline> getResultSplines( List<Double> vectorX, double[] answers ) {

        List<Spline> splines = new ArrayList<>();

        for ( int i = 1; i < vectorX.size(); i++ ) {
            Spline s = new Spline( vectorX.get(i - 1), vectorX.get(i) );
            splines.add( s );
        }

        int i = 0;
        int j = 0;
        for ( double value : answers ) {
            if ( j == 4 ) {
                j = 0;
                i++;
            }

            switch (j) {
                case 0:
                    splines.get(i).setA0( value );
                    break;
                case 1:
                    splines.get(i).setA1( value );
                    break;
                case 2:
                    splines.get(i).setA2( value );
                    break;
                case 3:
                    splines.get(i).setA3( value );
                    break;
            }

            j++;
        }

        return splines;

    }

    private static RealVector resolveSplineEquation( double[] vectorX, double[] vectorY ) {

        double[][] vars = defineSplineMatrix( vectorX );
        double[] cons = defineConstantsMatrix( vectorY );

        RealMatrix coefficients = new Array2DRowRealMatrix( vars, false );
        DecompositionSolver solver = new LUDecomposition( coefficients ).getSolver();
        RealVector constants = new ArrayRealVector( cons, false );

        return solver.solve(constants);

    }

    private static double[][] defineSplineMatrix( double[] vectorX ) {

        int n = vectorX.length;
        int size = 4*n - 4;
        double[][] result = new double[ size ][ size ];
        int[] ens = resolveEquationsNumbers( n );

        for ( int i = 0; i < n; i++ ) {
            Arrays.fill( result[i], 0f );
        }

        result[0][2] = 2;
        result[0][3] = 6*vectorX[0];
        result[1][size-2] = 2;
        result[1][size-1] = 6*vectorX[n-1];

        int[] bias = { 0, 0 };
        for ( int i = 1; i < n; i++ ) {

            int r0 = 2 + bias[0];
            int c0 = bias[1];

            result[r0][c0] = 1;
            result[r0+1][c0] = 1;
            result[r0][c0+1] = vectorX[i-1];
            result[r0+1][c0+1] = vectorX[i];
            result[r0][c0+2] = Math.pow(vectorX[i-1], 2);
            result[r0+1][c0+2] = Math.pow(vectorX[i], 2);
            result[r0][c0+3] = Math.pow(vectorX[i-1], 3);
            result[r0+1][c0+3] = Math.pow(vectorX[i], 3);

            bias[0] = bias[0] + 2;
            bias[1] = bias[1] + 4;

        }

        bias = new int[] { 0, 0 };
        for ( int i = 1; i < n-1; i++ ) {

            int r0 = ens[0] + ens[1] + bias[0];
            int r1 = r0 + 1;
            int c0 = 1 + bias[1];
            int c1 = 5 + bias[1];

            result[r0][c0] = 1;
            result[r0][c0+1] = 2*vectorX[i];
            result[r0][c0+2] = 3*Math.pow(vectorX[i], 2);
            result[r1][c0+1] = 2;
            result[r1][c0+2] = 6*vectorX[i];
            result[r0][c1] = -1;
            result[r0][c1+1] = -2*vectorX[i];
            result[r0][c1+2] = -3*Math.pow(vectorX[i], 2);
            result[r1][c1+1] = -2;
            result[r1][c1+2] = -6*vectorX[i];

            bias[0] = bias[0] + 2;
            bias[1] = bias[1] + 4;

        }

        return result;

    }

    private static double[] defineConstantsMatrix( double[] vectorY ) {

        int n = vectorY.length;
        int size = 4*n - 4;
        int[] ens = resolveEquationsNumbers( n );

        double[] result = new double[ size ];
        Arrays.fill( result, 0f );

        result[ ens[0] ] = vectorY[0];
        result[ ens[0]+ens[1]-1 ] = vectorY[ n-1 ];
        int index = ens[0] + 1;
        for ( int i = 1; i < n-1; i++ ) {
            result[ index ] = vectorY[i];
            result[ index + 1 ] = vectorY[i];
            index += 2;
        }

        return result;

    }

    private static int[] resolveEquationsNumbers( int size ) {

        int boundsEN = 2;
        int interpolationEN = 2;
        int dockingEN = 0;

        for ( int i = 0; i < size-2; i++ ) {
            dockingEN += 2;
            interpolationEN += 2;
        }

        return new int[] { boundsEN, interpolationEN, dockingEN };

    }

}