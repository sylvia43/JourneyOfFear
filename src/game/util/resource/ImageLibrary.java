package game.util.resource;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum ImageLibrary {
    
    // Player sprites.
    PLAYER_RIGHT("player/right.png"),
    PLAYER_UP("player/up.png"),
    PLAYER_LEFT("player/left.png"),
    PLAYER_DOWN("player/down.png"),
    
    // Player attack sprites.
    PLAYER_SWORD_SLASH("player/attacks/sword_slash.png"),
    
    // Hearts.
    EMPTY_HEART("player/health/health_empty.png"),
    HALF_HEART("player/health/health_half.png"),
    FULL_HEART("player/health/health_full.png"),
    
    // Enemy sprites.
    BLOB_RIGHT("blobred/right.png"),
    BLOB_UP("blobred/up.png"),
    BLOB_DOWN("blobred/down.png"),
    BLOB_LEFT("blobred/left.png"),
    
    SIRBLOB_RIGHT("blobgreen/right.png"), 
    SIRBLOB_UP("blobgreen/up.png"),
    SIRBLOB_DOWN("blobgreen/down.png"),
    SIRBLOB_LEFT("blobgreen/left.png"),
    
    // Environment sprites
    SPIKES("environment/spikes.png"),
    SPIKES_MODULAR("environment/spikes_modular.png"),
    GREEN_SLIME_PIT("environment/greenslimepit.png"),
    PINK_SLIME_PIT("environment/pinkslimepit.png"),
    TREE_LARGE("tiles/objects/tree_large.png"),
    
    // Tiles.
    TEST("tiles/test_blank.png"),
    GRASS_BASIC("tiles/grass/grass_basic.png"),
    GRASS_VARIANT("tiles/grass/grass_variant.png"),
    GRASS_FLOWER("tiles/grass/grass_flower.png"),
    GRASS_BOLD("tiles/grass/grass_bold.png"),
    GRASS_SHIFT("tiles/grass/grass_shift.png"),
    STONE_BASIC("tiles/stone_basic.png"),
    DIRT_BASIC("tiles/dirt/dirt_basic.png"),
    COBBLE_BASIC("tiles/cobble/cobble_basic.png"),
    COBBLE_VARIANT1("tiles/cobble/cobble_variant_1.png"),
    COBBLE_VARIANT2("tiles/cobble/cobble_variant_2.png"),
    COBBLE_ACCENT_GRASS("tiles/grass/cobble_accent_grass.png");
    
    private String filepath;
    private Image image;
    
    ImageLibrary(String filepath) {
        this.filepath = filepath;
    }
    
    public Image getImage() {
        if (image == null) {
            try {
                image = ResourceLoader.initializeImage(filepath);
            } catch (SlickException e) {
                System.out.println("Error loading resources! " + e);
                throw new RuntimeException("Error loading resources! " + e);
            }
        }
        return image.copy();
    }
}
