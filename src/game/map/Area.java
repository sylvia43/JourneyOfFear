package game.map;

import game.enemy.Enemy;
import game.enemy.blob.EnemyGreenBlob;
import game.enemy.blob.EnemyRedBlob;
import game.enemy.humanoid.EnemyMutant;
import game.environment.hazard.Spikes;
import game.environment.obstacle.Obstacle;
import game.environment.obstacle.Tree;
import game.environment.spawner.GreenSlimeSpawner;
import game.environment.spawner.PinkSlimeSpawner;
import game.npc.NPC;
import game.npc.QuestNPC;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;

/**
 * Area functions as a linked list in two dimensions,
 * or a "linked grid."
 */
public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<NPC> npcs;
    
    private Area[] adjacent;
    
    private final int width;
    private final int height;
    
    private GameContainer container;
    private final Player player;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    public TiledMap getMap() { return map; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public List<NPC> getNPCS() { return npcs; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    // Move adding of creatues out of here; need a factory.
    public Area(int width, int height, GameContainer container, Player player) {
        this.player = player;
        this.container = container;
        this.width = width;
        this.height = height;
        
        adjacent = new Area[4];
        enemies = new ArrayList<Enemy>();
        obstacles = new ArrayList<Obstacle>();
        npcs = new ArrayList<NPC>();
        
        map = new TiledMap(width/64, height/64, this);
        map.init();
        
        addEnemy(new EnemyRedBlob(player)).init();
        addEnemy(new EnemyGreenBlob(player)).init();
        
        addEnemy(new EnemyMutant(player)).init();
        
        addNPC(new QuestNPC()).init();
        
        addObstacle(new Spikes(player,enemies));           
        addObstacle(new GreenSlimeSpawner(player,enemies));
        addObstacle(new PinkSlimeSpawner(player,enemies));
        addObstacle(new Tree());           
    }
    
    public NPC addNPC(NPC n) {
        npcs.add(n);
        return n;
    }
    
    public Enemy addEnemy(Enemy e) {
        enemies.add(e);
        return e;
    }
    
    public Obstacle addObstacle(Obstacle o) {
        obstacles.add(o);
        return o;
    }
    
    public void setAdjacent(Area area, int index) {
        adjacent[index] = area;
    }
    
    public Area getAdjacent(int index) {
        Area adjacentArea = adjacent[index];
        if (adjacentArea != null)
            return adjacentArea;
        adjacentArea = new Area(width,height,container,player);
        adjacentArea.setAdjacent(this,(index+2)%4);
        adjacent[index] = adjacentArea;
        return adjacentArea;
    }
    
    public Area[] getAllAdjacent() { return adjacent; }
}