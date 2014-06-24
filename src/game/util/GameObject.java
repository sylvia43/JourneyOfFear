package game.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/** Represents an object in the game. */
public abstract class GameObject implements Comparable<GameObject> {
    
    /** Width on minimap */
    private int miniWidth = 3;
    
    /** Height on minimap */
    private int miniHeight = 3;
    
    /** @return x-value of position. */
    public abstract int getX();
    
    /** @return x-value of position. */
    public abstract int getY();
    
    public abstract void setX(int x);
    public abstract void setY(int y);
    
    public int getMiniWidth() { return miniWidth; }
    public int getMiniHeight() { return miniHeight; }
    
    public abstract Color getColor();
    
    public abstract int getDepth();
    
    /** Draws the object. */
    public abstract void render(Graphics g);
    
    /**
     * Compares the depth of objects to determine render order.
     * 
     * @param o The GameObject to compare to.
     * @return 1, 0, or -1 based on depth.
     */
    @Override
    public int compareTo(GameObject o) {
        return this.getDepth()>o.getDepth() ? 1 : (this.getDepth()==o.getDepth()?0:-1);
    }
}