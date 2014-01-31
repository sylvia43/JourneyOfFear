package game.util;

public class MathHelper {
    
    /**
     * Finds the median of three numbers.
     * @return The median of a, b, and c.
     */
    public static double median(double a, double b, double c) {
        return (a<=b)?((b<=c)?b:((a<c)?c:a)):((a<=c)?a:((b<c)?c:b));
    }
}