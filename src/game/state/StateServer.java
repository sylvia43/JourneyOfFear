package game.state;

import game.Game;
import game.enemy.EnemyPlayer;
import game.util.server.DataPacket;
import game.util.server.Server;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateServer extends BasicGameState {
    
    public static final int MAX_CONNECTIONS = 50;
    
    private int id;
    
    private Server server;
    private final int port = 9999;
    
    ArrayList<EnemyPlayer> players = new ArrayList<EnemyPlayer>();
    
    public StateServer(int id) {
        this.id = id;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        server = new Server(port);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        server.start();
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        DataPacket.update(players);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(0,0,Game.VIEW_SIZE_X,Game.VIEW_SIZE_Y);
    }
    
    @Override
    public int getID() {
        return id;
    }
}
