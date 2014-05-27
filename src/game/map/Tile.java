package game.map;

import game.util.resource.ImageLibrary;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Image;

public abstract class Tile {
    
    public static final Tile TEST                = new BasicTile(ImageLibrary.TEST,100);
    public static final Tile GRASS_BASIC         = new BasicTile(ImageLibrary.GRASS_BASIC,100,TileType.GRASS);
    public static final Tile GRASS_VARIANT       = new BasicTile(ImageLibrary.GRASS_VARIANT,20,TileType.GRASS);
    public static final Tile GRASS_FLOWER        = new BasicTile(ImageLibrary.GRASS_FLOWER,1,TileType.GRASS);
    public static final Tile GRASS_BOLD          = new BasicTile(ImageLibrary.GRASS_BOLD,5,TileType.GRASS);
    public static final Tile GRASS_SHIFT         = new BasicTile(ImageLibrary.GRASS_SHIFT,10,TileType.GRASS);
    public static final Tile STONE_BASIC         = new BasicTile(ImageLibrary.STONE_BASIC,100);
    public static final Tile DIRT_BASIC          = new BasicTile(ImageLibrary.DIRT_BASIC,100,TileType.DIRT);
    public static final Tile COBBLE_BASIC        = new BasicTile(ImageLibrary.COBBLE_BASIC,100,TileType.COBBLE);
    public static final Tile COBBLE_VARIANT1     = new BasicTile(ImageLibrary.COBBLE_VARIANT1,20,TileType.COBBLE);
    public static final Tile COBBLE_VARIANT2     = new BasicTile(ImageLibrary.COBBLE_VARIANT2,10,TileType.COBBLE);
    public static final Tile COBBLE_ACCENT_GRASS = new BasicTile(ImageLibrary.COBBLE_ACCENT_GRASS,5,TileType.COBBLE);
    
    public static final Tile GRASS_COBBLE_TRANS  = new TransitionTile(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,100,16);
    
    public static final List<Tile> allTiles;
    
    static {
        allTiles = new ArrayList<Tile>();
        allTiles.add(TEST);
        allTiles.add(TEST);
        allTiles.add(GRASS_BASIC);
        allTiles.add(GRASS_VARIANT);
        allTiles.add(GRASS_FLOWER);
        allTiles.add(GRASS_BOLD);
        allTiles.add(GRASS_SHIFT);
        allTiles.add(STONE_BASIC);
        allTiles.add(DIRT_BASIC);
        allTiles.add(COBBLE_BASIC);
        allTiles.add(COBBLE_VARIANT1);
        allTiles.add(COBBLE_VARIANT2);
        allTiles.add(COBBLE_ACCENT_GRASS);
    }
    
    protected ImageLibrary image;
    protected TileType[] types;
    protected int chance;
    
    public abstract Image getImage(int dir);
    
    public boolean isType(TileType type) {
        for (TileType t : types) {
            if (t==type)
                return true;
        }
        return false;
    }
    
    protected Tile(ImageLibrary image, int chance, TileType... types) {
        this.image = image;
        this.chance = chance;
        this.types = types;
    }
    
    public static Tile getTile(TileType type) {
        
        List<Tile> tiles = new ArrayList<Tile>();
        int sumChance = 0;
        
        for (Tile tile : allTiles) {
            for (TileType t : tile.types) {
                if (type == t) {
                    tiles.add(tile);
                    sumChance += tile.chance;
                    break;
                }
            }
        }
        
        int r = (int)(Math.random()*sumChance);
        
        int chanceCount = 0;
        
        for (Tile t : tiles) {
            if (chanceCount > r)
                return t;
            chanceCount += t.chance;
        }
        return null;
    }
}
