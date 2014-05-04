package game.enemy;

import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class Enemy extends GameObject implements Hittable {
    
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;
    protected int animationSpeed;
    
    protected int x;
    protected int y;
    protected double speed;
    protected int spriteHeight;
    protected int spriteWidth;
    
    protected int knockbackDX;
    protected int knockbackDY;
    protected int stunTimer;
    
    // These can be overridden in other classes.
    protected static final int KNOCKBACK_MULTIPLIER = 30;
    protected static final int KNOCKBACK_DISTANCE = 200;
    protected static final int STUN_DURATION = 400;
    protected static final int DAMAGE_BLINK_TIME = 200;
    private final int INVULNERABILITY_DURATION = DAMAGE_BLINK_TIME*3;
    
    private boolean damageBlink;
    private boolean invulnerable = false;
    private int invulnerabilityTimer = 0;
    
    protected int hitDamage;
    
    protected int health;
    
    protected int lastAttackId = -1;

    protected Player player;
    
    protected boolean readyToDie = false;
    
    protected Color minimapColor;
    
    //Getters. These methods probably can be left alone.
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getDepth() { return y; }
    
    @Override public Color getColor() { return minimapColor; }
    public boolean readyToDie() { return readyToDie; }
    public EntitySprite getSprite() { return sprite; }
    public int getHitDamage() { return hitDamage; }
    public abstract String getName(); //Name of enemy

    
    @Override
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame()).update(x-32,y-32);
    }
    
    public Enemy(Player player) {
        this(player,(int)(Math.random()*StateMultiplayer.WORLD_SIZE_X),
                (int)(Math.random()*StateMultiplayer.WORLD_SIZE_Y));
    }
    
    public Enemy(Player player, int x, int y) {
        this.hitDamage = 1;
        this.player = player;
        this.x = x;
        this.y = y;
        minimapColor = Color.red;
        spritePointer = (int)(Math.random()*4);
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    // Game loop methods
    public void init() {
        initializeSprite();
        spriteHeight = sprite.getAnim(spritePointer).getHeight() * 4;
        spriteWidth = sprite.getAnim(spritePointer).getWidth() * 4;
    }
    
    public void update(int delta) {
        resolveInvulnerability(delta);
        move(delta);
    }
    
    @Override
    public void render(Graphics g) {
        sprite.getAnim(spritePointer).draw(x-spriteHeight/2,y-spriteWidth/2,64,64,damageBlink?Color.red:Color.white);
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
        g.drawString("x: " + String.valueOf(x),x-32,y+32);
        g.drawString("y: " + String.valueOf(y),x-32,y+46);
        g.drawString("hp: " + String.valueOf(health),x-32,y+60);
        if (StateMultiplayer.DEBUG_COLLISION) {
            getCollisionMask().render(g);
        }
    }
    
    private void resolveInvulnerability(int delta) {
        invulnerabilityTimer -= delta;
        if (invulnerabilityTimer<1 && (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0) {
            invulnerable = false;
            invulnerabilityTimer = 0;
        }
        
        if (stunTimer>0)
            stunTimer -= delta;
        else if (health<1)
            readyToDie = true;
        
        damageBlink = false;
        if (invulnerable)
            damageBlink = (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0;
    }
    
    protected int directionToPlayer() {
        int playerDistX = player.getX() - getX();
        int playerDistY = player.getY() - getY();
        return Math.abs(playerDistX) > Math.abs(playerDistY) ? 
                playerDistX > 0 ? 0 : 2 : playerDistY > 0 ? 3 : 1;
    }
    
    @Override
    public void resolveHit(int ox, int oy, int attackId, int damage, double mult) {
        if (attackId != lastAttackId) {
            invulnerable = true;
            invulnerabilityTimer = INVULNERABILITY_DURATION;
            lastAttackId = attackId;
            initializeKnockback(x-ox,y-oy,mult);
            health-=damage;
            SoundLibrary.SWORD_HIT.play();
        }
    }
    
    // Empty methods. These methods should be overriden
    protected abstract void initializeSprite();
    protected abstract void move(int delta); // Default move behavior
    
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
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x-32,y-32);
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