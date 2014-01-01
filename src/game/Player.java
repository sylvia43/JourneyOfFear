package game;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player implements Collidable, Attackable {
    
    private EntitySprite sprite;

    private Animation sword;

    private int spritePointer;
    
    private int x = 640;
    private int y = 512;
    private final double speed = 0.5;
    
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    private Options keybind;
    
    private final int SWORD_DURATION = 48;
    private final int SWORD_DELAY = 400;
    
    private int direction;
    
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;
    
    private boolean collision;
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer).getImageMask(sprite.getAnim(spritePointer).getFrame());
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
    
    public Player() { }
    
    public void init(GameContainer container, Options options) throws SlickException {
        initializeSprite();
        spritePointer = 3;
        keybind = options;
        attacking = false;
        attackDelay = 0;
    }
    
    public void update(GameContainer container, int delta) {
        movePlayer(container.getInput(), delta);
        resolveCollision();
        resolveAttack(container.getInput(), delta, container.getHeight());
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        Animation currentSprite = sprite.getAnim(spritePointer);
        if (SlickGame.DEBUG_MODE)
            renderDebugInfo(g);
        currentSprite.draw(x,y,64,64);
        if (attacking) {
            sword.draw(x-64,y-64,192,192);
        }
    }
    
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    private void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(                
                ResourceLoader.initializeAnimation("resources/player/right.png",166),
                ResourceLoader.initializeAnimation("resources/player/up.png",166),
                ResourceLoader.initializeAnimation("resources/player/left.png",166),
                ResourceLoader.initializeAnimation("resources/player/down.png",166)
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
    
    public void movePlayer(Input input, int delta) {
        boolean DnHl = input.isKeyDown(keybind.M_DOWN);
        boolean DnPr = input.isKeyPressed(keybind.M_DOWN);
        boolean UpHl = input.isKeyDown(keybind.M_UP);
        boolean UpPr = input.isKeyPressed(keybind.M_UP);
        boolean LfHl = input.isKeyDown(keybind.M_LEFT);
        boolean LfPr = input.isKeyPressed(keybind.M_LEFT);
        boolean RiHl = input.isKeyDown(keybind.M_RIGHT);
        boolean RiPr = input.isKeyPressed(keybind.M_RIGHT);
        
        if ((DnHl || DnHl) && (UpHl || UpHl)) {
            UpHl = false;
            DnHl = false;
        }
        
        if ((LfHl || LfPr) && (RiHl || RiPr)) {
            LfHl = false;
            RiHl = false;
        }
        
        if (DnPr) {
            spritePointer = 3;
        } else {
            sprite.getAnim(3).stop();
        }
        if (DnHl) {
            sprite.getAnim(3).start();
            y += speed*delta;
            if (!UpHl && !LfHl && !RiHl) {
                spritePointer = 3;
            }
        } else {
            sprite.getAnim(3).setCurrentFrame(1);
        }
        
        if (RiPr) {
            spritePointer = 0;
        } else {
            sprite.getAnim(0).stop();
        }
        if (RiHl) {
            sprite.getAnim(0).start();
            x += speed*delta;
            if (!UpHl && !LfHl && !DnHl) {
                spritePointer = 0;
            }
        } else {
            sprite.getAnim(0).setCurrentFrame(1);
        }
        
        if (UpPr) {
            spritePointer = 1;
        } else {
            sprite.getAnim(1).stop();
        }
        if (UpHl) {
            sprite.getAnim(1).start();
            y -= speed*delta;
            if (!DnHl && !LfHl && !RiHl) {
                spritePointer = 1;
            }
        } else {
            sprite.getAnim(1).setCurrentFrame(1);
        }
        
        if (LfPr) {
            spritePointer = 2;
        } else {
            sprite.getAnim(2).stop();
        }
        if (LfHl) {
            sprite.getAnim(2).start();
            x -= speed*delta;
            if (!UpHl && !DnHl && !RiHl) {
                spritePointer = 2;
            }
        } else {
            sprite.getAnim(2).setCurrentFrame(1);
        }
    }
    
    private void resolveCollision() {
        collision = false; //getCollisionMask().intersects(enemy.getCollisionMask(),x,y,enemy.getX(),enemy.getY());
    }
    
    public void resolveAttack(Input input, int delta, int height) {
        if ((input.isKeyDown(keybind.A_UP)
                || input.isKeyDown(keybind.A_DOWN)
                || input.isKeyDown(keybind.A_LEFT)
                || input.isKeyDown(keybind.A_RIGHT))
                && !attacking && attackDelay < 1) {
            getKeyboardDirection(input);
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
    
    public void getKeyboardDirection(Input input) {
        if (input.isKeyDown(keybind.A_RIGHT))
            direction = 0;
        else if (input.isKeyDown(keybind.A_UP))
            direction = 2;
        else if (input.isKeyDown(keybind.A_LEFT))
            direction = 4;
        else if (input.isKeyDown(keybind.A_DOWN))
            direction = 6;
    }
    
    public void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction+10)%8);
    }

    private void renderDebugInfo(Graphics g) {
            g.setColor(Color.white);
            g.drawString("x: " + String.valueOf(x),10,38);
            g.drawString("y: " + String.valueOf(y),10,52);
            g.drawString(attacking?"Attacking":"Not attacking",10,66);
            g.drawString(String.valueOf(attackTimer),10,80);
            g.drawString(collision?"Colliding":"Not Colliding",10,94);
    }
}
