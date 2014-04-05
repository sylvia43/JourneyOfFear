package game.state;

import game.enemy.EnemyPlayer;
import game.network.client.NetworkHandler;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateMultiplayer extends StateSingleplayer {
    
    /* IP of server */
    public static String ip = "127.0.0.1";

    /* Port on server */
    public static int port = 9999;
    
    private NetworkHandler network;
    
    private ArrayList<EnemyPlayer> enemies;
    
    public StateMultiplayer(int id) {
        super(id);
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        super.enter(container,game);
        enemies = new ArrayList<EnemyPlayer>();
        network = new NetworkHandler(ip,port,player,enemies);
        network.start();
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        super.render(container,game,g);
        for (EnemyPlayer e : enemies)
            e.render(container,g);
        
        int posX = (int)(7.5 *VIEW_SIZE_X)/10 + camX;
        int posY = (int)(.75 *VIEW_SIZE_Y)/10 + camY;
        int width = (int)(2.3 *VIEW_SIZE_X)/10;
        int height = (int)(((double)WORLD_SIZE_Y / WORLD_SIZE_X)*(2.3 *VIEW_SIZE_X)/10);
                
        for (EnemyPlayer e : enemies) {
             g.setColor(Color.magenta);
             g.fillRect((int)(posX + width*((double)e.x)/WORLD_SIZE_X), 
                    (int)(posY + height*((double)e.y)/WORLD_SIZE_Y),3,3);    
        }
    }
}
