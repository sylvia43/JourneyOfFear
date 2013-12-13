package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {
    private static Animation player_up;
    private static Animation player_down;
    private static Animation player_left;
    private static Animation player_right;
    private static String player_sprite_pointer;
    private static double player_x = 128;
    private static double player_y = 128;
    private static final double player_speed = 0.25;
    private static double player_frame;
    private static Options keybind;
    
    public static void init(GameContainer container, Options options) throws SlickException {
        player_down = ResourceLoader.initializeAnimation("player_forward.png",4,166); //166
        player_up = ResourceLoader.initializeAnimation("player_backward.png",4,166);
        player_right = ResourceLoader.initializeAnimation("player_right.png",4,166);
        player_left = ResourceLoader.initializeAnimation("player_left.png",4,166);
        player_sprite_pointer = "player_down";
        player_frame = 0;
        keybind = options;
    }
    public static void update(GameContainer container, int delta) {
        Input input = container.getInput();
        
        boolean downDown = input.isKeyDown(keybind.KEY_DOWN);
        boolean downPressed = input.isKeyPressed(keybind.KEY_DOWN);
        boolean upDown = input.isKeyDown(keybind.KEY_UP);
        boolean upPressed = input.isKeyPressed(keybind.KEY_UP);
        boolean leftDown = input.isKeyDown(keybind.KEY_LEFT);
        boolean leftPressed = input.isKeyPressed(keybind.KEY_LEFT);
        boolean rightDown = input.isKeyDown(keybind.KEY_RIGHT);
        boolean rightPressed = input.isKeyPressed(keybind.KEY_RIGHT);
        
        if (downPressed) {
            player_sprite_pointer = "player_down";
        } else {
            player_down.stop();
        }
        if (downDown) {
            player_down.start();
            player_y += player_speed*delta;
            if (!upDown && !leftDown && !rightDown) {
                player_sprite_pointer = "player_down";
            }
        } else {
            player_down.setCurrentFrame(1);
        }
        
        if (rightPressed) {
            player_sprite_pointer = "player_right";
        } else {
            player_right.stop();
        }
        if (rightDown) {
            player_right.start();
            player_x += player_speed*delta;
            if (!upDown && !leftDown && !downDown) {
                player_sprite_pointer = "player_right";
            }
        } else {
            player_right.setCurrentFrame(1);
        }
        
        if (upPressed) {
            player_sprite_pointer = "player_up";
        } else {
            player_up.stop();
        }
        if (upDown) {
            player_up.start();
            player_y -= player_speed*delta;
            if (!downDown && !leftDown && !rightDown) {
                player_sprite_pointer = "player_up";
            }
        } else {
            player_up.setCurrentFrame(1);
        }
        
        if (leftPressed) {
            player_sprite_pointer = "player_left";
        } else {
            player_left.stop();
        }
        if (leftDown) {
            player_left.start();
            player_x -= player_speed*delta;
            if (!upDown && !downDown && !rightDown) {
                player_sprite_pointer = "player_left";
            }
        } else {
            player_left.setCurrentFrame(1);
        }
        
    }
    public static void render(GameContainer container, Graphics g) throws SlickException {
        Animation player_sprite = null;
        switch(player_sprite_pointer) {
            case "player_down":
                player_sprite = player_down;
                break;
            case "player_up":
                player_sprite = player_up;
                break;
            case "player_left":
                player_sprite = player_left;
                break;
            case "player_right":
                player_sprite = player_right;
                break;
        }
        player_sprite.draw((int)player_x,(int)player_y);
    }
}
