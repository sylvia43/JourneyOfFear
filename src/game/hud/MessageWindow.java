package game.hud;

import game.environment.obstacle.TechSign;
import game.map.Area;
import game.player.Player;
import game.state.StateSingleplayer;
import game.util.resource.FontLibrary;
import org.newdawn.slick.Graphics;

public class MessageWindow extends PassiveHUD {
    
    private String message;
    private int duration;
    private int timer;
    
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
    public int getDepth() {
        return -1;
    }
    
    public boolean update(int delta) {
        System.out.println(timer);
        timer -= delta;
        return timer<0;
    }
    
    @Override
    public void display(Graphics g, Player player, Area currentArea, int camX, int camY) {
        int viewX = StateSingleplayer.VIEW_SIZE_X;
        int viewY = StateSingleplayer.VIEW_SIZE_Y;
        int worldX = StateSingleplayer.WORLD_SIZE_X;
        int worldY = StateSingleplayer.WORLD_SIZE_Y;
        
        int posX = 10 + camX;
        int posY = viewY/2 + camY;
        int width = (int)(2.3*viewX)/10;
        int height = (int)(((double)worldY/worldX)*(2.3*viewX)/10);
        
        g.setColor(TechSign.SIGN_COLOR);
        g.fillRect(posX,posY,width,height);
        g.setFont(FontLibrary.PIXEL_FONT.getFont());
        g.setColor(FONT_WHITE);
        g.drawString(message,posX,posY);
    }
    
    public void resetTimer() {
        timer = duration;
    }
}
