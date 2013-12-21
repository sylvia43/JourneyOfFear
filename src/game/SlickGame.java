package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
    
    Options options;
    public static final boolean DEBUG_MODE = false;
    public static Image background_grass;
    
    public SlickGame() {
        super("Slick Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new SlickGame());
            app.setDisplayMode(600, 480, false);
            app.setShowFPS(DEBUG_MODE);
            app.setVSync(true);
            app.setForceExit(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        options = new Options();
        background_grass = ResourceLoader.initializeImage("back_grass.png", 4);
        Player.init(container, options);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Player.update(container,delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for(int x = 0; x < container.getWidth(); x += background_grass.getWidth()) {
            for(int y = 0; y < container.getHeight(); y += background_grass.getHeight()) {
                 background_grass.draw(x, y);
            }
       }
        Player.render(container,g);
    }
}
