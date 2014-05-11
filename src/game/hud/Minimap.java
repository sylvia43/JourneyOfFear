package game.hud;

import game.map.Area;
import game.player.Player;
import game.state.StateSingleplayer;
import game.util.GameObject;
import game.util.Options;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Minimap extends PassiveHUD {
    
    public Minimap(boolean visible) {
        super(visible);
    }
    
    @Override
    public int getDepth() {
        return -1; // Behind other windows.
    }
    
    @Override
    public void respondToUserInput(Input in) {
        if (in.isKeyPressed(Options.OPEN_MINIMAP.key()))
            visible = !visible;
    }
    
    @Override
    public void display(Graphics g, Player player, Area currentArea, int camX, int camY) {
        int viewX = StateSingleplayer.VIEW_SIZE_X;
        int viewY = StateSingleplayer.VIEW_SIZE_Y;
        int worldX = StateSingleplayer.WORLD_SIZE_X;
        int worldY = StateSingleplayer.WORLD_SIZE_Y;
        
        g.setColor(BACKGROUND_BLACK);
        
        int posX = (int)(7.5*viewX)/10+camX;
        int posY = (int)(.75*viewY)/10+camY;
        int width = (int)(2.3*viewX)/10;
        int height = (int)(((double)worldY/worldX)*(2.3*viewX)/10);
        
        g.fillRect(posX,posY,width,height);
        
        for (GameObject o : currentArea.getObjects()) {
            g.setColor(o.getColor());
            g.fillRect(1+(int)(posX+width*((double)o.getX())/worldX), 
                    1+(int)(posY+height*((double)o.getY())/worldY),
                    o.getMiniWidth(),o.getMiniHeight());
        }
    }
}
