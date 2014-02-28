package game.state;

import game.Game;
import game.enemy.Enemy;
import game.enemy.EnemyPlayer;
import game.environment.Hazard;
import game.environment.Obstacle;
import game.map.Area;
import game.player.Player;
import game.util.MathHelper;
import game.util.Soundtrack;
import game.util.server.DataPacket;
import game.util.server.NetworkHandler;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateMultiplayer extends BasicGameState {
    
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
    
    private ArrayList<EnemyPlayer> enemyPlayers = new ArrayList<EnemyPlayer>();
    
    private NetworkHandler network;

    public StateMultiplayer(int id) {
        this.id = id;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        soundtrack = new Soundtrack();
        try {
            initPlayer(container);
        } catch (SlickException e) {
            System.out.println("Failed to load player: " + e);
        }
        setupArea(container,player);
        soundtrack.init();
        network = new NetworkHandler("127.0.0.1",9999);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            close();
            container.exit();
        }
        
        if (Game.DEV_MODE)
            soundtrack.pauseNoStart(); // It gets annoying.
        
        if (container.getInput().isKeyPressed(Input.KEY_N))
            soundtrack.playNext();
        if (container.getInput().isKeyPressed(Input.KEY_P))
            soundtrack.pause();
        if (container.getInput().isKeyPressed(Input.KEY_R))
            soundtrack.restart();
        soundtrack.update();
        
        DataPacket.updatePlayer(player);
        DataPacket.update(enemyPlayers);
        
        updateArea();
        updateEnemies(container,delta);
        updateHazards(container,delta, currentArea);
        updatePlayer(container,delta);
        updateViewPort();
    }
    
    private void close() {
        network.close();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (game.getCurrentState().getID() != id)
            return;
        
        translateView(g);
        renderMap(g);
        renderHazards(container,g);
        for (EnemyPlayer e : enemyPlayers) {
            e.render(container,g);
        }
        renderEnemies(container,g);
        renderPlayer(container,g);
        renderObstacles(container, g);
    }
    
    private void setupArea(GameContainer container, Player player) {
        currentArea = new Area(WORLD_SIZE_X,WORLD_SIZE_Y,container,player);
        player.setEnemies(currentArea.getEnemies());
        player.setHazards(currentArea.getHazards());
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
    private void updateHazards(GameContainer container, int delta, Area currentArea) {
        Hazard.updateEnemies(currentArea.getEnemies());
        for (Hazard h : currentArea.getHazards()) {
            
            h.update(container,delta, currentArea);
        }
    }
        
    private void updatePlayer(GameContainer container, int delta) {
        player.setEnemies(currentArea.getEnemies());
        player.setHazards(currentArea.getHazards());
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
                    (int)(posY + height*((double)e.getY())/WORLD_SIZE_Y),3,3);    
        }
         for (Hazard h : currentArea.getHazards()){
             g.setColor(h.getColor());
             g.fillRect((int)(posX + width*((double)h.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)h.getY())/WORLD_SIZE_Y), h.getMiniWidth(),h.getMiniHeight());    
        }
        
        g.setColor(PLAYER_COLOR);
        g.fillRect((int)(posX + width*((double)player.getX())/WORLD_SIZE_X), 
                (int)(posY + height*((double)player.getY())/WORLD_SIZE_Y), 3, 3);
        g.setColor(Color.black);
        g.setColor(new Color(0f,0f,0f,0.5f));
        
        g.setColor(MINIMAP_BLACK);
        
        g.fillRect(posX, posY, width, height);
        
        for (Enemy e : currentArea.getEnemies()){
             g.setColor(e.getColor());
             g.fillRect((int)(posX + width*((double)e.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)e.getY())/WORLD_SIZE_Y), 3, 3);    
        }
        
        for (EnemyPlayer e : enemyPlayers){
             g.setColor(e.getColor());
             g.fillRect((int)(posX + width*((double)e.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)e.getY())/WORLD_SIZE_Y),3,3);    
        }
         for (Obstacle o : currentArea.getObstacles()){
             g.setColor(o.getColor());
             g.fillRect((int)(posX + width*((double)o.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)o.getY())/WORLD_SIZE_Y),o.getMiniWidth(),o.getMiniHeight());    
        }
        
        g.setColor(PLAYER_COLOR);
        g.fillRect((int)(posX + width*((double)player.getX())/WORLD_SIZE_X), 
                (int)(posY + height*((double)player.getY())/WORLD_SIZE_Y), 3, 3);
    }
    
    private void renderEnemies(GameContainer container, Graphics g) throws SlickException {
        for (Enemy e : currentArea.getEnemies()) {
            if (e.getX()>camX-64 && e.getY()>camY-64 && e.getX()<camX+VIEW_SIZE_X && e.getY()<camY+VIEW_SIZE_Y)
                e.render(container, g);
        }
    }
       private void renderHazards(GameContainer container, Graphics g) throws SlickException {
        for (Hazard h : currentArea.getHazards()) {
            if (h.getX()>camX-64 && h.getY()>camY-64 && h.getX()<camX+VIEW_SIZE_X && h.getY()<camY+VIEW_SIZE_Y)
                h.render(container, g);
        }
    }
       private void renderObstacles(GameContainer container, Graphics g) throws SlickException {
        for (Obstacle h : currentArea.getObstacles()) {
            if (h.getX()>camX-64 && h.getY()>camY-64 && h.getX()<camX+VIEW_SIZE_X && h.getY()<camY+VIEW_SIZE_Y)
                h.render(container, g);
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
