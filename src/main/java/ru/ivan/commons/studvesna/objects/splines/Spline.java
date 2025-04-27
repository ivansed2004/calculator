package ru.ivan.commons.studvesna.objects.splines;

import java.util.List;
import java.util.Objects;

public class Spline {

    private Double start;

    private Double end;

    private Double a0;

    private Double a1;

    private Double a2;

    private Double a3;

    private List<Double> waveNumbers;

    private List<Double> intensities;

    public Spline(Double start, Double end, Double a0, Double a1, Double a2, Double a3) {
        this.start = start;
        this.end = end;
        this.a0 = a0;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
    }

    public Spline(Double start, Double end) {
        this( start, end, 0d, 0d, 0d, 0d );
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    public Double getA0() {
        return a0;
    }

    public void setA0(Double a0) {
        this.a0 = a0;
    }

    public Double getA1() {
        return a1;
    }

    public void setA1(Double a1) {
        this.a1 = a1;
    }

    public Double getA2() {
        return a2;
    }

    public void setA2(Double a2) {
        this.a2 = a2;
    }

    public Double getA3() {
        return a3;
    }

    public void setA3(Double a3) {
        this.a3 = a3;
    }

    public List<Double> getWaveNumbers() {
        return waveNumbers;
    }

    public void setWaveNumbers(List<Double> waveNumbers) {
        this.waveNumbers = waveNumbers;
    }

    public List<Double> getIntensities() {
        return intensities;
    }

    public void setIntensities(List<Double> intensities) {
        this.intensities = intensities;
    }

    @Override
    public boolean equals(Object o) {
        Spline spline = (Spline) o;
        if (this == spline) return true;
        if (!(spline instanceof Spline)) return false;

        if (!Objects.equals(start, spline.start)) return false;
        if (!Objects.equals(end, spline.end)) return false;
        if (!Objects.equals(a0, spline.a0)) return false;
        if (!Objects.equals(a1, spline.a1)) return false;
        if (!Objects.equals(a2, spline.a2)) return false;
        return Objects.equals(a3, spline.a3);
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + (a0 != null ? a0.hashCode() : 0);
        result = 31 * result + (a1 != null ? a1.hashCode() : 0);
        result = 31 * result + (a2 != null ? a2.hashCode() : 0);
        result = 31 * result + (a3 != null ? a3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String interval = "Interval = [" + this.start + "; " + this.end + "]\n";
        String a = "a0 = " + this.a0 + ";\n";
        String b = "a1 = " + this.a1 + ";\n";
        String c = "a2 = " + this.a2 + ";\n";
        String d = "a3 = " + this.a3 + ".\n";
        return "[Spline description]\n" + interval + a + b + c + d;
    }

}