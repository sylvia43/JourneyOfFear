package game.environment.obstacle;

import game.map.Area;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class Obstacle extends GameObject {
    
    protected Animation sprite;
    protected ImageMask mask;
        
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int miniWidth;
    protected int miniHeight;
    
    protected int spriteWidth;
    protected int spriteHeight;
    
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
        
    public Obstacle() {
        this((int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)));
    }
    
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
        initializeSprite();
        minimapColor = Color.blue;
        mask.update(x-spriteWidth/2,y-spriteHeight/2);
        miniWidth = 3;
        miniHeight = 3;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    protected abstract void initializeSprite();
    
    public abstract void update(int delta, Area currentArea);
        
    @Override
    public void render(Graphics g) {
        sprite.draw(x-spriteWidth,y-spriteHeight,64,64);
        mask.render(g);
    }
}