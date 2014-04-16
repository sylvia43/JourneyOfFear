package game.enemy;

import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Enemy implements Hittable {
    
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;
    protected int animationSpeed;
    
    protected int x;
    protected int y;
    protected double speed;
    protected int moveTimer;
    
    protected int stunTimer;
    
    protected int health;
    
    protected int lastAttackId = -1;

    protected Player player;
    
    protected boolean readyToDie = false;
    
    protected Color minimapColor;
        
    //Getters. These methods probably can be left alone.
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    public boolean readyToDie() { return readyToDie; }
    public EntitySprite getSprite() { return sprite; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame()).update(x,y);
    }
    
    // By default enemies don't have attacks.
    public ImageMask getAttackMask() { return null; }
    
    public Enemy(Player player) {
        this.player = player;
        minimapColor = Color.red; //Red is default enemy color
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) {
        initializeVariables();
        initializeSprite();
    }
    
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        move(delta);
    }
    
    public void render(GameContainer container, Graphics g) {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        if (StateMultiplayer.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    // Miscelleneous universal methods.    
    public void resolveHit(int ox, int oy, int attackId) {
        resolveHit(ox,oy,attackId,1);
    }
    
    public void resolveHit(int ox, int oy, int attackId, int damage) {
        resolveHit(ox,oy,attackId,damage,1);
    }
    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),x+64,y+64+28);
        g.drawString("y: " + String.valueOf(y),x+64,y+64+42);
        if (StateMultiplayer.DEBUG_COLLISION) {
            getCollisionMask().render(g);
        }
    }
        
    protected void resolveInvulnerability(int delta) {
        if (stunTimer>0)
            stunTimer -= delta;
        else if (health<1)
            readyToDie = true;
    }
    
    protected int directionToPlayer() {
        int playerDistX = player.getX() - getX();
        int playerDistY = player.getY() - getY();
        return Math.abs(playerDistX) > Math.abs(playerDistY) ? 
                playerDistX > 0 ? 0 : 2 : playerDistY > 0 ? 3 : 1;
    }
    
    //Empty methods. These methods should be overriden
    protected abstract void initializeVariables();
    protected abstract void initializeSprite();
    protected abstract void move(int delta); // Default move behavior
    public abstract void resolveHit(int ox, int oy, int attackId, int damage, double mult);
    
    //Other methods. These can be overriden if necessary.
    protected void initializeMask() {
        AnimationMask[] animMaskList = {
            createMask(0),
            createMask(1),
            createMask(2),
            createMask(3)
        };
        sprite.setMasks(animMaskList);
    }
    
    protected AnimationMask createMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
}