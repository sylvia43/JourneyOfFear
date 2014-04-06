package game.environment;

import game.map.Area;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Obstacle extends GameObject {
    
    protected Animation sprite;
    protected ImageMask mask;
        
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
    
    public ImageMask getCollisionMask() { return mask; }
    
    public void canMove(int x, int y) { }
    
    public Obstacle() {
        minimapColor = Color.red;
        initializeSprite();
        this.x = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_X-sprite.getWidth()));
        this.y = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y-sprite.getHeight()));
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
    
    protected void initializeSprite() { }
    
    public void update(GameContainer container, int delta, Area currentArea) { }
        
    public void render(GameContainer container, Graphics g) {
        sprite.draw(x,y,64,64);
        mask.draw(x,y,g);
    }
}