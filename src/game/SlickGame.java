package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SlickGame extends BasicGame {
    
    Animation sprite;
        
    public SlickGame() {
        super("Slick Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new SlickGame());
            app.setDisplayMode(600, 480, false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        Image player_forward_img = new Image("player_forward.png");
        SpriteSheet player_forward_sheet = new SpriteSheet(player_forward_img,16,16);
        Animation player_forward_anim = new Animation(player_forward_sheet, 50);
        sprite = player_forward_anim;
        System.out.println("Init called.");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(32,32);
    }
}
