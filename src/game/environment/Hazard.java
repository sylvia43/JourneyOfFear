package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getMiniWidth() { return miniWidth; }
    public int getMiniHeight() { return miniHeight; }
    
    public void update(GameContainer container, int delta, Area currentArea) {
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }
     
    public static void updateEnemies(ArrayList<Enemy> newEnemies) {
        enemies = newEnemies;
    }
    
    protected void initializeSprite() throws SlickException { }
}