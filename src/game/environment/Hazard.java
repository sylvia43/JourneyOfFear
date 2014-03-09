package game.environment;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class Hazard extends Obstacle {
             
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    
    public Hazard() {
        minimapColor = Color.blue;
    }
    
    public Hazard(int x, int y) {
        minimapColor = Color.blue;
    }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    @Override public int getMiniWidth() { return miniWidth; }
    @Override public int getMiniHeight() { return miniHeight; }
    
    @Override protected void initializeSprite() throws SlickException { }
}