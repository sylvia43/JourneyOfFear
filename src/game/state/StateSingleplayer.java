package game.state;

import game.enemy.Enemy;
import game.environment.obstacle.Obstacle;
import game.map.Area;
import game.npc.NPC;
import game.player.Player;
import game.util.MathHelper;
import game.util.Renderer;
import game.util.Soundtrack;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateSingleplayer extends BasicGameState {
    
    public static final boolean DEBUG_MODE = true;
    public static final boolean DEBUG_COLLISION = true;
    public static final int VIEW_SIZE_X = 640;
    public static final int VIEW_SIZE_Y = 512;
    public static final int WORLD_SIZE_X = VIEW_SIZE_X*4;
    public static final int WORLD_SIZE_Y = VIEW_SIZE_Y*4;
    
    protected int camX;
    protected int camY;
    protected Area currentArea;
    protected Player player;
    protected int id;
    protected Soundtrack soundtrack;
    
    protected Renderer renderer;
    
    public StateSingleplayer(int id) {
        this.id = id;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException { }
    
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
        renderer = new Renderer(currentArea,player,container,
                VIEW_SIZE_X,VIEW_SIZE_Y,WORLD_SIZE_X,WORLD_SIZE_Y);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_ESCAPE))
            container.exit();
        
        if (container.getInput().isKeyPressed(Input.KEY_N))
            soundtrack.playNext();
        if (container.getInput().isKeyPressed(Input.KEY_P))
            soundtrack.pause();
        if (container.getInput().isKeyPressed(Input.KEY_R))
            soundtrack.restart();
        soundtrack.update();
        
        updateArea();
        
        for (Enemy e : currentArea.getEnemies())
            e.update(delta); 
        
        for (Obstacle o : currentArea.getObstacles())
            o.update(delta,currentArea);
        
        for (NPC n : currentArea.getNPCS()) {
            n.update(delta);
        }
        
        player.setEnemies(currentArea.getEnemies());
        player.setObstacles(currentArea.getObstacles());
        player.update(container,delta);
        
        updateViewPort();
        renderer.updateCamera(camX,camY);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (game.getCurrentState().getID() != id)
            return;
        
        translateView(g);
        renderer.renderMap(g);
        renderer.renderObjects(g);
        renderer.renderMinimap(g);
        
        for (NPC n : currentArea.getNPCS()) {
            n.render(g);
        }
    }
    
    private void setupArea(GameContainer container, Player player) {
        currentArea = new Area(WORLD_SIZE_X,WORLD_SIZE_Y,container,player);
        player.setEnemies(currentArea.getEnemies());
        player.setObstacles(currentArea.getObstacles());
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
                currentArea.getAdjacent(2).getEnemies().add(e);
                e.setX(currentArea.getWidth()-160);
            }
            if (e.getY()<-16) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getAdjacent(1).getEnemies().add(e);
                e.setY(currentArea.getHeight()-160);
            }
            if (e.getX()>currentArea.getWidth()-48) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getAdjacent(0).getEnemies().add(e);
                e.setX(96);
            }
            if (e.getY()>currentArea.getHeight()-48) {
                newCurrentAreaEnemyList.remove(e);
                currentArea.getAdjacent(3).getEnemies().add(e);
                e.setY(96);
            }
        }
        currentArea.getEnemies().clear();
        currentArea.getEnemies().addAll(newCurrentAreaEnemyList);
        
        if (player.getX()<-16) {
            currentArea = currentArea.getAdjacent(2);
            renderer.updateArea(currentArea);
            player.setX(currentArea.getWidth()-48);
        }
        if (player.getY()<-16) {
            currentArea = currentArea.getAdjacent(1);
            renderer.updateArea(currentArea);
            player.setY(currentArea.getHeight()-48);
        }
        if (player.getX()>currentArea.getWidth()-48) {
            currentArea = currentArea.getAdjacent(0);
            renderer.updateArea(currentArea);
            player.setX(-16);
        }
        if (player.getY()>currentArea.getHeight()-48) {
            currentArea = currentArea.getAdjacent(3);
            renderer.updateArea(currentArea);
            player.setY(-16);
        }
    }
    
    private void updateViewPort() {
        camX = (int)MathHelper.median(0,WORLD_SIZE_X-VIEW_SIZE_X,player.getX()-VIEW_SIZE_X/2);
        camY = (int)MathHelper.median(0,WORLD_SIZE_Y-VIEW_SIZE_Y,player.getY()-VIEW_SIZE_Y/2);
        player.updateViewPort(camX,camY);
    }
    
    private void translateView(Graphics g) {
        g.translate(-(float)camX,-(float)camY);
    }
    
    @Override
    public int getID() {
        return id;
    }
}
