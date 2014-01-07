package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Enemy {
    
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;

    protected int animationSpeed;
    
    protected int x;
    protected int y;
    protected double speed;
    
    protected Player player;
    
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    public Rectangle getAttackMask() { return null; }
    
    public Enemy(String spritepath, Player player) {
        this.spritepath = spritepath;
        this.player = player;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) throws SlickException {
        initializeVariables();
        initializeSprite();
        initializeAttack();
    }
    
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        move(delta);
        resolveCollision();
        resolveAttack(delta);
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        renderAttack();
        if (SlickGame.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    protected void renderDebugInfo(Graphics g) {
            g.setColor(Color.white);
            g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
            g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
    }
    
    //Empty methods. These methods should be overriden
    protected void initializeVariables() { }
    protected void initializeAttack() throws SlickException { }
    protected void move(int delta) { } //Default move behavior
    protected void resolveCollision() { }
    protected void resolveAttack(int delta) { }
    protected void renderAttack() { }
    protected void resolveInvulnerability(int delta) { }
    protected void resolveHit(int x, int y) { }
    
    //Other methods. These can be overriden if necessary.
    protected void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(
                ResourceLoader.initializeAnimation(spritepath + "/right.png",animationSpeed),
                ResourceLoader.initializeAnimation(spritepath + "/up.png",animationSpeed),
                ResourceLoader.initializeAnimation(spritepath + "/left.png",animationSpeed),
                ResourceLoader.initializeAnimation(spritepath + "/down.png",animationSpeed)
        );
        sprite.setMasks(
                initializeMask(0),
                initializeMask(1),
                initializeMask(2),
                initializeMask(3)
        );
    }
    
    protected AnimationMask initializeMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }
}