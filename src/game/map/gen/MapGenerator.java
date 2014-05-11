package game.map.gen;

import game.map.Tile;

public abstract class MapGenerator {
    
    protected Tile[][] map;
    
    public MapGenerator(Tile[][] map) {
        this.map = map;
    }
    
    /** Modifies the Tile[][] provided in constructor. */
    public abstract void generateMap();
}
