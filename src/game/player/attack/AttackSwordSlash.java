package game.player.attack;

import game.enemy.Enemy;
import game.sprite.Rectangle;
import game.util.Options;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class AttackSwordSlash extends Attack {
    
    private final int ATTACK_SPEED = 10;
    private final int SWORD_DELAY = 400;
    
    private int attackDirection;
    
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
    public void resolveAttack(Input input, int delta, int x, int y, boolean canAttack) {
        if ((input.isKeyDown(Options.ATTACK_UP.key())
                || input.isKeyDown(Options.ATTACK_DOWN.key())
                || input.isKeyDown(Options.ATTACK_LEFT.key())
                || input.isKeyDown(Options.ATTACK_RIGHT.key()))
                && !attacking && attackDelay < 1 && !canAttack) {
            getAttackDirection(input);
            attackDirection = (attackDirection+6)%8;
            attack(attackDirection);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED*6+160) {
            attacking = false;
        }
        resolveAttackHit(x,y);
    }
    
    private void resolveAttackHit(int x, int y) {
        attackHit = false;
        for (Enemy e : enemies) {
            if(e.getCollisionMask().intersects(getMask(x,y),e.getX(),e.getY())) {
                e.resolveHit(x,y,currentAttackId);
                attackHit = true;
            }
        }
    }
    
    private void getAttackDirection(Input input) {
        if (input.isKeyDown(Options.ATTACK_RIGHT.key()))
            attackDirection = 0;
        else if (input.isKeyDown(Options.ATTACK_UP.key()))
            attackDirection = 2;
        else if (input.isKeyDown(Options.ATTACK_LEFT.key()))
            attackDirection = 4;
        else if (input.isKeyDown(Options.ATTACK_DOWN.key()))
            attackDirection = 6;
    }
    
    private void attack(int direction) {
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction + 10) % 8);
        SoundLibrary.values()[(int)(3*Math.random())].play();
    }
    
    private int getAttackId() {
        attackId = attackId>Integer.MAX_VALUE-1?0:attackId+1;
        return attackId;
    }
    
    public void renderDebugInfo(int camX, int camY, Graphics g) {
        g.drawString(attacking?"Attacking":"Not attacking",10+camX,80+camY);
        g.drawString(String.valueOf(attackTimer),10+camX,94+camY);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+camX,108+camY);
    }
    
    public void renderMask(int x, int y, Graphics g) {
        if (attacking) {
            g.setColor(Color.red);
            Rectangle r = getMask(x,y);
            g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
        }
    }
}