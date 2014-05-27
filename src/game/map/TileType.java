package game.map;

public enum TileType {
    
    GRASS,DIRT,COBBLE;

    public static TileType getByName(String name) {
        for (TileType t : TileType.values()) {
            if (t.name().equals(name))
                return t;
        }
        return null;
    }
}
