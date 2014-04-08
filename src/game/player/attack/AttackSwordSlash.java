package game.player.attack;

import game.sprite.Hittable;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class AttackSwordSlash extends Attack {
    
    private final int ATTACK_SPEED = 220;
    private final int SWORD_DELAY = 600;
        
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;
    
    private int currentAttackId = 0;
    private int attackId = 0;
        
    @Override
    public ImageMask getMask(int x, int y) {
        if (!attacking)
            return null;
        
        int dx = (int) Math.round(Math.sin(Math.toRadians((anim.getFrame()+2)*45)));
        int dy = (int) Math.round(Math.cos(Math.toRadians((anim.getFrame()+2)*45)));
        
        return new ImageMask(new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64));
    }

    @Override
    public void init() {
        attacking = false;
        attackDelay = 0;
        anim = AnimationLibrary.ATTACK_SWORD_SLASH.getAnim();
        anim.stop();
    }

    @Override
    public void render(int x, int y) {
        if (attacking)
            anim.draw(x-64,y-64,192,192);
    }
    
    @Override
    public void update(int delta, int x, int y) {
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED)
            attacking = false;
    }
    
    @Override
    public void resolveAttackHit(Hittable other, int x, int y) {
        if (!attacking)
            return;
        if(other.getCollisionMask().intersects(getMask(x,y)))
            other.resolveHit(x,y,currentAttackId);
    }
    
    @Override
    public boolean canAttack() {
        return !attacking && attackDelay<1;
    }
    
    public void attack(int direction, boolean sound) {
        direction = (direction+6)%8;
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = anim.getDuration(0)*2 + SWORD_DELAY;
        anim.restart();
        anim.setCurrentFrame(direction);
        anim.stopAt((direction + 10) % 8);
        if (sound)
            SoundLibrary.values()[(int)(3*Math.random())].play();
    }
    
    private int getAttackId() {
        attackId = attackId>Integer.MAX_VALUE-1?0:attackId+1;
        return attackId;
    }
    
    public void renderDebugInfo(int camX, int camY, Graphics g) {
        g.drawString(attacking?"Attacking":"Not attacking",camX,camY);
        g.drawString(String.valueOf(attackTimer),camX,14+camY);
    }
    
    public void renderMask(int x, int y, Graphics g) {
        if (attacking) {
            g.setColor(Color.red);
            getMask(x,y).render(g);
        }
    }
}