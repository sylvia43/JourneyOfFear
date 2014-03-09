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
    
    private final Color MINIMAP_BLACK = new Color(0f,0f,0f,0.5f);
    private final Color PLAYER_COLOR = Color.green;
    
    public static final int VIEW_SIZE_X = 640;
    public static final int VIEW_SIZE_Y = 512;
    public static final int WORLD_SIZE_X = VIEW_SIZE_X*4;
    public static final int WORLD_SIZE_Y = VIEW_SIZE_Y*4;
    
    private GameContainer container;
    private Area currentArea;
    private Player player;
    private int camX;
    private int camY;
    
    public Renderer(Area currentArea, Player player, GameContainer container) {
        this.currentArea = currentArea;
        this.player = player;
        this.container = container;
    }
    
    public void updateCamera(int camX, int camY) {
        this.camX = camX;
        this.camY = camY;
    }
    
    public void updateArea(Area newArea) {
        this.currentArea = newArea;
    }
    
    public void renderMap(Graphics g) {
        for(int x=camX/64;x<Math.min(WORLD_SIZE_X/64,(camX+VIEW_SIZE_X)/64+1);x++) {
            for(int y=camY/64;y<Math.min(WORLD_SIZE_Y/64,(camY+VIEW_SIZE_Y)/64+1);y++) {
                 currentArea.getTile(x,y).image().draw(x*64,y*64,64,64);
            }
        }
    }
    
    public void renderEnemies(Graphics g) throws SlickException {
        for (Enemy e : currentArea.getEnemies()) {
            if (e.getX()+e.getSprite().getAnim(0).getWidth()*2>camX-64 && 
                    e.getY()+e.getSprite().getAnim(0).getHeight()*2>camY-64 && 
                    e.getX()-e.getSprite().getAnim(0).getWidth()*2<camX+VIEW_SIZE_X && 
                    e.getY()-e.getSprite().getAnim(0).getWidth()*2<camY+VIEW_SIZE_Y)
                e.render(container, g);
        }
    }
    
    public void renderObstacles(Graphics g) throws SlickException {
        for (Obstacle o : currentArea.getObstacles()) {
            if (o.getX()+o.getSprite().getImage(0).getWidth()*2>camX-64 && 
                  o.getY()+o.getSprite().getImage(0).getHeight()*2>camY-64 && 
                  o.getX()-o.getSprite().getImage(0).getWidth()*2<camX+VIEW_SIZE_X && 
                  o.getY()-o.getSprite().getImage(0).getHeight()*2<camY+VIEW_SIZE_Y)
                o.render(container,g);
        }
    }
    
    public void renderPlayer(Graphics g) throws SlickException {
        player.render(container,g);
    }
    
    public void renderMinimap(Graphics g) {
        g.setColor(MINIMAP_BLACK);
        
        int posX = (int)(7.5 *VIEW_SIZE_X)/10 + camX;
        int posY = (int)(.75 *VIEW_SIZE_Y)/10 + camY;
        int width = (int)(2.3 *VIEW_SIZE_X)/10;
        int height = (int)(((double)WORLD_SIZE_Y / WORLD_SIZE_X)*(2.3 *VIEW_SIZE_X)/10);
        
        g.fillRect(posX,posY,width,height);
        
        for (Enemy e : currentArea.getEnemies()){
             g.setColor(e.getColor());
             g.fillRect((int)(posX + width*((double)e.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)e.getY())/WORLD_SIZE_Y),3,3);    
        }
        
        for (Obstacle o : currentArea.getObstacles()){
             g.setColor(o.getColor());
             g.fillRect((int)(posX + width*((double)o.getX())/WORLD_SIZE_X), 
                    (int)(posY + height*((double)o.getY())/WORLD_SIZE_Y),o.getMiniWidth(),o.getMiniHeight());    
        }
        
        g.setColor(PLAYER_COLOR);
        g.fillRect((int)(posX + width*((double)player.getX())/WORLD_SIZE_X), 
                (int)(posY + height*((double)player.getY())/WORLD_SIZE_Y),3,3);
    }
}