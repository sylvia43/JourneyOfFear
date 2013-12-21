package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {
    
    private static Animation spr_player_up;
    private static Animation spr_player_down;
    private static Animation spr_player_left;
    private static Animation spr_player_right;
    private static Animation spr_sword;
    private static String player_sprite_pointer;
    private static double player_x = 128;
    private static double player_y = 128;
    private static final double player_speed = 0.25;
    private static Options keybind;
    
    private static boolean _downHeld;
    private static boolean _upHeld;
    private static boolean _leftHeld;
    private static boolean _rightHeld;
    private static boolean _downPressed;
    private static boolean _upPressed;
    private static boolean _leftPressed;
    private static boolean _rightPressed;
    
    private static int swordDuration = 48;
    
    private static int _direction;
    
    private static boolean attacking;
    private static int attackTimer;
    private static int attackDelay;
    
    public static void init(GameContainer container, Options options) throws SlickException {
        spr_player_down = ResourceLoader.initializeAnimation("player_forward.png",4,16,166);
        spr_player_up = ResourceLoader.initializeAnimation("player_backward.png",4,16,166);
        spr_player_right = ResourceLoader.initializeAnimation("player_right.png",4,16,166);
        spr_player_left = ResourceLoader.initializeAnimation("player_left.png",4,16,166);
        spr_sword = ResourceLoader.initializeAnimation("sword_slash.png",4,swordDuration,41);
        spr_sword.stop();
        player_sprite_pointer = "player_down";
        keybind = options;
        attacking = false;
        attackDelay = 0;
    }
    
    public static void update(GameContainer container, int delta) {
        movePlayer(container.getInput(), delta);
        resolveAttack(container.getInput(), delta, container.getHeight());
    }
    
    public static void render(GameContainer container, Graphics g) throws SlickException {
        Animation player_sprite = null;
        switch(player_sprite_pointer) {
            case "player_down":
                player_sprite = spr_player_down;
                break;
            case "player_up":
                player_sprite = spr_player_up;
                break;
            case "player_left":
                player_sprite = spr_player_left;
                break;
            case "player_right":
                player_sprite = spr_player_right;
                break;
        }
        Input input = container.getInput();
        if (SlickGame.DEBUG_MODE) {
            g.drawString((_downHeld?"dh ":"")
                    + (_upHeld?"uh ":"")
                    + (_leftHeld?"lh ":"")
                    + (_rightHeld?"rh ":"")
                    + (_downPressed?"dp ":"")
                    + (_upPressed?"up ":"")
                    + (_leftPressed?"lp ":"")
                    + (_rightPressed?"rp ":""), 50, 50);
            g.drawString(String.valueOf(_direction), 50, 75);
            g.drawString("x: " + String.valueOf(player_x), 50, 100);
            g.drawString("y: " + String.valueOf(player_y), 50, 125);
            g.drawString("mx: " + Mouse.getX(), 50, 150);
            g.drawString("my: " + Mouse.getY(), 50, 175);
            g.drawString("dx: " + String.valueOf(Mouse.getX()-player_x), 50, 200);
            g.drawString("dy: " + String.valueOf(Mouse.getY()+player_y-container.getHeight()), 50, 225);
            g.drawString(attacking?"Attacking":"Not attacking",50,250);
            g.drawString(String.valueOf(attackTimer),50,275);
            g.drawString(String.valueOf(spr_sword.getFrame()),50,300);
        }
        player_sprite.draw((int)player_x-32,(int)player_y-32);
        if (attacking) {
            spr_sword.draw((int)player_x-96,(int)player_y-96);
        }
    }
    
    public static void resolveAttack(Input input, int delta, int height) {
        if ((input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && keybind.MOUSE_ATTACK)
                || (input.isKeyDown(Input.KEY_UP)
                    ||input.isKeyDown(Input.KEY_DOWN)
                    ||input.isKeyDown(Input.KEY_LEFT)
                    ||input.isKeyDown(Input.KEY_RIGHT))
                && !attacking && attackDelay < 1) {
            double dy = player_y+Mouse.getY()-height;
            double dx = Mouse.getX()-player_x;
            //getMouseDirection(dx, dy);
            getKeyboardDirection(input);
            int direction = _direction;
            attack(direction);
        }
        attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > swordDuration*4.5) {
            attacking = false;
        } else if (attackTimer > swordDuration*3) {
            spr_sword.stop();
        }
    }
    
    public static void getKeyboardDirection(Input input) {
        if (input.isKeyDown(Input.KEY_RIGHT))
            _direction = 0;
        else if (input.isKeyDown(Input.KEY_UP))
            _direction = 2;
        else if (input.isKeyDown(Input.KEY_LEFT))
            _direction = 4;
        else if (input.isKeyDown(Input.KEY_DOWN))
            _direction = 6;
    }
    
    public static void getMouseDirection(double dx, double dy) {
        if (dx>=0) {
            if (dy>0) {
                if (dx>=dy)
                    _direction = 0;
                else
                    _direction = 1;
            }
            else {
                if (dx>=-dy)
                    _direction = 7;
                else
                    _direction = 6;
            }
        } else {
            if (dy>0) {
                if (-dx>=dy)
                    _direction = 3;
                else
                    _direction = 2;
            }
            else {
                if (-dx>=-dy)
                    _direction = 4;
                else
                    _direction = 5;
            }
        }
    }
    
    public static void attack(int direction) {
        attacking = true;
        attackTimer = 0;
        attackDelay = spr_sword.getDuration(0)*2 + 500;
        spr_sword.restart();
        spr_sword.setCurrentFrame(direction);
    }
    
    public static void movePlayer(Input input, int delta) {
        boolean downHeld = _downHeld = input.isKeyDown(keybind.KEY_DOWN);
        boolean downPressed = _downPressed  = input.isKeyPressed(keybind.KEY_DOWN);
        boolean upHeld = _upHeld  = input.isKeyDown(keybind.KEY_UP);
        boolean upPressed = _upPressed  = input.isKeyPressed(keybind.KEY_UP);
        boolean leftHeld = _leftHeld  = input.isKeyDown(keybind.KEY_LEFT);
        boolean leftPressed = _leftPressed  = input.isKeyPressed(keybind.KEY_LEFT);
        boolean rightHeld = _rightHeld = input.isKeyDown(keybind.KEY_RIGHT);
        boolean rightPressed = _rightPressed  = input.isKeyPressed(keybind.KEY_RIGHT);
        
        if ((downHeld || downPressed) && (upHeld || upPressed)) {
            upHeld = false;
            downHeld = false;
        }
        
        if ((leftHeld || leftPressed) && (rightHeld || rightPressed)) {
            leftHeld = false;
            rightHeld = false;
        }
        
        if (downPressed) {
            player_sprite_pointer = "player_down";
        } else {
            spr_player_down.stop();
        }
        if (downHeld) {
            spr_player_down.start();
            player_y += player_speed*delta;
            if (!upHeld && !leftHeld && !rightHeld) {
                player_sprite_pointer = "player_down";
            }
        } else {
            spr_player_down.setCurrentFrame(1);
        }
        
        if (rightPressed) {
            player_sprite_pointer = "player_right";
        } else {
            spr_player_right.stop();
        }
        if (rightHeld) {
            spr_player_right.start();
            player_x += player_speed*delta;
            if (!upHeld && !leftHeld && !downHeld) {
                player_sprite_pointer = "player_right";
            }
        } else {
            spr_player_right.setCurrentFrame(1);
        }
        
        if (upPressed) {
            player_sprite_pointer = "player_up";
        } else {
            spr_player_up.stop();
        }
        if (upHeld) {
            spr_player_up.start();
            player_y -= player_speed*delta;
            if (!downHeld && !leftHeld && !rightHeld) {
                player_sprite_pointer = "player_up";
            }
        } else {
            spr_player_up.setCurrentFrame(1);
        }
        
        if (leftPressed) {
            player_sprite_pointer = "player_left";
        } else {
            spr_player_left.stop();
        }
        if (leftHeld) {
            spr_player_left.start();
            player_x -= player_speed*delta;
            if (!upHeld && !downHeld && !rightHeld) {
                player_sprite_pointer = "player_left";
            }
        } else {
            spr_player_left.setCurrentFrame(1);
        }
    }
}
