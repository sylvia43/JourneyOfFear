package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SlickGame extends BasicGame {
    
    Animation player_up;
    Animation player_down;
    Animation player_left;
    Animation player_right;
    Animation player_sprite;
    double player_x = 128;
    double player_y = 128;
    final double player_speed = 0.25;
        
    public SlickGame() {
        super("Slick Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new SlickGame());
            app.setDisplayMode(600, 480, false);
            app.setShowFPS(true);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        player_down = initializeAnimation("player_forward.png",4,166); //166
        player_up = initializeAnimation("player_backward.png",4,166);
        player_right = initializeAnimation("player_right.png",4,166);
        player_sprite = player_down;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP)) {
            player_sprite = player_up;
            //player_up.setCurrentFrame();
            player_y -= player_speed*delta;
        } else {
            
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            player_sprite = player_down;
            player_y += player_speed*delta;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        player_sprite.draw((int)player_x,(int)player_y);
    }
    
    public Animation initializeAnimation(String filepath, float scale, int delay) throws SlickException {
        Image image = new Image(filepath);
        image.setFilter(Image.FILTER_NEAREST);
        image = image.getScaledCopy(scale);
        return new Animation(new SpriteSheet(image,(int)(16*scale),(int)(16*scale)),delay);
    }
}
