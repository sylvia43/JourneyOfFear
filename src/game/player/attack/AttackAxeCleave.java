package game.player.attack;

import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;

public class AttackAxeCleave extends Attack {
    
    protected AttackAxeCleave() { }
    
    public static AttackAxeCleave create() {
        return create(3,1.5);
    }
    
    public static AttackAxeCleave create(int damage, double knockback) {
        return create(damage,knockback,350,1000);
    }
    
    public static AttackAxeCleave create(int damage, double knockback,
            int swingEndRest, int attackRest) {
        AttackAxeCleave a = new AttackAxeCleave();
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
        
        int frame = anim.getFrame()+8;
        
        int dx = frame>=7&&frame<=9 ? 1 : (frame>=11&&frame<=13)?-1:0;
        int dy = frame>=13&&frame<=15 ? 1 : (frame>=9&&frame<=11)?-1:0;
        
        int ndx = 0;
        int ndy = 0;
        
        if (dx == 1)
            ndx = 64;
        else
            ndx = 96*dx;
        
        if (dy == 1)
            ndy = 64;
        else
            ndy = 96*dy;
        
        return new ImageMask(new Rectangle(x+ndx,y+ndy,x+ndx+96,y+ndy+96));
    }
    
    @Override
    public void init() {
        attacking = false;
        attackDelay = 0;
        anim = AnimationLibrary.ATTACK_AXE_CLEAVE.getAnim();
        anim.stop();
    }
    
    @Override
    public void attack(int direction, boolean sound) {
        direction = (direction+6)%8;
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = anim.getDuration(0)*2 + attackRest;
        anim.restart();
        anim.setCurrentFrame((direction+8)%8);
        targetDirection = (direction+9)%8;
        anim.stopAt((direction+11)%8);
        if (sound)
            SoundLibrary.values()[(int)(3*Math.random())].play();
    }
    
    @Override
    public void resolveAttackHit(Hittable other, int x, int y) {
        if (!attacking)
            return;
        if(other.getCollisionMask().intersects(getMask(x,y)))
            other.resolveHit(x,y,currentAttackId,damage,knockback);
    }
    
    @Override
    public void render(int x, int y) {
        if (attacking)
            anim.draw(x-96,y-96,256,256);
    }
}