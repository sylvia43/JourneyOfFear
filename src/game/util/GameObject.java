package game.util;

import org.newdawn.slick.Graphics;

public abstract class GameObject {
    
    public abstract int getX();
    public abstract int getY();
    
    public abstract int getDepth();
    
    public abstract void render(Graphics g);
}