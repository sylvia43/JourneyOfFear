package game.util;

import game.map.Area;
import game.player.Player;
import org.newdawn.slick.Graphics;

/** Much render. Such wow. */
public class Renderer {
        
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
                 currentArea.getTile(x,y).getImage(0).draw(x*64,y*64,64,64);
            }
        }
    }
    
    public void renderObjects(Graphics g) {
        currentArea.sortObjects();
        for (GameObject o : currentArea.getObjects()) {
            o.render(g);
        }
    }
}