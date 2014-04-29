package game.environment.obstacle;

import game.map.Area;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
    
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    public Animation getSprite() { return sprite; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getMiniWidth() { return miniWidth; }
    public int getMiniHeight() { return miniHeight; }
    public Color getColor() { return minimapColor; }
    
    public ImageMask getCollisionMask() { return mask; }
    
    public void canMove(int x, int y) { }
    
    public Obstacle() {
        this(-1,-1);
    }
    
    public Obstacle(int nx, int ny) {
        initializeSprite();
        minimapColor = Color.blue;
        if (nx==-1 && ny==-1) {
            this.x = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_X-sprite.getWidth()));
            this.y = (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y-sprite.getHeight()));
        } else {
            this.x = nx;
            this.y = ny;
        }
        mask.update(x,y);
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    protected void initializeSprite() { }
    
    public void update(int delta, Area currentArea) { }
        
    @Override
    public void render(Graphics g) {
        sprite.draw(x,y,64,64);
        mask.render(g);
    }
}