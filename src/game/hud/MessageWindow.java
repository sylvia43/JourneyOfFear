package game.hud;

import game.map.Area;
import game.player.Player;
import game.state.StateSingleplayer;
import org.newdawn.slick.Graphics;

public class MessageWindow extends PassiveHUD {
    
    private String message;
    
    private MessageWindow(boolean visible) {
        super(visible);
    }
    
    public MessageWindow(String msg) {
        this(true);
        message = msg;
    }
    
    @Override
    public int getDepth() {
        return -1; // Behind other windows.
    }
    
    @Override
    public void display(Graphics g, Player player, Area currentArea, int camX, int camY) {
        int viewX = StateSingleplayer.VIEW_SIZE_X;
        int viewY = StateSingleplayer.VIEW_SIZE_Y;
        int worldX = StateSingleplayer.WORLD_SIZE_X;
        int worldY = StateSingleplayer.WORLD_SIZE_Y;
        
        int posX = (int)(7.5*viewX)/10+camX;
        int posY = (int)(.75*viewY)/10+camY;
        int width = (int)(2.3*viewX)/10;
        int height = (int)(((double)worldY/worldX)*(2.3*viewX)/10);
        
        //g.fillRect(posX,posY,width,height);
        g.setColor(FONT_WHITE);
        g.drawString(message,posX,posY);
    }
}
