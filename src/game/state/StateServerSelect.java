package game.state;

import game.Game;
import java.awt.Font;
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

public class StateServerSelect extends BasicGameState implements ComponentListener {
    
    private Image buttonStart;
    
    private MouseOverArea startButton;
    
    private TextField fieldIp;
    private TextField fieldPort;
    
    private Image background;
    private UnicodeFont font;
    
    private String ip;
    private String port;
    
    private StateBasedGame game;

    private int id;

    public StateServerSelect(int id) {
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
        
        fieldIp = new TextField(container,font,60,20,500,35,new ComponentListener() {
            @Override
            public void componentActivated(AbstractComponent source) {
                ip = fieldIp.getText();
                fieldPort.setFocus(true);
            }
        });
        fieldIp.setText("127.0.0.1");

        fieldPort = new TextField(container,font,60,70,500,35,new ComponentListener() {
            @Override
            public void componentActivated(AbstractComponent source) {
                port = fieldPort.getText();
            }
        });
        fieldPort.setText("9999");
        
        try {
            buttonStart = new Image("resources/art/menu/Multiplayer.png");
            background = new Image("resources/art/menu/menuBG.png");
        } catch (SlickException e) {
            System.out.println("Failed to load menu resources: " + e);
        }

        startButton = new MouseOverArea(container,buttonStart,356,294,200,90,this);
        startButton.setNormalColor(new Color(1,1,1,1f));
        startButton.setMouseOverColor(new Color(1,1,1,0.3f));
    }
    
    @Override
    public void render(GameContainer container,StateBasedGame game,Graphics g) throws SlickException {
        if (game.getCurrentState().getID() != id)
            return;
        
        background.draw(0,0,640,512);

        startButton.render(container,g);

        fieldIp.render(container,g);
        fieldPort.render(container,g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (game.getCurrentState().getID() != id) {
        }
    }
    
    @Override
    public void keyPressed(int key, char c) {
        if (key == Input.KEY_ENTER)
            start();
    }
    
    @Override
    public void componentActivated(AbstractComponent source) {
        if (source==startButton) {
            start();
        }
    }
    
    public void start() {
        ip = fieldIp.getText();
        port = fieldPort.getText();
        System.out.println(ip + ":" + port);
        StateMultiplayer.ip = ip;
        StateMultiplayer.port = Integer.valueOf(port);
        game.enterState(Game.STATE_SINGLEPLAYER);
    }

    @Override
    public int getID() {
        return id;
    }
}
