package game.map;

import game.util.resource.ImageLibrary;
import org.newdawn.slick.Image;

public class BasicTile extends Tile {
    
    protected BasicTile(ImageLibrary image) {
        super(image);
    }
    
    @Override
    public Image getImage(int dir) {
        return image.getImage();
    }
}
