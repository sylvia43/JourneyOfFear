package game;

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
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void init(GameContainer container) throws SlickException {
        initializeVariables();
        initializeSprite();
        initializeAttack();
    }
    
    public void update(GameContainer container, int delta){
        move(delta);
        resolveCollision();
        resolveAttack(delta);
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        renderAttack();
    }
    
    //Empty methods. These methods should be overriden
    protected void initializeVariables() { }
    protected void initializeAttack() throws SlickException { }
    protected void move(int delta) { }
    protected void resolveCollision() { }
    protected void resolveAttack(int delta) { }
    protected void renderAttack() { }
    
    //Getters. These methods probably can be left alone.
    public ImageMask getCollisionMask() {
        return sprite.getMask(spritePointer).getMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    //Other methods. These can be overriden if necessary.
    protected void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/right.png",animationSpeed),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/up.png",animationSpeed),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/left.png",animationSpeed),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/down.png",animationSpeed)
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