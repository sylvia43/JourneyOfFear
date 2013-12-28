package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player implements Collidable, Attackable {
    
    private static EntitySprite sprite;

    private static Animation sword;

    private static int spritePointer;
    
    private static double x = 64;
    private static double y = 64;
    private static final double speed = 0.125;
    
    private static Options keybind;
    
    private static boolean DnHl;
    private static boolean UpHl;
    private static boolean LfHl;
    private static boolean RiHl;
    private static boolean DnPr;
    private static boolean UpPr;
    private static boolean LfPr;
    private static boolean RiPr;
    
    private static final int swordDuration = 48;
    private static final int SWORD_DELAY = 400;
    
    private static int direction;
    
    private static boolean attacking;
    private static int attackTimer;
    private static int attackDelay;
    
    private static DumbEnemy enemy;
    private static boolean collision;
    
    public static void init(GameContainer container, Options options, DumbEnemy enemy) throws SlickException {
        initializeSprite();
        spritePointer = 3;
        keybind = options;
        attacking = false;
        attackDelay = 0;
        Player.enemy = enemy;
    }
    
    public static void update(GameContainer container, int delta) {
        movePlayer(container.getInput(), delta);
        resolveCollision();
        resolveAttack(container.getInput(), delta, container.getHeight());
    }
    
    public static void render(GameContainer container, Graphics g) throws SlickException {
        Animation currentSprite = sprite.getAnim(spritePointer);
        if (SlickGame.DEBUG_MODE) {
            g.setColor(Color.white);
            g.drawString("Keys:"
                    + (DnHl?"dh ":"")
                    + (UpHl?"uh ":"")
                    + (LfHl?"lh ":"")
                    + (RiHl?"rh ":"")
                    + (DnPr?"dp ":"")
                    + (UpPr?"up ":"") 
                    + (LfPr?"lp ":"")
                    + (RiPr?"rp ":""),10,24);
            g.drawString("x: " + String.valueOf(x),10,38);
            g.drawString("y: " + String.valueOf(y),10,52);
            g.drawString(attacking?"Attacking":"Not attacking",10,66);
            g.drawString(String.valueOf(attackTimer),10,80);
            g.drawString(collision?"Colliding":"Not Colliding",10,94);
        }
        currentSprite.draw((int)(x*4),(int)(y*4),64,64);
        if (attacking) {
            sword.draw((int)(x*4)-64,(int)(y*4)-64,192,192);
        }
    }
    
    public static void resolveAttack(Input input, int delta, int height) {
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
        if (attackTimer > swordDuration*4.5) {
            attacking = false;
        }
    }
    
    public static void getKeyboardDirection(Input input) {
        if (input.isKeyDown(keybind.A_RIGHT))
            direction = 0;
        else if (input.isKeyDown(keybind.A_UP))
            direction = 2;
        else if (input.isKeyDown(keybind.A_LEFT))
            direction = 4;
        else if (input.isKeyDown(keybind.A_DOWN))
            direction = 6;
    }
    
    public static void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction+10)%8);
    }
    
    public static void movePlayer(Input input, int delta) {
        DnHl = input.isKeyDown(keybind.M_DOWN);
        DnPr = input.isKeyPressed(keybind.M_DOWN);
        UpHl = input.isKeyDown(keybind.M_UP);
        UpPr = input.isKeyPressed(keybind.M_UP);
        LfHl = input.isKeyDown(keybind.M_LEFT);
        LfPr = input.isKeyPressed(keybind.M_LEFT);
        RiHl = input.isKeyDown(keybind.M_RIGHT);
        RiPr = input.isKeyPressed(keybind.M_RIGHT);
        
        if ((DnHl || DnHl) && (UpHl || UpHl)) {
            UpHl = false;
            DnHl = false;
        }
        
        if ((LfHl || LfPr) && (RiHl || RiPr)) {
            LfHl = false;
            RiHl = false;
        }
        
        if (DnHl) {
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
    
    private static void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        sprite.setAnimations(                
                ResourceLoader.initializeAnimation("resources/player/player_right.png",166),
                ResourceLoader.initializeAnimation("resources/player/player_backward.png",166),
                ResourceLoader.initializeAnimation("resources/player/player_left.png",166),
                ResourceLoader.initializeAnimation("resources/player/player_forward.png",166)
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
    
    private static AnimationMask initializeMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }

    private static void resolveCollision() {
        collision = getStaticCollisionMask().intersects(enemy.getCollisionMask(),x,y,enemy.getX(),enemy.getY());
    }

    public ImageMask getCollisionMask() {
        return sprite.getMask(spritePointer).getMask(sprite.getAnim(spritePointer).getFrame());
    }
    
    public static ImageMask getStaticCollisionMask() {
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
