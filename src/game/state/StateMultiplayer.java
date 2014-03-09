package game.state;

import game.util.client.NetworkHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateMultiplayer extends StateSingleplayer {
    
    public static String ip = "127.0.0.1";
    public static int port = 9999;
    
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
        network = new NetworkHandler(ip,port,player);
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
