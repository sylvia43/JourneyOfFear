package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public class EnemyBlob extends Enemy {

    protected Animation attack;
    
    protected final int SWORD_DURATION = 48;
    protected final int SWORD_DELAY = 400;
    
    protected int direction;
    
    protected boolean attacking;
    protected int attackTimer;
    protected int attackDelay;
    
    public EnemyBlob(String spritepath) {
        this.spritepath = spritepath;
        this.x = 960;
        this.y = 768;
        this.speed = 0.25;
        this.animationSpeed = 332;
    }
    
    protected void renderAttack() {
        if (attacking) {
            attack.draw(x-64,y-64,192,192);
        }
    }
    
    protected void resolveAttack(int delta) {
        if (!attacking && attackDelay < 1) {
            direction = ((int)(Math.random()*4)*2+6)%8;
            attack(direction);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > SWORD_DURATION*4.5) {
            attacking = false;
        }
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
    
    protected void initializeAttack() throws SlickException {
        attack = ResourceLoader.initializeAnimation("resources/player/attacks/sword_slash.png",20,48);
        attack.stop();
    }
    
    protected AnimationMask initializeMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }

    protected void resolveCollision() {
        //collision = getStaticCollisionMask().intersects(enemy.getCollisionMask(),x,y,enemy.getX(),enemy.getY());
    }
    
    protected void initializeVariables() {
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }
    
    public Rectangle getAttackMask() {
        int dx = 0;
        int dy = 0;
        switch(attack.getFrame()) {
            case 0:
                dx = 1;  dy = 0;
                break;
            case 1:
                dx = 1;  dy = 1;
                break;
            case 2:
                dx = 0;  dy = 1;
                break;
            case 3:
                dx = -1; dy = 1;
                break;
            case 4:
                dx = -1; dy = 0;
                break;
            case 5:
                dx = -1; dy = -1;
                break;
            case 6:
                dx = 0;  dy = -1;
                break;
            case 7:
                dx = 1;  dy = -1;
                break;
        }
        return new Rectangle(x+16*dx,y+16*dy,x+16*dx+16,y+16*dy+16);
    }
}
