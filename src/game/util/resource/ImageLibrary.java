package game.util.resource;

import game.error.ResourceException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum ImageLibrary {
    
    // Menu buttons.
    BUTTON_SINGLE("menu/Singleplayer.png"),
    BUTTON_MULTI("menu/Multiplayer.png"),
    BUTTON_SERVER("menu/ServerHost.png"),
    BUTTON_OPTION("menu/Options.png"),
    BACKGROUND("menu/menuBG.png"),
    
    // Player sprites.
    PLAYER_RIGHT("humanoid/right.png"),
    PLAYER_UP("humanoid/up.png"),
    PLAYER_LEFT("humanoid/left.png"),
    PLAYER_DOWN("humanoid/down.png"),
    
    // Attack sprites.
    ATTACK_SWORD_SLASH("attacks/sword_slash.png"),
    ATTACK_DAGGER_SLASH("attacks/dagger_slash.png"),
    ATTACK_AXE_CLEAVE("attacks/axe_cleave.png"),
    
    // Hearts.
    EMPTY_HEART("player/health/health_empty.png"),
    HALF_HEART("player/health/health_half.png"),
    FULL_HEART("player/health/health_full.png"),
    
    // Enemy sprites.
    BLOB_RIGHT("blobred/right.png"),
    BLOB_UP("blobred/vertical.png"),
    BLOB_DOWN("blobred/vertical.png"),
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
    COBBLE_ACCENT_GRASS("tiles/cobble/cobble_accent_grass.png"),
    
    GRASS_COBBLE_TRANS_SMALL("tiles/grass/grass_cobble_trans_small.png");
    
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
                throw new ResourceException("Error loading resources! " + e);
            }
        }
        return image.copy();
    }
}
