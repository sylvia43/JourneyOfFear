package game.sprite;

import org.newdawn.slick.Graphics;

/**
 * Stores a rectangle by its vertices.
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
    
    /**
     * Default constructor for a rectangle.
     * @param x1 A x-vertex of the rectangle.
     * @param x2 A x-vertex of the rectangle.
     * @param y1 A y-vertex of the rectangle.
     * @param x2 A y-vertex of the rectangle.
     */
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public boolean intersects(Rectangle other) {
        return x1<=other.getX2() && x2>=other.getX1() && y1<=other.getY2() && y2>=other.getY1();
    }
    
    public boolean intersects(ImageMask other, int ox, int oy) {
        for(int i = 0; i < other.getMask().length; i++)
            for(int j = 0; j < other.getMask()[i].length; j++)
                if(other.getMask()[i][j] && 
                        ((ox+i*4>=x1 && ox+i*4<=x2) && 
                        (oy+j*4)>=y1 && (oy+j*4<=y2)))
                    return true;
        return false;
    }
    
    public void render(Graphics g) {
        g.drawRect(x1,y1,x2-x1,y2-y1);
    }
}