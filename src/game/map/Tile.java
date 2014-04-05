package game.map;

import game.util.resource.ImageLibrary;
import org.newdawn.slick.Image;

public enum Tile {
    
    TEST(ImageLibrary.TEST),
    GRASS_BASIC(ImageLibrary.GRASS_BASIC),
    GRASS_VARIANT(ImageLibrary.GRASS_VARIANT),
    GRASS_FLOWER(ImageLibrary.GRASS_FLOWER),
    GRASS_BOLD(ImageLibrary.GRASS_BOLD),
    GRASS_SHIFT(ImageLibrary.GRASS_SHIFT),
    STONE_BASIC(ImageLibrary.STONE_BASIC),
    DIRT_BASIC(ImageLibrary.DIRT_BASIC),
    COBBLE_BASIC(ImageLibrary.COBBLE_BASIC),
    COBBLE_VARIANT1(ImageLibrary.COBBLE_VARIANT1),
    COBBLE_VARIANT2(ImageLibrary.COBBLE_VARIANT2),
    COBBLE_ACCENT_GRASS(ImageLibrary.COBBLE_ACCENT_GRASS),
    
    GRASS_COBBLE_TRANS_SMALL_RIGHT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,32,16,16,16),
    GRASS_COBBLE_TRANS_SMALL_TOP_RIGHT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,32,0,16,16),
    GRASS_COBBLE_TRANS_SMALL_TOP(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,16,0,16,16),
    GRASS_COBBLE_TRANS_SMALL_TOP_LEFT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,0,0,16,16),
    GRASS_COBBLE_TRANS_SMALL_LEFT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,0,16,16,16),
    GRASS_COBBLE_TRANS_SMALL_BOTTOM_LEFT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,0,32,16,16),
    GRASS_COBBLE_TRANS_SMALL_BOTTOM(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,16,32,16,16),
    GRASS_COBBLE_TRANS_SMALL_BOTTOM_RIGHT(ImageLibrary.GRASS_COBBLE_TRANS_SMALL,32,32,16,16);
    
    private ImageLibrary image;
    
    private boolean useSubImage = false;
    
    private int x;
    private int y;
    private int width;
    private int height;
    
    public Image image() {
        if (!useSubImage)
            return image.getImage();
        return image.getImage().getSubImage(x,y,width,height);
    }
    
    Tile(ImageLibrary image) {
        this.image = image;
    }
    
    Tile(ImageLibrary image, int x, int y, int width, int height) {
        useSubImage = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
}
