package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EnemyBlob extends Enemy {

    protected Animation attack;
    
    protected final int ATTACK_SPEED = 10;
    protected final int SWORD_DELAY = 800;
    
    protected boolean attackHit;
    protected boolean isHit;
    protected boolean damageBlink;
    protected boolean invulnerable = false;
    protected int invulnerabilityTimer = 0;
    protected int stunTimer;
    protected int knockbackDX;
    protected int knockbackDY;
    protected final int DAMAGE_BLINK_TIME = 50;
    protected final int KNOCKBACK_DISTANCE = 200;
    //How slippery knockback is. Less means more slide.
    protected final int KNOCKBACK_MULTIPLIER = 30;
    protected final int STUN_DURATION = 400;
    protected final int INVULNERABILITY_DURATION = DAMAGE_BLINK_TIME;

    public EnemyBlob(Player player) {
        super("blobred",player);
        this.spritepath = "blobred";
        this.x=500;
        this.y=500;
        this.speed = 0.25;
        this.animationSpeed = 332;
    }
    
    protected void initializeVariables() {
        spritePointer = 3;
    }
    
    public void move(int delta) {
        if (stunTimer>0) {
            sprite.getAnim(spritePointer).setCurrentFrame(0);
            sprite.getAnim(spritePointer).stop();
            x+=(int)((knockbackDX*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER));
            y+=(int)((knockbackDY*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER));
            return;
        }
        sprite.getAnim(spritePointer).start();
        if (Math.random()*10<1) {
            spritePointer=(int)(Math.random()*4);
        }
        if (spritePointer==0) {
            x+=speed*delta;
        } else if (spritePointer==1) {
            y-=speed*delta;
        } else if (spritePointer==2) {
            x-=speed*delta;
        } else if (spritePointer==3) {
            y+=speed*delta;
        }
    }
    
    protected void resolveCollision() {
        isHit = getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY());
    }
    
    public void resolveHit(int ox, int oy) {
        isHit = true;
        if (!invulnerable) {
            invulnerable = true; //Deal damage here somewhere.
            invulnerabilityTimer = 0;
            initializeKnockback(x-ox,y-oy);
        }
    }
    
    protected void initializeKnockback(int dx, int dy) {
        if (stunTimer<=0) {
            knockbackDX=(int)(KNOCKBACK_DISTANCE*Math.cos(Math.atan2(dy,dx)));
            knockbackDY=(int)(KNOCKBACK_DISTANCE*Math.sin(Math.atan2(dy,dx)));
            stunTimer = STUN_DURATION;
        }
    }
    
    protected void resolveInvulnerability(int delta) {
        invulnerabilityTimer += delta;
        if (stunTimer>0)
            stunTimer -= delta;
        if (invulnerabilityTimer>INVULNERABILITY_DURATION && (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0) {
            invulnerable = false;
            invulnerabilityTimer = 0;
        }
        if (invulnerable)
            damageBlink = (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0;
    }
    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        g.drawString(isHit?"Hit":"Not Hit",10+x+64,66+y+64);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+x+64,80+y+64);
        if (SlickGame.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
        }
    }
}
