package game.enemy.blob;

import game.ability.Ability;
import game.ability.AbilityDodge;
import game.enemy.AttackingEnemy;
import game.enemy.EnemyType;
import game.player.Player;
import game.player.attack.AttackSwordSlash;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class EnemyGreenBlob extends AttackingEnemy implements EnemyBlob {
    
    protected static final int DIR_SWITCH_SPEED = 500;
    protected int dirChangeCounter = 0;
    
    protected int direction;
    
    private Ability abilityDodge;
    
    public ImageMask getAttackMask() {
        return attack.getMask(x-spriteWidth/2,y-spriteHeight/2);
    }
    
    @Override
    public String getName() { return "Green Blob"; }
    
    public EnemyGreenBlob(Player player) {
        super(player);
        types.add(EnemyType.BLOB);
        types.add(EnemyType.GREEN_BLOB);
        types.add(EnemyType.ATTACKING);
        attack = AttackSwordSlash.create().setAttackRest(1000);
        speed = 0.125;
        health = 30;
        minimapColor = new Color(181,230,29);
        abilityDodge = new AbilityDodge();
        addAbility(abilityDodge);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.BLOB_GREEN_RIGHT.getAnim(),
            AnimationLibrary.BLOB_GREEN_UP.getAnim(),
            AnimationLibrary.BLOB_GREEN_LEFT.getAnim(),
            AnimationLibrary.BLOB_GREEN_DOWN.getAnim(),
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
        attack.update(delta,x-spriteWidth/2,y-spriteHeight/2);
        resolveAttackCollision();
    }
    
    protected void resolveAttackCollision() {
        attack.resolveAttackHit(player,x,y);
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
        if(player.getAttack().isAttacking() &&
                Math.sqrt((x-player.getX())*(x-player.getX())+(y-player.getY())*(y-player.getY())) <= 192)
            abilityDodge.use(x-player.getX(),y-player.getY());
        abilityDodge.update(delta,this,stunTimer>0);
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
    protected void renderAttack() {
        attack.render(x-spriteWidth/2,y-spriteHeight/2);
    }
    
    @Override
    protected void renderDebugInfo(Graphics g) {
        super.renderDebugInfo(g);
        attack.renderDebugInfo(x-spriteWidth/2,y+74,g);
        if (StateMultiplayer.DEBUG_COLLISION) {
            attack.renderMask(x-spriteWidth/2,y-spriteHeight/2,g);
        }
    }
}
