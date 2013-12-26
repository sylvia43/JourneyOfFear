package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {
    
    private static Animation up;
    private static Animation down;
    private static Animation left;
    private static Animation right;
    private static Animation sword;
    private static String spritePointer;
    
    private static double x = 64;
    private static double y = 64;
    private static final double speed = 0.1;
    
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
    
    private static int direction;
    
    private static boolean attacking;
    private static int attackTimer;
    private static int attackDelay;
    
    private EntityMask[] maskUp = new EntityMask[4];
    private EntityMask[] maskDown = new EntityMask[4];
    private EntityMask[] maskLeft = new EntityMask[4];
    private EntityMask[] maskRight = new EntityMask[4];
    
    public static void init(GameContainer container, Options options) throws SlickException {
        down = ResourceLoader.initializeAnimation("player_forward.png",166);
        up = ResourceLoader.initializeAnimation("player_backward.png",166);
        right = ResourceLoader.initializeAnimation("player_right.png",166);
        left = ResourceLoader.initializeAnimation("player_left.png",166);
        sword = ResourceLoader.initializeAnimation("sword_slash.png",41,48);
        sword.stop();
        spritePointer = "player_down";
        keybind = options;
        attacking = false;
        attackDelay = 0;
        //new EntityMask(up.getImage(1));
    }
    
    public static void update(GameContainer container, int delta) {
        movePlayer(container.getInput(), delta);
        resolveAttack(container.getInput(), delta, container.getHeight());
    }
    
    public static void render(GameContainer container, Graphics g) throws SlickException {
        Animation player_sprite = null;
        switch(spritePointer) {
            case "player_down":
                player_sprite = down;
                break;
            case "player_up":
                player_sprite = up;
                break;
            case "player_left":
                player_sprite = left;
                break;
            case "player_right":
                player_sprite = right;
                break;
        }
        Input input = container.getInput();
        if (SlickGame.DEBUG_MODE) {
            g.drawString((DnHl?"dh ":"")
                    + (UpHl?"uh ":"")
                    + (LfHl?"lh ":"")
                    + (RiHl?"rh ":"")
                    + (DnPr?"dp ":"")
                    + (UpPr?"up ":"")
                    + (LfPr?"lp ":"")
                    + (RiPr?"rp ":""), 50, 50);
            g.drawString(String.valueOf(direction), 50, 75);
            g.drawString("x: " + String.valueOf(x), 50, 100);
            g.drawString("y: " + String.valueOf(y), 50, 125);
            g.drawString("mx: " + Mouse.getX(), 50, 150);
            g.drawString("my: " + Mouse.getY(), 50, 175);
            g.drawString("dx: " + String.valueOf(Mouse.getX()-x), 50, 200);
            g.drawString("dy: " + String.valueOf(Mouse.getY()+y-container.getHeight()), 50, 225);
            g.drawString(attacking?"Attacking":"Not attacking",50,250);
            g.drawString(String.valueOf(attackTimer),50,275);
            g.drawString(String.valueOf(sword.getFrame()),50,300);
        }
        player_sprite.draw((int)(x*4)-32,(int)(y*4)-32,64,64);
        if (attacking) {
            sword.draw((int)(x*4)-96,(int)(y*4)-96,192,192);
        }
    }
    
    public static void resolveAttack(Input input, int delta, int height) {
        if ((input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && keybind.MOUSE_ATTACK)
                || (input.isKeyDown(keybind.A_UP)
                    ||input.isKeyDown(keybind.A_DOWN)
                    ||input.isKeyDown(keybind.A_LEFT)
                    ||input.isKeyDown(keybind.A_RIGHT))
                && !attacking && attackDelay < 1) {
            double dy = y+Mouse.getY()-height;
            double dx = Mouse.getX()-x;
            //getMouseDirection(dx, dy);
            getKeyboardDirection(input);
            direction = (direction+6)%8;
            attack(direction);
        }
        attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > swordDuration*4.5) {
            attacking = false;
        } else if (sword.getFrame()==(direction+10)%8) {
            sword.stop();
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
    
    public static void getMouseDirection(double dx, double dy) {
        if (dx>=0) {
            if (dy>0) {
                if (dx>=dy)
                    direction = 0;
                else
                    direction = 1;
            }
            else {
                if (dx>=-dy)
                    direction = 7;
                else
                    direction = 6;
            }
        } else {
            if (dy>0) {
                if (-dx>=dy)
                    direction = 3;
                else
                    direction = 2;
            }
            else {
                if (-dx>=-dy)
                    direction = 4;
                else
                    direction = 5;
            }
        }
    }
    
    public static void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + 500;
        sword.restart();
        sword.setCurrentFrame(direction);
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
            spritePointer = "player_down";
        } else {
            down.stop();
        }
        if (DnHl) {
            down.start();
            y += speed*delta;
            if (!UpHl && !LfHl && !RiHl) {
                spritePointer = "player_down";
            }
        } else {
            down.setCurrentFrame(1);
        }
        
        if (RiPr) {
            spritePointer = "player_right";
        } else {
            right.stop();
        }
        if (RiHl) {
            right.start();
            x += speed*delta;
            if (!UpHl && !LfHl && !DnHl) {
                spritePointer = "player_right";
            }
        } else {
            right.setCurrentFrame(1);
        }
        
        if (UpPr) {
            spritePointer = "player_up";
        } else {
            up.stop();
        }
        if (UpHl) {
            up.start();
            y -= speed*delta;
            if (!DnHl && !LfHl && !RiHl) {
                spritePointer = "player_up";
            }
        } else {
            up.setCurrentFrame(1);
        }
        
        if (LfPr) {
            spritePointer = "player_left";
        } else {
            left.stop();
        }
        if (LfHl) {
            left.start();
            x -= speed*delta;
            if (!UpHl && !DnHl && !RiHl) {
                spritePointer = "player_left";
            }
        } else {
            left.setCurrentFrame(1);
        }
    }
}
