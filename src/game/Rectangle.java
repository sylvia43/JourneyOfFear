package game;

public class Rectangle {
    private double x1,x2,y1,y2;
    
    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public double getX() { return x1; }
    public double getY() { return y1; }
}