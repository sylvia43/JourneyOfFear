package game;

import java.util.ArrayList;

public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    private Area left;
    private Area right;
    private Area up;
    private Area down;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public void addEnemy(Enemy e) { enemies.add(e); }
    
    public Area(int WORLD_SIZE_X, int WORLD_SIZE_Y) {
       this.map = new TiledMap((int)(WORLD_SIZE_X/64),(int)(WORLD_SIZE_Y/64));
    }
    
    public Area(TiledMap map) {
        this.map = map;
    }
}