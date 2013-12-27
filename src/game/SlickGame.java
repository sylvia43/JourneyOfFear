package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
    
    Options options;
    public static final boolean DEBUG_MODE = true;
    public static Image background_grass;
    public Enemy enemy;
    
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
            app.setVerbose(DEBUG_MODE);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        options = new Options();
        background_grass = ResourceLoader.initializeImage("tiles/back_grass.png");
        enemy = new Enemy();
        enemy.init(container);
        Player.init(container, options, enemy);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Player.update(container,delta);
        enemy.update(container, delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for(int x = 0; x < container.getWidth(); x += 64) {
            for(int y = 0; y < container.getHeight(); y += 64) {
                 background_grass.draw(x,y,64,64);
            }
        }
        enemy.render(container, g);
        Player.render(container,g);
    }
}
