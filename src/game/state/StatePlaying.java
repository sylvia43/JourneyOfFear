package game.state;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.util.MathHelper;
import game.util.Soundtrack;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StatePlaying extends BasicGameState {
    
    public static final boolean DEBUG_MODE = false;
    public static final boolean DEBUG_COLLISION = false;
    public static final int VIEW_SIZE_X = 640;
    public static final int VIEW_SIZE_Y = 512;
    public static final int WORLD_SIZE_X = VIEW_SIZE_X*4;
    public static final int WORLD_SIZE_Y = VIEW_SIZE_Y*4;
    
    private final Color MINIMAP_BLACK = new Color(0f,0f,0f,0.5f);
    private final Color PLAYER_COLOR = Color.green;
        
    private int camX;
    private int camY;
    private Area currentArea;
    private Player player;
    private int id;
    private Soundtrack soundtrack;
    

    public StatePlaying(int id) {
        this.id = id;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        soundtrack = new Soundtrack();
        soundtrack.init();
        initPlayer(container);
        setupArea(container,player);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_N))
            soundtrack.playNext();
        if (container.getInput().isKeyPressed(Input.KEY_P))
            soundtrack.pause();
        if (container.getInput().isKeyPressed(Input.KEY_R))
            soundtrack.restart();
        soundtrack.update();
        updateArea();
        updateEnemies(container,delta);
        updatePlayer(container,delta);
        updateViewPort();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        translateView(g);
        renderMap(g);
        renderEnemies(container,g);
        renderPlayer(container,g);
    }
    
    private void setupArea(GameContainer container, Player player) {
        currentArea = new Area(WORLD_SIZE_X,WORLD_SIZE_Y,container,player);
        player.setEnemies(currentArea.getEnemies());
    }
    
    private void initPlayer(GameContainer container) throws SlickException {
        player = new Player();
        player.init(container);
    }
    
    private void updateArea() {
        ArrayList<Enemy> newCurrentAreaEnemyList = new ArrayList<Enemy>(currentArea.getEnemies());
        for (Enemy e : currentArea.getEnemies()) {
            if (e.readyToDie()) {
                newCurrentAreaEnemyList.remove(e);
                e = null;
                continue;
            }
            if (e.getX()<-16) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getLeft().getEnemies().add(e);
                e.setX(currentArea.getWidth()-160);
            }
            if (e.getY()<-16) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getUp().getEnemies().add(e);
                e.setY(currentArea.getHeight()-160);
            }
            if (e.getX()>currentArea.getWidth()-48) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getRight().getEnemies().add(e);
                e.setX(96);
            }
            if (e.getY()>currentArea.getHeight()-48) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getDown().getEnemies().add(e);
                e.setY(96);
            }
        }
        currentArea.getEnemies().clear();
        currentArea.getEnemies().addAll(newCurrentAreaEnemyList);
        if (player.getX()<-16) {
            currentArea = currentArea.getLeft();
            player.setX(currentArea.getWidth()-48);
        }
        if (player.getY()<-16) {
            currentArea = currentArea.getUp();
            player.setY(currentArea.getHeight()-48);
        }
        if (player.getX()>currentArea.getWidth()-48) {
            currentArea = currentArea.getRight();
            player.setX(-16);
        }
        if (player.getY()>currentArea.getHeight()-48) {
            currentArea = currentArea.getDown();
            player.setY(-16);
        }
    }
    
    private void updateEnemies(GameContainer container, int delta) {
        for (Enemy e : currentArea.getEnemies()) {
            e.update(container, delta);
        }
    }
        
    private void updatePlayer(GameContainer container, int delta) {
        player.setEnemies(currentArea.getEnemies());
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
    
    private void renderMap(Graphics g) {
        for(int x=camX/64;x<Math.min(WORLD_SIZE_X/64,(camX+VIEW_SIZE_X)/64+1);x++) {
            for(int y=camY/64;y<Math.min(WORLD_SIZE_Y/64,(camY+VIEW_SIZE_Y)/64+1);y++) {
                 currentArea.getTile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
        
        g.setColor(MINIMAP_BLACK);
        int posX = (int)(7.5 *VIEW_SIZE_X)/10 + camX;
        int posY = (int)(.75 *VIEW_SIZE_Y)/10 + camY;
        int width = (int)(2.3 *VIEW_SIZE_X)/10;
        int height = (int)(((double)WORLD_SIZE_Y / WORLD_SIZE_X)*(2.3 *VIEW_SIZE_X)/10);
        
        g.fillRect(posX, posY, width, height);
        
        for (Enemy e : currentArea.getEnemies()){
             g.setColor(e.getColor());
             g.fillRect((int)(posX + width*((double)e.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)e.getY())/WORLD_SIZE_Y), 3, 3);    
        }
        
        g.setColor(PLAYER_COLOR);
        g.fillRect((int)(posX + width*((double)player.getX())/WORLD_SIZE_X), 
                (int)(posY + height*((double)player.getY())/WORLD_SIZE_Y), 3, 3);
        g.setColor(Color.black);
        g.setColor(new Color(0f,0f,0f,0.5f));
        g.setColor(MINIMAP_BLACK);
        int miniOriginX = (int)(7.5 *VIEW_SIZE_X)/10 + camX;
        int miniOriginY = (int)(.75 *VIEW_SIZE_Y)/10 + camY;
        int miniDistX = (int)(2.3 *VIEW_SIZE_X)/10;
        int miniDistY = (int)(((double)WORLD_SIZE_Y / WORLD_SIZE_X)*(2.3 *VIEW_SIZE_X)/10);
        g.fillRect(miniOriginX, miniOriginY, miniDistX, miniDistY);
        
        ArrayList<Enemy> list = currentArea.getEnemies();
        for (Enemy e : list){
             g.setColor(Color.red);
             g.fillRect((int)(miniOriginX + miniDistX*((double)e.getX())/WORLD_SIZE_X), 
             (int)(miniOriginY + miniDistY*((double)e.getY())/WORLD_SIZE_Y), 3, 3);    
        }
        
         g.setColor(PLAYER_COLOR);
             g.fillRect((int)(miniOriginX + miniDistX*((double)player.getX())/WORLD_SIZE_X), 
             (int)(miniOriginY + miniDistY*((double)player.getY())/WORLD_SIZE_Y), 3, 3);    
    }
    
    private void renderEnemies(GameContainer container, Graphics g) throws SlickException {
        for (Enemy e : currentArea.getEnemies()) {
            if (e.getX()>camX-64 && e.getY()>camY-64
                && e.getX()<camX+VIEW_SIZE_X && e.getY()<camY+VIEW_SIZE_Y)
                e.render(container, g);
        }
    }
    
    private void renderPlayer(GameContainer container, Graphics g) throws SlickException {
        player.render(container,g);
    }

    @Override
    public int getID() {
        return id;
    }
}
