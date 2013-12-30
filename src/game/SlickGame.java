package game;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
    
    Options options;
    public static final boolean DEBUG_MODE = true;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private DumbEnemy dumbEnemy;
    private TiledMap map;
    private Player player;
    
    public SlickGame() {
        super("Slick Game");
    }

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new SlickGame());
            setupAGC(app);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        map = new TiledMap(10,8);
        options = new Options();
        
        initDumbEnemy(container);
        
        Enemy enemy = new Enemy("blobredsir");
        enemy.init(container);
        enemies.add(enemy);
        
        for (Enemy e : enemies) {
            e.init(container);
        }
        
        player = new Player();
        player.init(container, options, dumbEnemy);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        dumbEnemy.update(container, delta);
        for (Enemy e : enemies) {
            e.update(container, delta);
        }
        player.update(container,delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        renderMap(container,g);
        dumbEnemy.render(container, g);
        for (Enemy e : enemies) {
            e.render(container, g);
        }
        player.render(container,g);
    }
    
    private static void setupAGC(AppGameContainer app) throws SlickException {
        app.setDisplayMode(640, 512, false);
        app.setShowFPS(DEBUG_MODE);
        app.setVSync(true);
        app.setForceExit(true);
        app.setVerbose(DEBUG_MODE);
        app.start();
    }

    private void renderMap(GameContainer container,Graphics g) {
        for(int x=0;x<container.getWidth()/64;x++) {
            for(int y=0;y<container.getHeight()/64;y++) {
                 map.tile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
    
    private void initDumbEnemy(GameContainer container) throws SlickException {
        dumbEnemy = new DumbEnemy();
        dumbEnemy.init(container);
    }
}
