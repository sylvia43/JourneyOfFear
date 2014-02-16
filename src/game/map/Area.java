package game.map;

import game.enemy.Enemy;
import game.enemy.EnemyBlob;
import game.enemy.EnemySmartBlob;
import game.environment.GreenSlimePit;
import game.environment.Hazard;
import game.environment.Spikes;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Area functions as a linked list in two dimensions,
 * or a "linked grid."
 */
public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Hazard> hazards = new ArrayList<Hazard>();
    
    private Area left;
    private Area right;
    private Area up;
    private Area down;
    
    private final int width;
    private final int height;
    
    private GameContainer container;
    private final Player player;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Hazard> getHazards() { return hazards; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
        
    public Area(int width, int height, GameContainer container, Player player) {
        this.player = player;
        this.container = container;
        this.width = width;
        this.height = height;
        this.map = new TiledMap(width/64, height/64);
        this.map.fillStandardGrass();
        
        try {
            addEnemy(new EnemyBlob(player)).init(container);
            addEnemy(new EnemySmartBlob(player)).init(container);
        } catch (SlickException e) {
            System.out.println("Error initializing enemy: " + e);
        }
        
        try {
            addHazard(new Spikes(player)).init(container);
            addHazard(new GreenSlimePit(player)).init(container);
        } catch (SlickException e) { 
            System.out.println("Error initializing hazard: " + e);
        }
    }
    
    public Enemy addEnemy(Enemy e) {
        enemies.add(e);
        return e;
    }
     public Enemy addEnemy(Enemy e, int x, int y) {
         e.setX(x);
         e.setY(y);
        enemies.add(e);
        return e;
    }
    public Hazard addHazard(Hazard h){
        hazards.add(h);
        return h;
    }
    
    
    public void setLeft(Area area) {
        left = area;
    }
    
    public Area getLeft() {
        if (left!=null)
            return left;
        left = new Area(width,height,container,player);
        left.setRight(this);
        return left;
    }

    public void setRight(Area area) { right = area; }
    
    public Area getRight() {
        if (right!=null)
            return right;
        right = new Area(width,height,container,player);
        right.setLeft(this);
        return right;
    }
    
    public void setUp(Area area) {
        up = area;
    }
    
    public Area getUp() {
        if (up!=null)
            return up;
        up = new Area(width,height,container,player);
        up.setDown(this);
        return up;
    }

    public void setDown(Area area) {
        down = area;
    }
    
    public Area getDown() {
        if (down!=null)
            return down;
        down = new Area(width,height,container,player);
        down.setUp(this);
        return down;
    }

    
}