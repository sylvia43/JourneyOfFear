package game.player.attack;

import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;

public class AttackSwordSlash extends Attack {
    
    public AttackSwordSlash() {
        swingEndRest = 220;
        attackRest = 600;
    }
    
    @Override
    public ImageMask getMask(int x, int y) {
        if (!attacking)
            return null;
        
        int frame = anim.getFrame()+8;
        
        int dx = frame>=7&&frame<=9 ? 1 : (frame>=11&&frame<=13)?-1:0;
        int dy = frame>=13&&frame<=15 ? 1 : (frame>=9&&frame<=11)?-1:0;
        
        return new ImageMask(new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64));
    }
    
    @Override
    public void init() {
        attacking = false;
        attackDelay = 0;
        anim = AnimationLibrary.ATTACK_SWORD_SLASH.getAnim();
        anim.stop();
    }
    
    public void attack(int direction, boolean sound) {
        direction = (direction+6)%8;
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = anim.getDuration(0)*2 + attackRest;
        anim.restart();
        anim.setCurrentFrame(direction);
        targetDirection = (direction+9)%8;
        anim.stopAt((direction+10)%8);
        if (sound)
            SoundLibrary.values()[(int)(3*Math.random())].play();
    }
}