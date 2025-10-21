package pl.adeks.simplegame.utils;

public final class MathUtil {
    public static double minimum(final double... doubles) {
        double minimum = Double.MAX_VALUE;
        for(final double d : doubles) {
            if(d < minimum) {
                minimum = d;
            }
        }
        return minimum;
    }
}
