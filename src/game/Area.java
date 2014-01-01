package game;

import java.util.ArrayList;

public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies;
    
    private Area left;
    private Area right;
    private Area up;
    private Area down;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    
    public Area(TiledMap map) {
        this.map = map;
    }
}