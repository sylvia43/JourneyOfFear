package game.environment;

import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hazard {
    
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;
    protected int animationSpeed;  
    protected boolean collisionHit;
    
    protected int x;
    protected int y;    
    
    protected Color minimapColor;
        
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    // By default hazards don't have attacks.
    public Rectangle getAttackMask() { return null; }
    
    public Hazard() {
       minimapColor=(Color.blue);
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeVariables();
        initializeSprite();
       
    }
    
    public void update(GameContainer container, int delta) {
        resolveCollision();
       
    }
     public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        
        if (StatePlaying.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    // Miscelleneous universal methods.    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
    }
    
    
    //Empty methods. These methods should be overriden
    protected void initializeVariables() { }
    protected void initializeSprite() throws SlickException { initializeMask(); }
    protected void resolveCollision() { }
    
    

    
   
    
    //Other methods. These can be overriden if necessary.
    protected void initializeMask() throws SlickException {
        sprite.setMasks(
                createMask(0),
                createMask(1),
                createMask(2),
                createMask(3)
        );
    }
    
    protected AnimationMask createMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }
}