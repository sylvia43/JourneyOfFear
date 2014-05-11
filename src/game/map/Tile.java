package game.map;

import game.util.resource.ImageLibrary;
import org.newdawn.slick.Image;

public abstract class Tile {
    
    public static final Tile TEST                = new BasicTile(ImageLibrary.TEST);
    public static final Tile GRASS_BASIC         = new BasicTile(ImageLibrary.GRASS_BASIC,TileType.GRASS);
    public static final Tile GRASS_VARIANT       = new BasicTile(ImageLibrary.GRASS_VARIANT,TileType.GRASS);
    public static final Tile GRASS_FLOWER        = new BasicTile(ImageLibrary.GRASS_FLOWER,TileType.GRASS);
    public static final Tile GRASS_BOLD          = new BasicTile(ImageLibrary.GRASS_BOLD,TileType.GRASS);
    public static final Tile GRASS_SHIFT         = new BasicTile(ImageLibrary.GRASS_SHIFT,TileType.GRASS);
    public static final Tile STONE_BASIC         = new BasicTile(ImageLibrary.STONE_BASIC);
    public static final Tile DIRT_BASIC          = new BasicTile(ImageLibrary.DIRT_BASIC,TileType.DIRT);
    public static final Tile COBBLE_BASIC        = new BasicTile(ImageLibrary.COBBLE_BASIC);
    public static final Tile COBBLE_VARIANT1     = new BasicTile(ImageLibrary.COBBLE_VARIANT1);
    public static final Tile COBBLE_VARIANT2     = new BasicTile(ImageLibrary.COBBLE_VARIANT2);
    public static final Tile COBBLE_ACCENT_GRASS = new BasicTile(ImageLibrary.COBBLE_ACCENT_GRASS);
    
    public static final Tile GRASS_COBBLE_TRANS  = new TransitionTile(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,16);
    
    protected ImageLibrary image;
    protected TileType[] types;
    
    public abstract Image getImage(int dir);
    
    public boolean isType(TileType type) {
        for (TileType t : types) {
            if (t==type)
                return true;
        }
        return false;
    }
    
    protected Tile(ImageLibrary image, TileType... types) {
        this.image = image;
        this.types = types;
    }
}
