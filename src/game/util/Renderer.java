package game.util;

import game.enemy.Enemy;
import game.environment.Obstacle;
import game.map.Area;
import game.player.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Renderer {
    
    private static final Color MINIMAP_BLACK = new Color(0f,0f,0f,0.5f);
    private static final Color PLAYER_COLOR = Color.green;
    
    private final int viewX;
    private final int viewY;
    private final int worldX;
    private final int worldY;
    
    private GameContainer container;
    private Area currentArea;
    private Player player;
    private int camX;
    private int camY;
    
    public Renderer(Area currentArea, Player player, GameContainer container,
            int viewX, int viewY, int worldX, int worldY) {
        this.currentArea = currentArea;
        this.player = player;
        this.container = container;
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
    
    public void renderEnemies(Graphics g) throws SlickException {
        for (Enemy e : currentArea.getEnemies()) {
            if (e.getX()+e.getSprite().getAnim(0).getWidth()*2>camX-64 && 
                    e.getY()+e.getSprite().getAnim(0).getHeight()*2>camY-64 && 
                    e.getX()-e.getSprite().getAnim(0).getWidth()*2<camX+viewX && 
                    e.getY()-e.getSprite().getAnim(0).getWidth()*2<camY+viewY)
                e.render(container, g);
        }
    }
    
    public void renderObstacles(Graphics g) throws SlickException {
        for (Obstacle o : currentArea.getObstacles()) {
            if (o.getX()+o.getSprite().getImage(0).getWidth()*2>camX-64 && 
                  o.getY()+o.getSprite().getImage(0).getHeight()*2>camY-64 && 
                  o.getX()-o.getSprite().getImage(0).getWidth()*2<camX+viewX && 
                  o.getY()-o.getSprite().getImage(0).getHeight()*2<camY+viewY)
                o.render(container,g);
        }
    }
    
    public void renderPlayer(Graphics g) throws SlickException {
        player.render(container,g);
    }
    
    public void renderMinimap(Graphics g) {
        g.setColor(MINIMAP_BLACK);
        
        int posX = (int)(7.5 *viewX)/10 + camX;
        int posY = (int)(.75 *viewY)/10 + camY;
        int width = (int)(2.3 *viewX)/10;
        int height = (int)(((double)worldY / worldX)*(2.3 *viewX)/10);
        
        g.fillRect(posX,posY,width,height);
        
        for (Enemy e : currentArea.getEnemies()){
             g.setColor(e.getColor());
             g.fillRect((int)(posX + width*((double)e.getX())/worldX), 
                    (int)(posY + height*((double)e.getY())/worldY),3,3);    
        }
        
        for (Obstacle o : currentArea.getObstacles()){
             g.setColor(o.getColor());
             g.fillRect((int)(posX + width*((double)o.getX())/worldX), 
                    (int)(posY + height*((double)o.getY())/worldY),o.getMiniWidth(),o.getMiniHeight());    
        }
        
        g.setColor(PLAYER_COLOR);
        g.fillRect((int)(posX + width*((double)player.getX())/worldX), 
                (int)(posY + height*((double)player.getY())/worldY),3,3);
    }
}