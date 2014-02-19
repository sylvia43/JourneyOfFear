package game.state;

import game.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateServer extends BasicGameState {
    
    private int id;
    
    public StateServer(int id) {
        this.id = id;
    }
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
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
