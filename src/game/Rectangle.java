package game;

public class Rectangle {
    private int x1,x2,y1,y2;
    
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    
    public int getWidth()   {
        return x2>x1 ? x2-x1 : x1-x2;
    }
    
    public int getHeight()  {
        return y2>y1 ? y2-y1 : y1-y2;
    }
    
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public boolean intersects(Rectangle other) {
        return x1<=other.getX2() && x2>=other.getX1() && y1<=other.getY2() && y2>=other.getY1();
    }
    
    public Rectangle scaleDown(int amount) {
        return new Rectangle(x1,x1+(x2-x1)/amount,y1,y1+(y2-y1)/amount);
    }
}