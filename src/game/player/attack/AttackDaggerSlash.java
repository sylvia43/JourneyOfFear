package game.player.attack;

import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;

public class AttackDaggerSlash extends Attack {
    
     public AttackDaggerSlash() {
        swingEndRest = 200;
        attackRest = 300;
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
            other.resolveHit(x,y,currentAttackId,4,0.5);
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