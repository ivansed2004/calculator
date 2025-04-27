package ru.ivan.commons.studvesna.objects.interferogram;

import static ru.ivan.commons.studvesna.environment.Environment.SEP;

import ru.ivan.commons.studvesna.objects.splines.Spline;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InterferogramUtils {

    // Keep it always private!
    private InterferogramUtils() {}

    public static Interferogram derive( List<Spline> splines ) {
        int SIZE = splines.size(); // N, where N - number of splines
        Unit[][] units = new Unit[10][SIZE+1];

        List<Double> intLimits = getIntLimits(splines);

        units[0][0] = new Unit(6*splines.get(0).getA3(), intLimits.get(0), (byte) 4, false);
        units[0][SIZE] = new Unit(-6*splines.get(SIZE-1).getA3(), intLimits.get(SIZE), (byte) 4, false);
        /////////////////////////////////////////////////
        units[1][0] = new Unit(6*splines.get(0).getA3()*intLimits.get(0), intLimits.get(0), (byte) 3, true);
        units[1][SIZE] = new Unit(-6*splines.get(SIZE-1).getA3()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 3, true);
        units[2][0] = new Unit(2*splines.get(0).getA2(), intLimits.get(0), (byte) 3, true);
        units[2][SIZE] = new Unit(-2*splines.get(SIZE-1).getA2(), intLimits.get(SIZE), (byte) 3, true);
        /////////////////////////////////////////////////
        units[3][0] = new Unit(-3*splines.get(0).getA3()*Math.pow(intLimits.get(0), 2), intLimits.get(0), (byte) 2, false);
        units[3][SIZE] = new Unit(3*splines.get(SIZE-1).getA3()*Math.pow(intLimits.get(SIZE), 2), intLimits.get(SIZE), (byte) 2, false);
        units[4][0] = new Unit(-2*splines.get(0).getA2()*intLimits.get(0), intLimits.get(0), (byte) 2, false);
        units[4][SIZE] = new Unit(2*splines.get(SIZE-1).getA2()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 2, false);
        units[5][0] = new Unit(-splines.get(0).getA1(), intLimits.get(0), (byte) 2, false);
        units[5][SIZE] = new Unit(splines.get(SIZE-1).getA1(), intLimits.get(SIZE), (byte) 2, false);
        /////////////////////////////////////////////////
        units[6][0] = new Unit(-splines.get(0).getA3()*Math.pow(intLimits.get(0), 3), intLimits.get(0), (byte) 1, true);
        units[6][SIZE] = new Unit(splines.get(SIZE-1).getA3()*Math.pow(intLimits.get(SIZE), 3), intLimits.get(SIZE), (byte) 1, true);
        units[7][0] = new Unit(-splines.get(0).getA2()*Math.pow(intLimits.get(0), 2), intLimits.get(0), (byte) 1, true);
        units[7][SIZE] = new Unit(splines.get(SIZE-1).getA2()*Math.pow(intLimits.get(SIZE), 2), intLimits.get(SIZE), (byte) 1, true);
        units[8][0] = new Unit(-splines.get(0).getA1()*intLimits.get(0), intLimits.get(0), (byte) 1, true);
        units[8][SIZE] = new Unit(splines.get(SIZE-1).getA1()*intLimits.get(SIZE), intLimits.get(SIZE), (byte) 1, true);
        units[9][0] = new Unit(-splines.get(0).getA0(), intLimits.get(0), (byte) 1, true);
        units[9][SIZE] = new Unit(splines.get(SIZE-1).getA0(), intLimits.get(SIZE), (byte) 1, true);
        for ( int i = 1; i < SIZE; i++ ) {

            units[0][i] = new Unit(6*(splines.get(i).getA3() - splines.get(i-1).getA3()), intLimits.get(i), (byte) 4, false);
            units[1][i] = new Unit(6*(splines.get(i).getA3() - splines.get(i-1).getA3())*intLimits.get(i), intLimits.get(i), (byte) 3, true);
            units[2][i] = new Unit(2*(splines.get(i).getA2() - splines.get(i-1).getA2()), intLimits.get(i), (byte) 3, true);
            units[3][i] = new Unit(-3*(splines.get(i).getA3() - splines.get(i-1).getA3())*Math.pow(intLimits.get(i), 2), intLimits.get(i), (byte) 2, false);
            units[4][i] = new Unit(-2*(splines.get(i).getA2() - splines.get(i-1).getA2())*intLimits.get(i), intLimits.get(i), (byte) 2, false);
            units[5][i] = new Unit(-(splines.get(i).getA1() - splines.get(i-1).getA1()), intLimits.get(i), (byte) 2, false);
            units[6][i] = new Unit(-(splines.get(i).getA3() - splines.get(i-1).getA3())*Math.pow(intLimits.get(i), 3), intLimits.get(i), (byte) 1, true);
            units[7][i] = new Unit(-(splines.get(i).getA2() - splines.get(i-1).getA2())*Math.pow(intLimits.get(i), 2), intLimits.get(i), (byte) 1, true);
            units[8][i] = new Unit(-(splines.get(i).getA1() - splines.get(i-1).getA1())*intLimits.get(i), intLimits.get(i), (byte) 1, true);
            units[9][i] = new Unit(-(splines.get(i).getA0() - splines.get(i-1).getA0()), intLimits.get(i), (byte) 1, true);

        }

        return new Interferogram(units, new ArrayList<>());
    }

    public static String[] toStrings(Interferogram interferogram) {
        ExpressionBuilder builder = new ExpressionBuilder(interferogram.getUNITS());
        return builder.build();
    }

    private static List<Double> getIntLimits(List<Spline> splines) {
        List<Double> intLimits = new ArrayList<>();
        intLimits.add(splines.get(0).getStart());
        for ( Spline s : splines ) {
            intLimits.add( s.getEnd() );
        }
        return intLimits;
    }

    public static Map<Double, Double> performSampling( Interferogram interferogram, double start, double end, double period ) {
        Map<Double, Double> samples = new HashMap<>();

        double count = Math.ceil((end - start) / period);

        double arg = start;
        for ( int i = 0; i < count; i++ ) {
            samples.put(arg, interferogram.getValue(arg));
            arg += period;
        }
        samples.put(end, interferogram.getValue(end));

        return samples;
    }

    public static void persist( Map<Double, Double> samples, String path, String fileName, double start, double end,
                               double period ) {
        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating interferogram_discrete file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {

            double count = Math.ceil((end - start) / period);
            double arg = start;
            for ( int i = 0; i < count; i++ ) {
                fw.write( String.format( "%.3f", arg ) + "\t" + String.format( "%.8f", samples.get(arg) ) );
                fw.write("\n");
                arg += period;
            }
            fw.write( String.format( "%.3f", end ) + "\t" + String.format( "%.8f", samples.get(end) ) );
            fw.write("\n");

        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }
    }

    public static void printExpression(String[] strings, String path, String fileName ) {
        try {

            Path directory = Paths.get( path );
            Path filePath = directory.resolve( fileName );
            Files.createDirectories( directory );
            Files.createFile( filePath );

        } catch (IOException ex) {
            System.out.println("Error occurred while creating interferogram_analytical file...: " + ex.getMessage());
        }

        try ( FileWriter fw = new FileWriter(path + SEP + fileName) ) {

            for (int i = 0; i < strings.length; i++) {
                if (!Objects.equals(strings[i], "")) {
                    if ( i != strings.length-1 ) {
                        fw.write( String.format("%s +", strings[i]) );
                    } else {
                        fw.write( String.format("%s", strings[i]) );
                    }
                    fw.write("\n\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error occurred while writing into the file: " + ex.getMessage());
        }
    }

}