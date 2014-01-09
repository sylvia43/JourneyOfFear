package game.util;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ResourceLoader {
    
    public static Image initializeImage(String filepath) throws SlickException {
        Image image = new Image("resources/" + filepath);
        image.setFilter(Image.FILTER_NEAREST);
        return image;
    }
    
    public static Animation initializeAnimation(String filepath, int delay, int size) throws SlickException {
        Image image = initializeImage(filepath);
        return new Animation(new SpriteSheet(image,(int)(size),(int)(size)),delay);
    }
    
    public static Animation initializeAnimation(String filepath, int delay) throws SlickException {
        return initializeAnimation(filepath, delay, 16);
    }
    
    public static Animation initializeAnimation(String filepath) throws SlickException {
        return initializeAnimation(filepath, 100, 16);
    }
}
