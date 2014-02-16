package game.enemy;

import game.player.Player;
import game.sprite.EntitySprite;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import game.util.resource.SoundPlayer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemySmartBlob extends Enemy {

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
    
    @Override
    public Rectangle getAttackMask() {
        if (!attacking)
            return null;
        
        int dx = (int) Math.round(Math.sin((attack.getFrame()+2)*0.25*Math.PI));
        int dy = (int) Math.round(Math.cos((attack.getFrame()+2)*0.25*Math.PI));
        
        return new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64);
    }

    public EnemySmartBlob(Player player) {
        super(player);
        this.spritepath = "blobredsir";
        this.x=500;
        this.y=500;
        this.speed = 0.125;
        this.animationSpeed = 332;
        this.health = 20;
        this.minimapColor = new Color(181, 230, 29);
    }
    
    @Override
    protected void initializeVariables() {
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }
    
    @Override
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
    
    @Override
    protected void initializeAttack() throws SlickException {
        attack = AnimationLibrary.PLAYER_SWORD_SLASH.getAnim(48);
        attack.stop();
    }
    
    @Override
    protected void resolveAttack(int delta) {
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
    }
    
    protected void resolveAttackCollision() {
        attackHit = player.getCollisionMask().intersects(getAttackMask(),player.getX(),player.getY());
        if (attackHit)
            player.resolveHit(x,y);
    }
    
    protected void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = attack.getDuration(0)*2 + SWORD_DELAY;
        attack.restart();
        attack.setCurrentFrame(direction);
        attack.stopAt((direction+10)%8);
    }
    
    @Override
    public void move(int delta) {
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
    }
    
    protected void updateSpritePointer(int delta) {
        dirChangeCounter = 0;
        if (Math.random()<0.1) {
            spritePointer = (int) (Math.random()*4);
            return;
        }
        spritePointer = directionToPlayer();
    }
    
    @Override
    protected void resolveCollision() {
        isHit = getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY());
    }
    
    @Override
    public void resolveHit(int ox, int oy, int attackId) {
        if (attackId != lastAttackId) {
            lastAttackId = attackId;
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
    
    @Override
    protected void renderAttack() {
        if (attacking) {
            attack.draw(x-64,y-64,192,192);
        }
    }
    
    @Override
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        g.drawString(isHit?"Hit":"Not Hit",10+x+64,66+y+64);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+x+64,80+y+64);
        if (StatePlaying.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
            if (attacking) {
                g.setColor(Color.red);
                Rectangle r = getAttackMask();
                g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
            }
        }
    }
}
