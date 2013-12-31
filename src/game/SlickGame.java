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
    public static final int VIEW_SIZE_X = 640;
    public static final int VIEW_SIZE_Y = 512;
    public static final int WORLD_SIZE_X = 3200;
    public static final int WORLD_SIZE_Y = 2560;
    private int camX;
    private int camY;
    private ArrayList<EnemyBlob> enemies = new ArrayList<EnemyBlob>();
    private ArrayList<DumbEnemy> dumbEnemies = new ArrayList<DumbEnemy>();
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
        
        DumbEnemy dumbEnemy = new DumbEnemy();
        dumbEnemy.init(container);
        dumbEnemies.add(dumbEnemy);
        
        for (DumbEnemy e : dumbEnemies) {
            e.init(container);
        }
        
        EnemyBlob enemy = new EnemyBlob("blobredsir");
        enemy.init(container);
        enemies.add(enemy);
        
        for (EnemyBlob e : enemies) {
            e.init(container);
        }
        
        player = new Player();
        player.init(container, options);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        for (DumbEnemy e : dumbEnemies) {
            e.update(container, delta);
        }
        for (EnemyBlob e : enemies) {
            e.update(container, delta);
        }
        player.update(container,delta);
        camX = (int)MathHelper.median(0,WORLD_SIZE_X-VIEW_SIZE_X,player.getX()-VIEW_SIZE_X/2);
        camY = (int)MathHelper.median(0,WORLD_SIZE_Y-VIEW_SIZE_Y,player.getY()-VIEW_SIZE_Y/2);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.translate(-(float)camX,-(float)camY);
        renderMap();
        for (DumbEnemy e : dumbEnemies) {
            if (e.getX()>camX-64 && e.getY()>camY-64
                    && e.getX()<camX+VIEW_SIZE_X && e.getY()<camY+VIEW_SIZE_Y)
                e.render(container, g);
        }
        for (EnemyBlob e : enemies) {
            if (e.getX()>camX-64 && e.getY()>camY-64
                && e.getX()<camX+VIEW_SIZE_X && e.getY()<camY+VIEW_SIZE_Y)
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
        for(int x=camX/64;x<Math.min(WORLD_SIZE_X/64,(camX+VIEW_SIZE_X)/64+1);x++) {
            for(int y=camY/64;y<Math.min(WORLD_SIZE_Y/64,(camY+VIEW_SIZE_Y)/64+1);y++) {
                 map.tile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
}
