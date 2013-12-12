package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {

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
        
        try {
            Image player_forward_img = new Image("player_forward.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        
        //SpriteSheet player_forward_sheet = new SpriteSheet();
        //Animation player_forward_anim = new Animation();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        
    }
}
