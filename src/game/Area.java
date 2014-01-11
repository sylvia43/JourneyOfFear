package game;

import game.enemy.Enemy;
import java.util.ArrayList;

public class Area {
    
    private TiledMap map;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    private Area left;
    private Area right;
    private Area up;
    private Area down;
    
    private final int width;
    private final int height;
    
    public Tile getTile(int x, int y) { return map.getTile(x,y); }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
        
    public Area(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new TiledMap((int)(width/64),(int)(height/64));
        this.map.fillStandardGrass();
    }
    
    public void addEnemy(Enemy e) {
        enemies.add(e);
    }
    
    public void setLeft(Area area) {
        left = area;
    }
    
    public Area getLeft() {
        if (left!=null)
            return left;
        left = new Area(width,height);
        left.setRight(this);
        return left;
    }

    public void setRight(Area area) { right = area; }
    
    public Area getRight() {
        if (right!=null)
            return right;
        right = new Area(width,height);
        right.setLeft(this);
        return right;
    }
    
    public void setUp(Area area) {
        up = area;
    }
    
    public Area getUp() {
        if (up!=null)
            return up;
        up = new Area(width,height);
        up.setDown(this);
        return up;
    }

    public void setDown(Area area) {
        down = area;
    }
    
    public Area getDown() {
        if (down!=null)
            return down;
        down = new Area(width,height);
        down.setUp(this);
        return down;
    }
}