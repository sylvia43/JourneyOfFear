package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Enemy {

    private EntitySprite sprite;
    private String spritepath;

    private Animation sword;

    private int spritePointer;
    
    private double x = 64;
    private double y = 64;
    private final double speed = 0.0625;
    
    private final int swordDuration = 48;
    private final int SWORD_DELAY = 400;
    
    private int direction;
    
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;
    
    public void init(GameContainer container, String spritepath) throws SlickException {
        this.spritepath = spritepath;
        initializeSprite();
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }
    
    public void update(GameContainer container, int delta) {
        move(container.getInput(), delta);
        resolveCollision();
        resolveAttack(container.getInput(), delta, container.getHeight());
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.draw((int)(x*4),(int)(y*4),64,64);
        if (attacking) {
            sword.draw((int)(x*4)-64,(int)(y*4)-64,192,192);
        }
    }
    
    public void resolveAttack(Input input, int delta, int height) {
        if (!attacking && attackDelay < 1) {
            getDirection(input);
            direction = (direction+6)%8;
            attack(direction);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > swordDuration*4.5) {
            attacking = false;
        }
    }
    
    public void getDirection(Input input) {
        direction = (int)(Math.random()*4)*2;
    }
    
    public void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction+10)%8);
    }
    
    public void move(Input input, int delta) {
        
        //Code to randomly choose a direction and move/update animations.
        
        if (y>120)
            y=-16;
    }
    
    private void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/right.png",166),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/up.png",166),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/left.png",166),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/down.png",166)
        );
        sprite.setMasks(
                initializeMask(0),
                initializeMask(1),
                initializeMask(2),
                initializeMask(3)
        );
        sword = ResourceLoader.initializeAnimation("resources/player/attacks/sword_slash.png",20,48);
        sword.stop();
    }
    
    private AnimationMask initializeMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }

    private void resolveCollision() {
        //collision = getStaticCollisionMask().intersects(enemy.getCollisionMask(),x,y,enemy.getX(),enemy.getY());
    }

    public ImageMask getCollisionMask() {
        return sprite.getMask(spritePointer).getMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    public ImageMask getStaticCollisionMask() {
        return sprite.getMask(spritePointer).getMask(sprite.getAnim(spritePointer).getFrame());
    }

    public Rectangle getAttackMask() {
        int dx = 0;
        int dy = 0;
        switch(sword.getFrame()) {
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
