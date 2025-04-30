package ru.ivan.commons.studvesna.objects;

import ru.ivan.commons.studvesna.api.MathObject;
import ru.ivan.commons.studvesna.objects.interferogram.Interferogram;
import ru.ivan.commons.studvesna.samplers.InterferogramSampler;

public class Hyperbola extends MathObject {

    private final double[] AMPLITUDES;

    public Hyperbola(Interferogram interferogram ) {
        this.AMPLITUDES = getHyperbolaCoefficients( interferogram, 5 );
    }

    public double getValue( double arg ) {
        return AMPLITUDES[0]/Math.pow(arg, 4) +
                AMPLITUDES[1]/Math.pow(arg, 3) +
                AMPLITUDES[2]/Math.pow(arg, 2) +
                AMPLITUDES[3]/Math.pow(arg, 1);
    }

    private double[] getHyperbolaCoefficients( Interferogram interferogram, int a ) {
        double t = 0.1628895;
        double s1 = 0.0667401 + a*t;
        double s2 = 0.0709894 + a*t;
        double e1 = 0.0942738 + a*t;
        double e2 = 0.0985998 + a*t;
        double period = 0.00001;

        double[] results = new double[4];

        InterferogramSampler sampler = new InterferogramSampler();
        int i = 0;
        /*
        while ( i < 5 ) {
            double res1 = sampler
                    .perform( interferogram, () -> Map.of( "start", s1, "end", s2, "period", period, "i", i) )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            double res2 = sampler.perform( interferogram, () -> Map.of( "start", s1, "end", s2, "period", period, "i", i) )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            results[i] = Math.max( res1, res2 );
            i++;
        }

         */

        return results;
    }

    public double[] getAMPLITUDES() {
        return AMPLITUDES;
    }

}
