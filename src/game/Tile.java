package game;

import game.util.ResourceLoader;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    TEST("test_blank.png",true),
    GRASS_BASIC("grass_basic.png",true),
    GRASS_FLOWER("grass_flower.png",true),
    GRASS_BOLD("grass_bold.png",true),
    GRASS_SHIFT("grass_shift.png",true),
    STONE_BASIC("stone_basic.png",true),
    DIRT_BASIC("dirt_basic.png",true);

    // Transition tiles need to be 8 different tiles here. In fact, they can be
    // 9 and we can use the middle one as the normal tile. We also need a way to
    // let tiles have logic, in choosing their forms. Maybe another object that
    // has the all the transitions/variations? Or could it be handled by the
    // Area object? Or a World/WorldBuilder object?
    
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