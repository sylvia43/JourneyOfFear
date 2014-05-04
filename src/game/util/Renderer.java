package game.util;

import game.map.Area;
import game.player.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Renderer {
    
    private static final Color MINIMAP_BLACK = new Color(0f,0f,0f,0.5f);
    
    private final int viewX;
    private final int viewY;
    private final int worldX;
    private final int worldY;
    
    private Area currentArea;
    private Player player;
    private int camX;
    private int camY;
    
    public Renderer(Area currentArea, Player player, int viewX, int viewY, int worldX, int worldY) {
        this.currentArea = currentArea;
        this.player = player;
        this.viewX = viewX;
        this.viewY = viewY;
        this.worldX = worldX;
        this.worldY = worldY;
    }
    
    public void updateCamera(int camX, int camY) {
        this.camX = camX;
        this.camY = camY;
    }
    
    public void updateArea(Area newArea) {
        this.currentArea = newArea;
    }
    
    public void renderMap(Graphics g) {
        for(int x=camX/64;x<Math.min(worldX/64,(camX+viewX)/64+1);x++) {
            for(int y=camY/64;y<Math.min(worldY/64,(camY+viewY)/64+1);y++) {
                 currentArea.getTile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
    
    public void renderObjects(Graphics g) {
        currentArea.sortObjects();
        for (GameObject o : currentArea.getObjects()) {
            o.render(g);
        }
    }
    
    public void renderMinimap(Graphics g) {
        g.setColor(MINIMAP_BLACK);
        
        int posX = (int)(7.5 *viewX)/10 + camX;
        int posY = (int)(.75 *viewY)/10 + camY;
        int width = (int)(2.3 *viewX)/10;
        int height = (int)(((double)worldY / worldX)*(2.3 *viewX)/10);
        
        g.fillRect(posX,posY,width,height);
        
        for (GameObject o : currentArea.getObjects()) {
            g.setColor(o.getColor());
            g.fillRect(1+(int)(posX+width*((double)o.getX())/worldX), 
                    1+(int)(posY+height*((double)o.getY())/worldY),
                    o.getMiniWidth(),o.getMiniHeight());
        }
    }
}