package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.sprite.Rectangle;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Obstacle {
    
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
    
   
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.TREE_LARGE.getAnim();
        mask = new Rectangle(x,y,x+sprite.getImage(0).getWidth()*4,y+sprite.getImage(0).getHeight()*4);
        sprite.setDuration(0,1000);
        mask = new Rectangle(x-32,y,x+sprite.getImage(0).getWidth()*2,y+sprite.getImage(0).getHeight()*4);
    }
    
    protected Rectangle createMask() {
        return new Rectangle(x,y,x+sprite.getWidth(),y+sprite.getHeight());
    }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta, Area currentArea) { }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }
}