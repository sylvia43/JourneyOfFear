package game.state;

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

public class StateMenu extends BasicGameState implements ComponentListener {

    private Image image;
    private MouseOverArea[] areas = new MouseOverArea[4];
    private String message = "Information here.";
    private TextField field1;
    private TextField field2;
    private Image background;
    private UnicodeFont font;
    
    private StateBasedGame game;

    private int id;

    public StateMenu(int id) {
        this.id = id;
    }

    public void init(GameContainer container,StateBasedGame game) throws SlickException {
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
                
        image = new Image("resources/art/menu/button.png");
        background = new Image("resources/art/menu/background.png");
        
        for (int i=0;i<4;i++) {
            areas[i] = new MouseOverArea(container,image,300,100+(i*100),200,90,this);
            areas[i].setNormalColor(new Color(1,1,1,1f));
            areas[i].setMouseOverColor(new Color(1,1,1,0.3f));
        }
    }

    @Override
    public void render(GameContainer container,StateBasedGame game,Graphics g) throws SlickException {
        background.draw(0,0,640,512);

        for (int i = 0; i<4; i++)
            areas[i].render(container,g);

        field1.render(container,g);
        field2.render(container,g);

        g.setFont(font);
        g.drawString(message,50,400);
    }

    @Override
    public void update(GameContainer container,StateBasedGame game,int delta) throws SlickException {
    }
    
    @Override
    public void keyPressed(int key,char c) {
        if (key == Input.KEY_ENTER)
            game.enterState(1);
    }
    
    @Override
    public void componentActivated(AbstractComponent source) {
        for (int i = 0; i<4; i++)
            if (source==areas[i])
                message = "Option "+(i+1)+" clicked.";
    }

    @Override
    public int getID() {
        return id;
    }
}
