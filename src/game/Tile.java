package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Tile {
    TEST(new String[]{"resources/misc/enemy_blank.png"},true),
    GRASS(new String[]{"resources/tiles/back_grass.png"},true);
    
    Image[] image;
    boolean passable;
    
    Tile(String[] filepaths, boolean passable) {
        this.passable = passable;
        image = new Image[filepaths.length];
        for (int i=0;i<filepaths.length;i++)
            try {
                image[i] = ResourceLoader.initializeImage(filepaths[i]);
            } catch (SlickException e) { }
    }
    
    public Image image(int index) { return image[index]; }
}