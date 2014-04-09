package game.sprite;

import org.newdawn.slick.Graphics;

/**
 * Stores a rectangle by its vertices (not width/height).
 */
public class Rectangle {
    
    private int x1,x2,y1,y2;
    
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    
    public int getWidth() {
        return x2>x1 ? x2-x1 : x1-x2;
    }
    
    public int getHeight() {
        return y2>y1 ? y2-y1 : y1-y2;
    }
    
    public void set(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public void shift(int x, int y) {
        this.x1 += x;
        this.x2 += x;
        this.y1 += y;
        this.y2 += y;
    }
    
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public int intersects(Rectangle other) {
        return intersectsShifted(other,0,0);
    }
    
    public int intersectsShifted(Rectangle other, int dx, int dy) {
        return (x1<other.getX2()+dx && x2>other.getX1()+dx && y1<other.getY2()+dy && y2>other.getY1()+dy)
                ? 2 : (x1==other.getX2()+dx || x2==other.getX1()+dx || y1==other.getY2()+dy || y2==other.getY1()+dy)
                ? 1 : 0;
    }
    
    public void render(Graphics g) {
        g.drawRect(x1,y1,x2-x1,y2-y1);
    }
    
    @Override
    public String toString() {
        return x1 + " " + x2 + " " + y1 + " " + y2;
    }
}