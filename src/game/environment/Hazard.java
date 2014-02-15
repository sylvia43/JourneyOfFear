package game.environment;

import game.sprite.AnimationMask;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hazard {
    
    protected Animation sprite;
    protected AnimationMask mask;
    protected String spritepath;
    protected int animationSpeed;  
    protected boolean collisionHit;
    
    protected int x;
    protected int y;    
    
    protected Color minimapColor;
        
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    
    public ImageMask getCollisionMask() {
        return mask.getImageMask(sprite.getFrame());
    }
    
    // By default hazards don't have attacks :).
    public Rectangle getAttackMask() { return null; }
    
    public Hazard() {
       minimapColor = Color.blue;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta) {
        resolveCollision();
    }
     public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,64);
    }    
    
    //Empty methods. These methods should be overriden
    protected void initializeSprite() throws SlickException { }
    protected void resolveCollision() { }
    
    protected AnimationMask createMask() {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getImage(i));
        }
        return new AnimationMask(masks);
    }
}