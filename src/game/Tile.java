package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    NULL(null,true),
    TEST("resources/misc/enemy_blank.png",true),
    GRASS("resources/tiles/back_grass.png",true);
    
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