package game;

public class TiledMap {
    
    private Tile[][] map;
    
    public Tile getTile(int x, int y) { return map[x][y]; }
    
    public TiledMap(int width, int height) {
        map = new Tile[width][height];
        fill(Tile.GRASS);
    }

    private void fill(Tile tile) {
        for(int i=0;i<map.length;i++) {
            for(int j=0;j<map[i].length;j++) {
                map[i][j] = tile;
            }
        }
    }
    
    private void randomize(Tile tile) {
        for(int i=0;i<map.length;i++) {
            for(int j=0;j<map[i].length;j++) {
                if (Math.random()>0.5)
                    map[i][j] = tile;
            }
        }
    }
}