package game.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class GameObject implements Comparable<GameObject> {
    
    private int miniWidth = 3;
    private int miniHeight = 3;
    
    public abstract int getX();
    public abstract int getY();
    
    public abstract void setX(int x);
    public abstract void setY(int y);
    
    public int getMiniWidth() { return miniWidth; }
    public int getMiniHeight() { return miniHeight; }
    
    public abstract Color getColor();
    
    public abstract int getDepth();
    
    public abstract void render(Graphics g);
    
    @Override
    public int compareTo(GameObject o) {
        return this.getDepth()>o.getDepth() ? 1 : (this.getDepth()==o.getDepth()?0:-1);
    }
}