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
    
    private int x;
    private int y;
    private final double speed = 0.25;
    
    private final int SWORD_DURATION = 48;
    private final int SWORD_DELAY = 400;
    private final int ANIM_SPEED = 332;
    
    private int direction;
    
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;
    
    public Enemy(String spritepath) {
        this.spritepath = spritepath;
        this.x = 960;
        this.y = 768;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void init(GameContainer container) throws SlickException {
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
        currentSprite.draw(x,y,64,64);
        if (attacking) {
            sword.draw(x-64,y-64,192,192);
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
        if (attackTimer > SWORD_DURATION*4.5) {
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
    }
    
    private void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/right.png",ANIM_SPEED),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/up.png",ANIM_SPEED),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/left.png",ANIM_SPEED),
                ResourceLoader.initializeAnimation("resources/" + spritepath + "/down.png",ANIM_SPEED)
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
