package game.enemy;

import game.Player;
import game.SlickGame;
import game.sprite.EntitySprite;
import game.util.ResourceLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyBlob extends Enemy {
    
    protected boolean isHit;
    protected boolean damageBlink;
    protected int stunTimer;
    protected int knockbackDX;
    protected int knockbackDY;
    protected final int KNOCKBACK_DISTANCE = 200;
    //How slippery knockback is. Less means more slide.
    protected final int KNOCKBACK_MULTIPLIER = 30;
    protected final int STUN_DURATION = 400;

    public EnemyBlob(Player player) {
        super(player);
        this.x=500;
        this.y=500;
        this.speed = 0.0625;
        this.animationSpeed = 332;
    }
    
    protected void initializeVariables() {
        spritePointer = 3;
    }
    
    protected void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            ResourceLibrary.getBlobRight(),
            ResourceLibrary.getBlobUp(),
            ResourceLibrary.getBlobLeft(),
            ResourceLibrary.getBlobDown(),
        };
        sprite.setAnimations(animList);
        initializeMask();
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
        if (Math.random()*20<1) {
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
    
    public void resolveHit(int ox, int oy, int attackId) {
        if (attackId != lastAttackId) {
            isHit = true;
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
        if (stunTimer>0)
            stunTimer -= delta;
    }
    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        g.drawString(isHit?"Hit":"Not Hit",10+x+64,66+y+64);
        if (SlickGame.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
        }
    }
}
