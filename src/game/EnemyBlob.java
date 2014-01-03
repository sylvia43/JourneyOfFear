package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyBlob extends Enemy implements Attackable {

    protected Animation attack;
    
    protected final int ATTACK_SPEED = 10;
    protected final int SWORD_DELAY = 400;
    
    protected int direction;
    
    protected boolean attacking;
    protected int attackTimer;
    protected int attackDelay;
    
    protected boolean attackHit = false;
    protected boolean collision = false;
    
    public Rectangle getAttackMask() {
        if (!attacking)
            return null;
        int dx=0,dy=0;
        switch(attack.getFrame()) {
            case 0:
                dx=1;  dy=0;
                break;
            case 1:
                dx=1;  dy=-1;
                break;
            case 2:
                dx=0;  dy=-1;
                break;
            case 3:
                dx=-1; dy=-1;
                break;
            case 4:
                dx=-1; dy=0;
                break;
            case 5:
                dx=-1; dy=1;
                break;
            case 6:
                dx=0;  dy=1;
                break;
            case 7:
                dx=1;  dy=1;
                break;
        }
        return new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64);
    }

    public EnemyBlob(String spritepath, Player player) {
        super(spritepath,player);
        this.spritepath = spritepath;
        this.x = 960;
        this.y = 768;
        this.speed = 0.25;
        this.animationSpeed = 332;
    }
    
    protected void initializeVariables() {
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }

    protected void initializeAttack() throws SlickException {
        attack = ResourceLoader.initializeAnimation(
                "resources/player/attacks/sword_slash.png",ATTACK_SPEED*2,48);
        attack.stop();
    }
        
    protected void resolveAttack(int delta) {
        if (!attacking && attackDelay < 1) {
            direction = 2;//((int)(Math.random()*4)*2+6)%8;
            attack(direction);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED*6+160) {
            attacking = false;
        }
        resolveAttackCollision();
    }
    
    protected void resolveAttackCollision() {
        attackHit = player.getCollisionMask().intersects(getAttackMask(),player.getX(),player.getY());
    }
    
    protected void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = attack.getDuration(0)*2 + SWORD_DELAY;
        attack.restart();
        attack.setCurrentFrame(direction);
        attack.stopAt((direction+10)%8);
    }
    
    public void move(int delta) {
        //Code to randomly choose a direction and move/update animations.
    }
    
    protected void resolveCollision() {
        collision = getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY());
    }
    
    protected void renderAttack() {
        if (attacking) {
            attack.draw(x-64,y-64,192,192);
        }
    }
    
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        g.drawString(collision?"Colliding":"Not Colliding",10+x+64,66+y+64);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+x+64,80+y+64);
        if (SlickGame.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
            if (attacking) {
                g.setColor(Color.red);
                Rectangle r = getAttackMask();
                g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
            }
        }
    }
}
