package ru.sedinkin.calculator.objects;

import ru.sedinkin.calculator.interfaces.MathObject;
import ru.sedinkin.calculator.objects.interferogram.Interferogram;
import ru.sedinkin.calculator.samplers.HInterferogramSampler;
import ru.sedinkin.calculator.samplers.InterferogramSampler;
import java.util.Map;

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

        InterferogramSampler sampler = new HInterferogramSampler();

        for ( int i = 0; i < results.length; i++ ) {
            double res1 = sampler
                    .perform( interferogram, Map.of( "start", s1, "end", e1, "period", period, "i", i) )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            double res2 = sampler
                    .perform( interferogram, Map.of( "start", s2, "end", e2, "period", period, "i", i) )
                    .values()
                    .stream()
                    .max( Double::compareTo )
                    .get();
            results[i] = Math.max( res1, res2 );
        }

        return results;
    }

    public double[] getAMPLITUDES() {
        return AMPLITUDES;
    }

}
