package game.enemy;

import game.player.Player;
import game.player.attack.Attack;
import game.player.attack.AttackSwordSlash;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EnemyGreenSlime extends EnemySlime {

    protected Attack attack;
    
    protected final int ATTACK_SPEED = 10;
    protected final int SWORD_DELAY = 800;
    protected final int DIR_SWITCH_SPEED = 500;
    protected int dirChangeCounter = 0;
    
    protected int direction;
    
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
    public ImageMask getAttackMask() {
        return attack.getMask(x,y);
    }

    public EnemyGreenSlime(Player player) {
        super(player);
        attack = new AttackSwordSlash();
        spritepath = "blobredsir";
        x = 500;
        y = 500;
        speed = 0.125;
        animationSpeed = 332;
        health = 10;
        minimapColor = new Color(181, 230, 29);
    }
    
    @Override
    protected void initializeSprite() {
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
    protected void initializeAttack() {
        attack.init();
    }
    
    @Override
    protected void resolveAttack(int delta) {
        if (attack.canAttack()) {
            direction = directionToPlayer()*2;
            attack.attack(direction,false);
        }
        attack.update(delta,x,y);
        resolveAttackCollision();
    }
    
    protected void resolveAttackCollision() {
        attack.resolveAttackHit(player,x,y);
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
        
        if (dirChangeCounter<DIR_SWITCH_SPEED)
            dirChangeCounter += (int) (2*delta*Math.random());
        else
            updateSpritePointer(delta);
        
        sprite.getAnim(spritePointer).start();
        
        int dx = 0;
        int dy = 0;
        
        switch(spritePointer) {
            case 0:
                dx+=speed*delta;
                break;
            case 1:
                dy-=speed*delta;
                break;
            case 2:
                dx-=speed*delta;
                break;
            case 3:
                dy+=speed*delta;
                break;
        }
        
        x += dx;
        y += dy;
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
    protected void renderAttack() {
        attack.render(x,y);
    }
    
    @Override
    protected void renderDebugInfo(Graphics g) {
        super.renderDebugInfo(g);
        attack.renderDebugInfo(x+64,y+64+56,g);
        if (StateMultiplayer.DEBUG_COLLISION) {
            attack.renderMask(x,y,g);
        }
    }
}
