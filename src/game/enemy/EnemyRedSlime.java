package game.enemy;

import game.player.Player;
import game.sprite.EntitySprite;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EnemyRedSlime extends EnemySlime {
    
    protected boolean isHit;
    protected boolean damageBlink;
    protected int knockbackDX;
    protected int knockbackDY;
    protected final int KNOCKBACK_DISTANCE = 200;
    
    //How slippery knockback is. Less means more slide.
    protected final int KNOCKBACK_MULTIPLIER = 30;
    protected final int STUN_DURATION = 400;

    public EnemyRedSlime(Player player) {
        super(player);
        this.x = (int)(Math.random()*StateMultiplayer.WORLD_SIZE_X);
        this.y = (int)(Math.random()*StateMultiplayer.WORLD_SIZE_Y);
        this.speed = 0.0625;
        this.animationSpeed = 332;
        this.health = 5;
        this.minimapColor = new Color(255,128,128);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.BLOB_RIGHT.getAnim(),
            AnimationLibrary.BLOB_UP.getAnim(),
            AnimationLibrary.BLOB_LEFT.getAnim(),
            AnimationLibrary.BLOB_DOWN.getAnim(),
        };
        sprite.setAnimations(animList);
        initializeMask();
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
        
        sprite.getAnim(spritePointer).start();
        if (Math.random()*20<1) {
            spritePointer = (int)(Math.random()*4);
        }
        
        int dx = 0;
        int dy = 0;
        
        if (spritePointer == 0)
            dx+=speed*delta;
        else if (spritePointer == 1)
            dy-=speed*delta;
        else if (spritePointer == 2)
            dx-=speed*delta;
        else if (spritePointer == 3)
            dy+=speed*delta;
        
        x += dx;
        y += dy;
        
        /*
        for(int i=0;i<Math.abs(dx);i++) {
            if (Obstacle.testForCollision(x+(dx>0?1:-1),y,getCollisionMask()))
                return;
            x+=dx>0?1:-1;
        }
        
        for(int i=0;i<Math.abs(dy);i++) {
            if (Obstacle.testForCollision(x,y+(dy>0?1:-1),getCollisionMask()))
                return;
            y+=dy>0?1:-1;
        }
        */
    }
    
    @Override
    protected void resolveCollision() {
        isHit = getCollisionMask().intersects(player.getCollisionMask());
    }
    
    @Override
    public void resolveHit(int ox, int oy, int attackId, int damage) {
        if (attackId != lastAttackId) {
            lastAttackId = attackId;
            isHit = true;
            initializeKnockback(x-ox,y-oy);
            health-=damage;
            SoundLibrary.SWORD_HIT.play();
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
    protected void renderDebugInfo(Graphics g) {
        super.renderDebugInfo(g);
        g.drawString(isHit?"Hit":"Not Hit",10+x+64,66+y+64);
        if (StateMultiplayer.DEBUG_COLLISION) {
            getCollisionMask().render(g);
        }
    }
}
