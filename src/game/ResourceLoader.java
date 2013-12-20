package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ResourceLoader {
    
    public static Animation initializeAnimation(String filepath, float scale, int size, int delay) throws SlickException {
        Image image = new Image(filepath);
        image.setFilter(Image.FILTER_NEAREST);
        image = image.getScaledCopy(scale);
        return new Animation(new SpriteSheet(image,(int)(size*scale),(int)(size*scale)),delay);
    }
}
