package game.map;

import game.map.gen.MapGenerator;
import game.map.gen.RoadGenerator;
import java.util.Random;

public class TiledMap {
    
    private Area wrapper;
    private Tile[][] map;
    private int width;
    private int height;
    private int rx; //Road Start (X)
    private int ry; //Road Start (Y)
    private int rxe; //Road End (X)
    private int rye; //Catcher in the (Y)
    private Random rand;
    
    public Tile getTile(int x, int y) {
        if (x<0 || y<0 || x>map.length || y>map[x].length)
            return null;
        return map[x][y];
    }
    
    public int[][] getRoadCoords() {
        return new int[][] {
            { rx, ry },
            { rxe, rye },
        };
    }
    
    public TiledMap(int width, int height, Area wrapper) {
        this.width = width;
        this.height = height;
        this.wrapper = wrapper;
        
        map = new Tile[width][height];
        rand = new Random();
    }
    
    public TiledMap(int width, int height, int seed, Area wrapper) {
        this(width,height,wrapper);
        rand = new Random(seed);
    }
    
    public void init() {
        fillStandardGrass();
        MapGenerator roadGen = new RoadGenerator(map);
        roadGen.generateMap();
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
}