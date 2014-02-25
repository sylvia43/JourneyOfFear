package game.state;

import game.Game;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateMenu extends BasicGameState implements ComponentListener {

    private Image image;
    private MouseOverArea[] areas = new MouseOverArea[4];
    private String message = "Use first button or enter key to start.";
    private TextField field1;
    private TextField field2;
    private Image background;
    private UnicodeFont font;
    
    public static final int AREA_SINGLEPLAYER = 0;
    public static final int AREA_MULTIPLAYER = 1;
    public static final int AREA_SERVER = 2;
    
    private StateBasedGame game;

    private int id;

    public StateMenu(int id) {
        this.id = id;
    }

    public void init(GameContainer container,StateBasedGame game) throws SlickException {
        
    }
    
    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        this.game = game;

        Font awtFont = new Font("Times New Roman",Font.BOLD,24);
        font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(java.awt.Color.white));
        font.addAsciiGlyphs();

        try {
            font.loadGlyphs();
        } catch (SlickException e) {
            System.out.println("Error loading font: " + e);
        }

        field1 = new TextField(container,font,60,20,500,35,new ComponentListener() {
            @Override
            public void componentActivated(AbstractComponent source) {
                message = "Field 1: " + field1.getText();
                field2.setFocus(true);
            }
        });

        field2 = new TextField(container,font,60,70,500,35,new ComponentListener() {
            @Override
            public void componentActivated(AbstractComponent source) {
                message = "Field 2: " + field2.getText();
                field1.setFocus(true);
            }
        });
        
        field2.setBorderColor(Color.red);
        try {
            image = new Image("resources/art/menu/button.png");
            background = new Image("resources/art/menu/background.png");
        } catch (SlickException e) {
            System.out.println("Failed to load menu resources: " + e);
        }

        for (int i=0;i<4;i++) {
            areas[i] = new MouseOverArea(container,image,300,100+(i*100),200,90,this);
            areas[i].setNormalColor(new Color(1,1,1,1f));
            areas[i].setMouseOverColor(new Color(1,1,1,0.3f));
        }
    }
    
    @Override
    public void render(GameContainer container,StateBasedGame game,Graphics g) throws SlickException {
        if (game.getCurrentState().getID() != id)
            return;
        
        background.draw(0,0,640,512);

        for (int i=0;i<4;i++)
            areas[i].render(container,g);

        field1.render(container,g);
        field2.render(container,g);

        g.setFont(font);
        g.drawString(message,50,400);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (game.getCurrentState().getID() != id)
            return;
    }
    
    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER)
            game.enterState(1);
    }
    
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source==areas[AREA_SINGLEPLAYER]) {
            message = "Entering singleplayer.";
            game.enterState(Game.STATE_SINGLEPLAYER);
        } else if (source==areas[AREA_MULTIPLAYER]) {
            message = "Entering multiplayer.";
            game.enterState(Game.STATE_MULTIPLAYER);
        } else if (source==areas[AREA_SERVER]) {
            message = "Hosting server.";
            game.enterState(Game.STATE_SERVER);
        } else if (source==areas[3]) {
            message = "Option 4 " + " clicked.";
        } 
    }

    @Override
    public int getID() {
        return id;
    }
}
