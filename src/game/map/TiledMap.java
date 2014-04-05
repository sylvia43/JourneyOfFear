package game.map;

public class TiledMap {
    
    private Tile[][] map;
    
    public Tile getTile(int x, int y) {
        if (x<0 || y<0 || x>map.length || y>map[x].length)
            return null;
        return map[x][y];
    }
    
    public TiledMap(int width, int height) {
        map = new Tile[width][height];
    }
    
    public void init() {
        if (Math.random()<0.5)
            fillStandardGrass();
        else
            fillStandardCobble();
        
        map[6][5] = Tile.GRASS_COBBLE_TRANS_SMALL_RIGHT;
        map[6][4] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP_RIGHT;
        map[5][4] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP;
        map[4][4] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP_LEFT;
        map[4][5] = Tile.GRASS_COBBLE_TRANS_SMALL_LEFT;
        map[4][6] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM_LEFT;
        map[5][6] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM;
        map[6][6] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM_RIGHT;
        map[5][5] = Tile.GRASS_BASIC;
    }
    
    public void fillStandardGrass() {
        fill(Tile.GRASS_BASIC);
        randomize(Tile.GRASS_VARIANT,0.2);
        randomize(Tile.GRASS_SHIFT,0.1);
        randomize(Tile.GRASS_BOLD,0.05);
        randomize(Tile.GRASS_FLOWER,0.01);
    }
    
    public void fillStandardCobble() {
        fill(Tile.COBBLE_BASIC);
        randomize(Tile.COBBLE_VARIANT1,0.2);
        randomize(Tile.COBBLE_VARIANT2,0.1);
        randomize(Tile.COBBLE_ACCENT_GRASS,0.05);
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