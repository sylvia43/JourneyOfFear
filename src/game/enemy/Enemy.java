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
    
    protected int knockbackDX;
    protected int knockbackDY;
    protected int stunTimer;
    
    // These can be overridden in other classes.
    protected static final int KNOCKBACK_MULTIPLIER = 30;
    protected static final int KNOCKBACK_DISTANCE = 200;
    protected static final int STUN_DURATION = 400;
    protected static final int DAMAGE_BLINK_TIME = 50;
    protected static final int INVULNERABILITY_DURATION = DAMAGE_BLINK_TIME;
    
    protected int health;
    
    protected int lastAttackId = -1;

    protected Player player;
    
    protected boolean readyToDie = false;
    
    protected Color minimapColor;
    
    //Getters. These methods probably can be left alone.
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    public boolean readyToDie() { return readyToDie; }
    public EntitySprite getSprite() { return sprite; }
    
    @Override
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame()).update(x,y);
    }
    
    public Enemy(Player player) {
        this(player,(int)(Math.random()*StateMultiplayer.WORLD_SIZE_X),
                (int)(Math.random()*StateMultiplayer.WORLD_SIZE_Y));
    }
    
    public Enemy(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
        minimapColor = Color.red;
        spritePointer = (int)(Math.random()*4);
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    //Game loop methods
    public void init(GameContainer container) {
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
        g.drawString("hp: " + String.valueOf(health),x+64,y+64+56);
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
    
    // Empty methods. These methods should be overriden
    protected abstract void initializeSprite();
    protected abstract void move(int delta); // Default move behavior
    @Override public abstract void resolveHit(int ox, int oy, int attackId, int damage, double mult);
    
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
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
    
    protected void initializeKnockback(int dx, int dy, double mult) {
        if (stunTimer>0)
            return;
        
        knockbackDX=(int)(mult*KNOCKBACK_DISTANCE*Math.cos(Math.atan2(dy,dx)));
        knockbackDY=(int)(mult*KNOCKBACK_DISTANCE*Math.sin(Math.atan2(dy,dx)));
        stunTimer = STUN_DURATION;
    }
}