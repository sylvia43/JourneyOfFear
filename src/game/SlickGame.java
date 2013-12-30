package game;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame {
    
    private Options options;
    public static final boolean DEBUG_MODE = true;
    private static final int VIEW_SIZE_X = 640;
    private static final int VIEW_SIZE_Y = 512;
    private static final int WORLD_SIZE_X = 6400;
    private static final int WORLD_SIZE_Y = 5120;
    private double camX;
    private double camY;
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
        map = new TiledMap((int)(WORLD_SIZE_X/64),(int)(WORLD_SIZE_Y/64));
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
        camX = MathHelper.median(0,WORLD_SIZE_X-VIEW_SIZE_X,player.getX()*4-VIEW_SIZE_X/2);
        camY = MathHelper.median(0,WORLD_SIZE_Y-VIEW_SIZE_Y,player.getY()*4-VIEW_SIZE_Y/2);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.translate(-(float)camX,-(float)camY);
        renderMap();
        dumbEnemy.render(container, g);
        for (Enemy e : enemies) {
            e.render(container, g);
        }
        player.render(container,g);
    }
    
    private static void setupAGC(AppGameContainer app) throws SlickException {
        app.setDisplayMode((int)VIEW_SIZE_X, (int)VIEW_SIZE_Y, false);
        app.setShowFPS(DEBUG_MODE);
        app.setVSync(true);
        app.setForceExit(true);
        app.setVerbose(DEBUG_MODE);
        app.start();
    }

    private void renderMap() {
        for(int x=(int)(camX/64);x<(camX+VIEW_SIZE_X)/64;x++) {
            for(int y=(int)(camY/64);y<(camY+VIEW_SIZE_Y)/64;y++) {
                 map.tile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
    
    private void initDumbEnemy(GameContainer container) throws SlickException {
        dumbEnemy = new DumbEnemy();
        dumbEnemy.init(container);
    }
}
