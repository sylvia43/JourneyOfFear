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
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private Area area;
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
        setupArea();
        setupOptions();
        initEnemies(container);
        initPlayer(container);
    }

    public void update(GameContainer container, int delta) throws SlickException {
        updateEnemies(container,delta);
        updatePlayer(container,delta);
        updateViewPort();
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        translateView(g);
        renderMap();
        renderEnemies(container,g);
        renderPlayer(container,g);
    }
    
    private static void setupAGC(AppGameContainer app) throws SlickException {
        app.setDisplayMode((int)VIEW_SIZE_X, (int)VIEW_SIZE_Y, false);
        app.setShowFPS(DEBUG_MODE);
        app.setVSync(true);
        app.setForceExit(true);
        app.setVerbose(DEBUG_MODE);
        app.start();
    }
    
    private void setupArea() {
        area = new Area(new TiledMap((int)(WORLD_SIZE_X/64),(int)(WORLD_SIZE_Y/64)));
    }
    
    private void setupOptions() {
        options = new Options();
    }
    
    private void initPlayer(GameContainer container) throws SlickException {
        player = new Player();
        player.setEnemies(enemies);
        player.init(container, options);
    }
    
    private void initEnemies(GameContainer container) throws SlickException {
        EnemyBlob enemy = new EnemyBlob("blobredsir", player);
        enemies.add(enemy);
        for (Enemy e : enemies) {
            e.init(container);
        }
    }
    
    private void updateEnemies(GameContainer container, int delta) {
        for (Enemy e : enemies) {
            e.update(container, delta);
        }        
    }
        
    private void updatePlayer(GameContainer container, int delta) {
        player.setEnemies(enemies);
        player.update(container,delta);     
    }
    
    private void updateViewPort() {
        camX = (int)MathHelper.median(0,WORLD_SIZE_X-VIEW_SIZE_X,player.getX()-VIEW_SIZE_X/2);
        camY = (int)MathHelper.median(0,WORLD_SIZE_Y-VIEW_SIZE_Y,player.getY()-VIEW_SIZE_Y/2);
        player.updateViewPort(camX,camY);
    }
    
    private void translateView(Graphics g) {
        g.translate(-(float)camX,-(float)camY);
    }
    
    private void renderMap() {
        for(int x=camX/64;x<Math.min(WORLD_SIZE_X/64,(camX+VIEW_SIZE_X)/64+1);x++) {
            for(int y=camY/64;y<Math.min(WORLD_SIZE_Y/64,(camY+VIEW_SIZE_Y)/64+1);y++) {
                 area.getTile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
    
    private void renderEnemies(GameContainer container, Graphics g) throws SlickException {
        for (Enemy e : enemies) {
            if (e.getX()>camX-64 && e.getY()>camY-64
                && e.getX()<camX+VIEW_SIZE_X && e.getY()<camY+VIEW_SIZE_Y)
            e.render(container, g);
        }
    }
    
    private void renderPlayer(GameContainer container, Graphics g) throws SlickException {
        player.render(container,g);
    }
}
