package game.enemy;

import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import game.util.resource.SoundPlayer;
import game.util.server.DataPacket;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyPlayer {
    
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
    
    protected int lastAttackId=-1;
    
    protected boolean readyToDie = false;
    
    protected Color minimapColor = Color.red;
    protected Animation attack;
    
    protected final int ATTACK_SPEED = 10;
    protected final int SWORD_DELAY = 800;
    protected final int DIR_SWITCH_SPEED = 500;
    protected int dirChangeCounter = 0;
    
    protected int direction;
    
    protected boolean attacking;
    protected int attackTimer;
    protected int attackDelay;
    
    protected boolean attackHit;
    protected boolean isHit;
    protected boolean damageBlink;
    protected boolean invulnerable = false;
    protected int invulnerabilityTimer = 0;
    protected int knockbackDX;
    protected int knockbackDY;
    protected final int DAMAGE_BLINK_TIME = 50;
    protected final int KNOCKBACK_DISTANCE = 200;
    //How slippery knockback is. Less means more slide.
    protected final int KNOCKBACK_MULTIPLIER = 30;
    protected final int STUN_DURATION = 400;
    protected final int INVULNERABILITY_DURATION = DAMAGE_BLINK_TIME;
    
    protected final int id;
    
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return minimapColor; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    public Rectangle getAttackMask() {
        if (!attacking)
            return null;
        
        int dx = (int) Math.round(Math.sin((attack.getFrame()+2)*0.25*Math.PI));
        int dy = (int) Math.round(Math.cos((attack.getFrame()+2)*0.25*Math.PI));
        
        return new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64);
    }
    
    public EnemyPlayer(int x, int y, int id) throws SlickException {
        this.id = id;
        this.spritepath = "blobredsir";
        this.x=x;
        this.y=y;
        this.speed = 0.125;
        this.animationSpeed = 332;
        this.health = 20;
        initializeSprite();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        renderAttack();
        if (StateMultiplayer.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    protected void initializeVariables() {
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }
    
    protected void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.SIRBLOB_RIGHT.getAnim(),
            AnimationLibrary.SIRBLOB_UP.getAnim(),
            AnimationLibrary.SIRBLOB_LEFT.getAnim(),
            AnimationLibrary.SIRBLOB_DOWN.getAnim(),
        };
        sprite.setAnimations(animList);
        initializeMask();
    }
    
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
    
    protected void initializeAttack() throws SlickException {
        attack = AnimationLibrary.PLAYER_SWORD_SLASH.getAnim();
        attack.stop();
    }
    
    protected void resolveAttack(int delta) {
        /*
        if (!attacking && attackDelay < 1) {
            direction = directionToPlayer()*2;
            attack(direction);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED*6+160) {
            attacking = false;
        }
        resolveAttackCollision();
        */
    }
    
    protected void resolveAttackCollision() {
        /*
        attackHit = player.getCollisionMask().intersects(getAttackMask(),player.getX(),player.getY());
        if (attackHit)
            player.resolveHit(x,y);
        */
    }
    
    protected void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = attack.getDuration(0)*2 + SWORD_DELAY;
        attack.restart();
        attack.setCurrentFrame(direction);
        attack.stopAt((direction+10)%8);
    }
    
    public void move(int delta) {
        /*
        if (stunTimer>0) {
            sprite.getAnim(spritePointer).setCurrentFrame(0);
            sprite.getAnim(spritePointer).stop();
            x+=(knockbackDX*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            y+=(knockbackDY*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            return;
        }
        if (dirChangeCounter<DIR_SWITCH_SPEED) {
            dirChangeCounter += (int) (2*delta*Math.random());
        } else {
            updateSpritePointer(delta);
        }
        sprite.getAnim(spritePointer).start();
        switch(spritePointer) {
            case 0:
                x+=speed*delta;
                break;
            case 1:
                y-=speed*delta;
                break;
            case 2:
                x-=speed*delta;
                break;
            case 3:
                y+=speed*delta;
                break;
        }
        */
    }
    
    protected void updateSpritePointer(int delta) {
        /*
        dirChangeCounter = 0;
        if (Math.random()<0.1) {
            spritePointer = (int) (Math.random()*4);
            return;
        }
        spritePointer = directionToPlayer();
        */
    }
    
    protected void resolveCollision() {
        /*
        isHit = getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY());
        */
    }
    
    public void resolveHit(int ox, int oy, int attackId) {
        if (attackId != lastAttackId) {
            isHit = true;
            initializeKnockback(x-ox,y-oy);
            health--;
            SoundPlayer.play(SoundLibrary.SWORD_HIT);
        }
    }
    
    protected void initializeKnockback(int dx, int dy) {
        if (stunTimer<=0) {
            knockbackDX=(int)(KNOCKBACK_DISTANCE*Math.cos(Math.atan2(dy,dx)));
            knockbackDY=(int)(KNOCKBACK_DISTANCE*Math.sin(Math.atan2(dy,dx)));
            stunTimer = STUN_DURATION;
        }
    }
    
    protected void renderAttack() {
        if (attacking) {
            attack.draw(x-64,y-64,192,192);
        }
    }
    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        g.drawString(isHit?"Hit":"Not Hit",10+x+64,66+y+64);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+x+64,80+y+64);
        if (StateMultiplayer.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
            if (attacking) {
                g.setColor(Color.red);
                Rectangle r = getAttackMask();
                g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
            }
        }
    }
    
    public int getId() {
        return id;
    }
    
    public DataPacket getPacket() {
        DataPacket packet = new DataPacket();
        packet.add(x,0);
        packet.add(y,4);
        packet.add(id,8);
        return packet;
    }
}
