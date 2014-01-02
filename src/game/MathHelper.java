package game;

public class MathHelper {
    
    public static double median(double a, double b, double c) {
        return (a<=b)?((b<=c)?b:((a<c)?c:a)):((a<=c)?a:((b<c)?c:b));
    }
}