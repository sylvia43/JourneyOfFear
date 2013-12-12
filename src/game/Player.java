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
    private static Animation player_sprite;
    private static double player_x = 128;
    private static double player_y = 128;
    private static final double player_speed = 0.25;
    private static int player_frame = 0;
    
    public static void init(GameContainer container) throws SlickException {
        player_down = ResourceLoader.initializeAnimation("player_forward.png",4,166); //166
        player_up = ResourceLoader.initializeAnimation("player_backward.png",4,166);
        player_right = ResourceLoader.initializeAnimation("player_right.png",4,166);
        player_sprite = player_down;
    }
    public static void update(GameContainer container, int delta) {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            player_sprite = player_up;
            //player_up.setCurrentFrame();
            player_y -= player_speed*delta;
        } else {
            
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player_sprite = player_down;
            player_sprite.setCurrentFrame(player_frame%4);
            player_y += player_speed*delta;
        } else {
            
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            player_sprite = player_right;
            player_x += player_speed*delta;
        } else {
            
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            player_sprite = player_down;
            player_x -= player_speed*delta;
        } else {
            
        }
        player_frame++;
    }
    public static void render(GameContainer container, Graphics g) throws SlickException {
        player_sprite.draw((int)player_x,(int)player_y);
    }
}
