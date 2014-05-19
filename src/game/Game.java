package game;

import game.state.StateMenu;
import game.state.StateMultiplayer;
import game.state.StateServerSelect;
import game.state.StateSingleplayer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
    
    public static final int STATE_MENU = 0;
    public static final int STATE_SINGLEPLAYER = 1;
    public static final int STATE_MULTIPLAYER = 2;
    public static final int STATE_SERVER_SELECT = 3;
    public static final int STATE_OPTIONS = 4;
    
    public static final int VIEW_SIZE_X = 640;
    public static final int VIEW_SIZE_Y = 512;
    
    public Game() {
        super("Journey of Fear");
    }
    
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Game());
            setupAGC(app);
        } catch (SlickException e) {
            System.out.println("Error initializing game: " + e);
        }
    }
    
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        this.addState(new StateMenu(STATE_MENU));
        this.addState(new StateSingleplayer(STATE_SINGLEPLAYER));
        this.addState(new StateMultiplayer(STATE_MULTIPLAYER));
        this.addState(new StateServerSelect(STATE_SERVER_SELECT));
    }
    
    /** Sets up the window. */
    private static void setupAGC(AppGameContainer app) throws SlickException {
        app.setDisplayMode(VIEW_SIZE_X,VIEW_SIZE_Y,false);
        app.setShowFPS(StateSingleplayer.DEBUG_MODE);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);
        app.setVSync(true);
        app.setForceExit(true);
        app.setVerbose(false);
        app.start();
    }
}