package game.map;

import game.util.SimplexNoise;

public class TiledMap {
    
    private Tile[][] map;
    private double[][] priority;
    private int width;
    private int height;
    
    public Tile getTile(int x, int y) {
        if (x<0 || y<0 || x>map.length || y>map[x].length)
            return null;
        return map[x][y];
    }
    
    public TiledMap(int width, int height) {
        this.width = width;
        this.height = height;
        
        map = new Tile[width][height];
    }
    
    public void init() {
        fillStandardGrass();
        
        generateRoad();
    }
        
    private void fillStandardGrass() {
        fill(Tile.GRASS_BASIC);
        randomize(Tile.GRASS_VARIANT,0.2);
        randomize(Tile.GRASS_SHIFT,0.1);
        randomize(Tile.GRASS_BOLD,0.05);
        randomize(Tile.GRASS_FLOWER,0.01);
    }
        
    private void fill(Tile tile) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                map[i][j] = tile;
    }
    
    private void randomize(Tile tile, double chance) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                if (Math.random()<chance)
                    map[i][j] = tile;
    }
    
    private void generateRoad() {
        priority = new double[map.length][map[0].length];
        
    }
}