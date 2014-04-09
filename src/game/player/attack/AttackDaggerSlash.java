package game.player.attack;

import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;

public class AttackDaggerSlash extends Attack {
    
    protected static final int defaultDamage = 4;
    protected static final double defaultKnockback = 0.5;
    protected static final int defaultSwingEndRest = 200;
    protected static final int defaultAttackRest = 300;
    
    protected AttackDaggerSlash() { }
    
    public static AttackDaggerSlash create() {
        return create(defaultDamage,defaultKnockback);
    }
    
    public static AttackDaggerSlash create(int damage, double knockback) {
        return create(damage,knockback,defaultSwingEndRest,defaultAttackRest);
    }
    
    public static AttackDaggerSlash create(int damage, double knockback,
            int swingEndRest, int attackRest) {
        AttackDaggerSlash a = new AttackDaggerSlash();
        a.damage = damage;
        a.knockback = knockback;
        a.swingEndRest = swingEndRest;
        a.attackRest = attackRest;
        return a;
    }
    
    @Override
    public ImageMask getMask(int x, int y) {
        if (!attacking)
            return null;
        
        int frame = anim.getFrame()+16;
        
        int dx = frame>=14&&frame<=18||frame==31 ? 1 : (frame>=22&&frame<=26)?-1:0;
        int dy = frame>=26&&frame<=30 ? 1 : (frame>=18&&frame<=22)?-1:0;
        
        return new ImageMask(new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64));
    }
    
    @Override
    public void init() {
        attacking = false;
        attackDelay = 0;
        anim = AnimationLibrary.ATTACK_DAGGER_SLASH.getAnim();
        anim.stop();
    }
    
    @Override
    public void resolveAttackHit(Hittable other, int x, int y) {
        if (!attacking)
            return;
        if(other.getCollisionMask().intersects(getMask(x,y)))
            other.resolveHit(x,y,currentAttackId,damage,knockback);
    }
    
    @Override
    public void attack(int direction, boolean sound) {
        direction = direction*2;
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = anim.getDuration(0)*2 + attackRest;
        anim.restart();
        anim.setCurrentFrame((direction+15)%16);
        targetDirection = direction;
        anim.stopAt((direction+17)%16);
        if (sound)
            SoundLibrary.values()[(int)(3*Math.random())].play();
    }
}