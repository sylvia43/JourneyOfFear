package game.state;

import game.util.server.NetworkHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateMultiplayer extends StateSingleplayer {
    
    public static String ip = null;
    public static int port = 0;
    
    private NetworkHandler network;
    
    public StateMultiplayer(int id) {
        super(id);
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        super.enter(container,game);
        network = new NetworkHandler(ip,port);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container,game,delta);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container,game,g);
    }
}
