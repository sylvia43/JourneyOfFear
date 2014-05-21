package game.util.resource;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/** Loads resources. */
public class ResourceLoader {
    
    public static Sound initializeSound(String filepath) throws SlickException {
        return new Sound("resources/sound/" + filepath);
    }
    
    public static Music initializeMusic(String filepath) throws SlickException {
        return new Music("resources/music/" + filepath);
    }
    
    public static Image initializeImage(String filepath) throws SlickException {
        Image image = new Image("resources/art/" + filepath);
        image.setFilter(Image.FILTER_NEAREST);
        return image;
    }
    
    public static Animation initializeAnimation(Image image, int delay, int width, int height) throws SlickException {
        return new Animation(new SpriteSheet(image,width,height),delay);
    }
    
    public static Animation initializeAnimation(Image image, int delay, int size) throws SlickException {
        return initializeAnimation(image,delay,size,size);
    }
    
    public static Animation initializeAnimation(Image image, int delay) throws SlickException {
        return initializeAnimation(image,delay,16);
    }
    
    public static Animation initializeAnimation(Image image) throws SlickException {
        return initializeAnimation(image,100,16);
    }
    
    public static Animation initializeAnimation(String filepath, int delay, int width, int height) throws SlickException {
        return initializeAnimation(initializeImage(filepath),delay,width,height);
    }
    
    public static Animation initializeAnimation(String filepath, int delay, int size) throws SlickException {
        return initializeAnimation(initializeImage(filepath),delay,size,size);
    }
    
    public static Animation initializeAnimation(String filepath, int delay) throws SlickException {
        return initializeAnimation(initializeImage(filepath),delay,16,16);
    }
    
    public static Animation initializeAnimation(String filepath) throws SlickException {
        return initializeAnimation(initializeImage(filepath),100,16,16);
    }
}
