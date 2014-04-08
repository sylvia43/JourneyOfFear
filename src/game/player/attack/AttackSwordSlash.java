package game.player.attack;

import game.sprite.Hittable;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class AttackSwordSlash extends Attack {
    
    private final int ATTACK_SPEED = 10;
    private final int SWORD_DELAY = 400;
        
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;
    
    private int currentAttackId = 0;
    private int attackId = 0;
    
    private boolean attackHit;
    
    @Override
    public Rectangle getMask(int x, int y) {
        if (!attacking)
            return null;
        
        int dx = (int) Math.round(Math.sin(Math.toRadians((sword.getFrame()+2)*45)));
        int dy = (int) Math.round(Math.cos(Math.toRadians((sword.getFrame()+2)*45)));
        
        return new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64);
    }

    @Override
    public void init() {
        attacking = false;
        attackDelay = 0;
        sword = AnimationLibrary.PLAYER_SWORD_SLASH.getAnim();
        sword.stop();
    }

    @Override
    public void render(int x, int y) {
        if (attacking)
            sword.draw(x-64,y-64,192,192);
    }
    
    @Override
    public void resolveAttack(int delta, int x, int y) {
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED*6+160)
            attacking = false;
    }
    
    @Override
    public void resolveAttackHit(Hittable other, int x, int y, int ox, int oy) {
        if(other.getCollisionMask().intersects(getMask(x,y),ox,oy)) {
            other.resolveHit(x,y,currentAttackId);
            attackHit = true;
        }
    }
    
    public boolean canAttack() {
        return !attacking && attackDelay<1;
    }
    
    public void attack(int direction, boolean sound) {
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction + 10) % 8);
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
        g.drawString(attackHit?"Hitting!":"Not Hitting",camX,28+camY);
    }
    
    public void renderMask(int x, int y, Graphics g) {
        if (attacking) {
            g.setColor(Color.red);
            Rectangle r = getMask(x,y);
            g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
        }
    }
}