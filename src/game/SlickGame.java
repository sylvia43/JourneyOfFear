package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
        
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
        Player.init(container);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Player.update(container,delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        Player.render(container,g);
    }
}
