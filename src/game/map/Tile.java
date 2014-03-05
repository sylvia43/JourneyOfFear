package game.map;

import game.util.resource.ImageLibrary;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    TEST(ImageLibrary.TEST,true),
    GRASS_BASIC(ImageLibrary.GRASS_BASIC,true),
    GRASS_VARIANT(ImageLibrary.GRASS_VARIANT,true),
    GRASS_FLOWER(ImageLibrary.GRASS_FLOWER,true),
    GRASS_BOLD(ImageLibrary.GRASS_BOLD,true),
    GRASS_SHIFT(ImageLibrary.GRASS_SHIFT,true),
    STONE_BASIC(ImageLibrary.STONE_BASIC,true),
    DIRT_BASIC(ImageLibrary.DIRT_BASIC,true),
    COBBLE_BASIC(ImageLibrary.COBBLE_BASIC,true),
    COBBLE_VARIANT1(ImageLibrary.COBBLE_VARIANT1,true),
    COBBLE_VARIANT2(ImageLibrary.COBBLE_VARIANT2,true),
    COBBLE_ACCENT_GRASS(ImageLibrary.COBBLE_ACCENT_GRASS,true);
    
    private Image image;
    private boolean passable;
    
    public Image image() { return image; }
    public boolean isPassable() { return passable; }
    
    Tile(ImageLibrary image, boolean passable) {
        this.passable = passable;
        try {
            this.image = image.getImage();
        } catch (SlickException e) {
            System.out.println("Error loading tile: " + e);
        }
    }
}
