package game.state;

import game.enemy.Enemy;
import game.environment.obstacle.Obstacle;
import game.map.Area;
import game.npc.NPC;
import game.player.Player;
import game.util.MathHelper;
import game.util.Renderer;
import game.util.Soundtrack;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateSingleplayer extends BasicGameState {
    
    public static final boolean DEBUG_MODE = false;
    public static final boolean DEBUG_COLLISION = false;
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
        renderer = new Renderer(currentArea,player,VIEW_SIZE_X,VIEW_SIZE_Y,WORLD_SIZE_X,WORLD_SIZE_Y);
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
        
        currentArea = currentArea.update();
        renderer.updateArea(currentArea);
        
        for (Enemy e : currentArea.getEnemies())
            e.update(delta); 
        
        for (Obstacle o : currentArea.getObstacles())
            o.update(delta,currentArea);
        
        for (NPC n : currentArea.getNPCS()) {
            n.update(delta);
        }
        
        player.setArea(currentArea);
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
        
        player.renderHUD(g);
    }
    
    private void setupArea(GameContainer container, Player player) {
        currentArea = new Area(WORLD_SIZE_X,WORLD_SIZE_Y,container,player);
        player.setArea(currentArea);
    }
    
    private void initPlayer(GameContainer container) throws SlickException {
        player = new Player();
        player.init(container);
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
