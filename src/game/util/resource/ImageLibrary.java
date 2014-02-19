package game.util.resource;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** 
 * This class exists to prevent multiple objects from creating duplicate images,
 * the heap, that's just a waste of memory. Put all resources here so to avoid
 * duplicates in the heap, and make things simpler (except this class).
 */
public enum ImageLibrary {
    
    // Hearts.
    EMPTY_HEART("player/health/health_empty.png"),
    HALF_HEART("player/health/health_half.png"),
    FULL_HEART("player/health/health_full.png");
    
    private String filepath;
    private Image image;
    
    ImageLibrary(String filepath) {
        this.filepath = filepath;
    }
    
    public Image getImage() throws SlickException {
        if (image == null)
            image = ResourceLoader.initializeImage(filepath);
        return image.copy();
    }
}
