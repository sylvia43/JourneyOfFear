package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.sprite.Rectangle;
import game.state.StateMultiplayer;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hazard {
    
    protected Animation sprite;
    public Animation getSprite() { return sprite; }
    protected Rectangle mask;
    
    protected int miniWidth;
    protected int miniHeight;
    protected int x;
    protected int y;    
    
    protected Color minimapColor;
    
    protected static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
         
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    
    public Rectangle getCollisionMask() {
        return mask;
    }
    
    public Hazard() {
        minimapColor = Color.blue;
        this.x = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_X-100)) + 50;
        this.y = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y-100)) + 50;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public Hazard(int x, int y) {
        minimapColor = Color.blue;
        this.x = x;
        this.y = y;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public int getMiniWidth() { return miniWidth; }
     public int getMiniHeight() { return miniHeight; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta, Area currentArea) {
        resolveCollision();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }
     
    public static void updateEnemies(ArrayList<Enemy> newEnemies) {
        enemies = newEnemies;
    }
    
    //Empty methods. These methods should be overriden
    protected void initializeSprite() throws SlickException { }
    protected void resolveCollision() { }
    
    protected Rectangle createMask() {
        return new Rectangle(x,y,x+sprite.getWidth(),y+sprite.getHeight());
    }
    
    public byte isInHazard(Player player) { return -1; }
}