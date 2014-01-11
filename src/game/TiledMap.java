package game;

public class TiledMap {
    
    private Tile[][] map;
    
    public Tile getTile(int x, int y) { return map[x][y]; }
    
    public TiledMap(int width, int height) {
        map = new Tile[width][height];
        fill(Tile.TEST);
    }
    
    public void fillStandardGrass() {
        fill(Tile.GRASS_BASIC);
        randomize(Tile.GRASS_SHIFT,0.1);
        randomize(Tile.GRASS_BOLD,0.05);
        randomize(Tile.GRASS_FLOWER,0.01);
    }
    
    private void fill(Tile tile) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                map[i][j] = tile;
    }
    
    public void randomize(Tile tile, double chance) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                if (Math.random()<chance)
                    map[i][j] = tile;
    }
}