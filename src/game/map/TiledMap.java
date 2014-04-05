package game.map;

public class TiledMap {
    
    private Tile[][] map;
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
        if (Math.random()<0.5)
            fillStandardGrass();
        else
            fillStandardCobble();
        
        for (int i=0;i<10;i++)
            createGrassPatch();
    }
    
    private void createGrassPatch() {
        
        int size = (int)(Math.random()*3);
        
        int x = (int)(Math.random()*(width-2*size)+size);
        int y = (int)(Math.random()*(height-2*size)+size);
        
        for (int i=-size;i<=size;i++) {
            for (int j=-size;j<=size;j++) {
                if (i>-size && j>-size && i<size && j<size) {
                    map[x+i][y+j] = Tile.GRASS_BASIC;
                    continue;
                }
                if (i>0 && j>0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM_RIGHT;
                else if (i>0 && j<0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP_RIGHT;
                else if (i<0 && j>0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM_LEFT;
                else if (i<0 && j<0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP_LEFT;
                else if (i>0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_RIGHT;
                else if (i<0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_LEFT;
                else if (j>0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_BOTTOM;
                else if (j<0)
                    map[x+i][y+j] = Tile.GRASS_COBBLE_TRANS_SMALL_TOP;
            }
        }
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