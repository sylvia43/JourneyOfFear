package game;

public class Rectangle {
    private double x1,x2,y1,y2;
    
    public double getX1() { return x1; }
    public double getY1() { return y1; }
    public double getX2() { return x2; }
    public double getY2() { return y2; }
    
    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public boolean intersects(Rectangle other) {
        return x1<=other.getX2() && x2>=other.getX1() && y1<=other.getY2() && y2>=other.getY1();
    }
}