package game.hud;

import game.map.Area;
import game.player.Player;
import game.state.StateSingleplayer;
import game.util.resource.FontLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MessageWindow extends PassiveHUD {
    
    private String message;
    private int duration;
    private int timer;
    public static final Color BACKGROUND_COLOR = new Color(185,147,66,128);
    
    private MessageWindow(boolean visible) {
        super(visible);
    }
    
    public MessageWindow(String msg, int millis) {
        this(true);
        message = msg;
        duration = millis;
        timer = duration;
    }
    
    @Override
    public boolean isVisible() {
        return super.isVisible() && timer>=0;
    }
    
    @Override
    public int getDepth() {
        return -1;
    }
    
    public boolean update(int delta) {
        timer -= delta;
        return timer<0;
    }
    
    @Override
    public void display(Graphics g, Player player, Area currentArea, int camX, int camY) {
        int viewX = StateSingleplayer.VIEW_SIZE_X;
        int worldX = StateSingleplayer.WORLD_SIZE_X;
        int worldY = StateSingleplayer.WORLD_SIZE_Y;
        
        int posX = camX+5;
        int width = (int)(2.3*viewX)/6;
        int height = (int)(((double)worldY/worldX)*(2.3*viewX)/8);
        
        int posY = camY + 5;
        
        if (player.isQuestDisplayOpen())
            posY = camY + (int)(((double)worldY/worldX)*(2.3*viewX)/4)+10;
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(posX,posY,width,height);
        g.setFont(FontLibrary.PIXEL_FONT_SMALL.getFont());
        g.setColor(FONT_WHITE);
        g.drawString(message,posX,posY);
    }
    
    public void resetTimer() {
        timer = duration;
    }
}
