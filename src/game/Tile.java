package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    TEST("misc/enemy_blank.png",true),
    GRASS_BASIC("tiles/grass_basic.png",true),
    GRASS_FLOWER("tiles/grass_flower.png",true),
    GRASS_BOLD("tiles/grass_bold.png",true),
    GRASS_SHIFT("tiles/grass_shift.png",true),
    STONE_BASIC("tiles/stone_basic.png",true);
    
    Image image;
    boolean passable;
    
    Tile(String filepath, boolean passable) {
        this.passable = passable;
        try {
            image = ResourceLoader.initializeImage(filepath);
        } catch (SlickException e) { }
    }
    
    public Image image() { return image; }
}