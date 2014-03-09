package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.sprite.Rectangle;
import game.state.StateMultiplayer;
import java.util.ArrayList;
import java.util.Comparator;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Obstacle implements Comparator {
    
    protected Animation sprite;
    protected Rectangle mask;

    protected static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    protected Player player;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int miniWidth;
    protected int miniHeight;
    
    protected Color minimapColor;
             
    public int getX() { return x; }
    public int getY() { return y; }
    public Animation getSprite() { return sprite; }
    public int getMiniWidth() { return miniWidth; }
    public int getMiniHeight() { return miniHeight; }
    public Color getColor() { return minimapColor; }
    
    public Rectangle getCollisionMask() {
        return mask;
    }
    
    public Obstacle() {
        minimapColor = Color.red;
        this.x = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_X-100)) + 50;
        this.y = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y-100)) + 50;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public Obstacle(int x, int y) {
        minimapColor = Color.red;
        this.x = x;
        this.y = y;
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
   
    protected void initializeSprite() throws SlickException { }
    
    protected Rectangle createMask() {
        return new Rectangle(x,y,x+sprite.getWidth(),y+sprite.getHeight());
    }
    
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta, Area currentArea) { }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }

    @Override
    public int compare(Object c1, Object c2) {
        Obstacle o1 = (Obstacle) c1;
        Obstacle o2 = (Obstacle) c2;
        
        if (o1.getY() > o2.getY())
            return -1;
        else if (o1.getY() == o2.getY())
            return 0;
        else
            return 1;
    }
}