package game;

import game.util.ResourceLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    TEST("test_blank.png",true),
    GRASS_BASIC("dirt_basic.png",true),
    GRASS_FLOWER("dirt_basic.png",true),
    GRASS_BOLD("dirt_basic.png",true),
    GRASS_SHIFT("dirt_basic.png",true),
    STONE_BASIC("dirt_basic.png",true),
    DIRT_BASIC("dirt_basic.png",true);
    //GRASS_DIRT_TRANS("grass_dirt_trans.png",true);
    
    private Image image;
    private boolean passable;
    
    public Image image() { return image; }
    public boolean isPassable() { return passable; }
    
    Tile(String filepath, boolean passable) {
        this.passable = passable;
        try {
            image = ResourceLoader.initializeImage("tiles/" + filepath);
        } catch (SlickException e) { }
    }
}