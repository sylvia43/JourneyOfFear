package game.map;

import game.util.resource.ImageLibrary;
import org.newdawn.slick.Image;

public class TransitionTile extends Tile {
    
    private int size;
    
    protected TransitionTile(ImageLibrary image, int chance, int size, TileType... types) {
        super(image,chance,types);
        this.size = size;
    }

    @Override
    public Image getImage(int dir) {
        int x=0,y=0;
        if (dir>6 || dir<2) x=1;
        if (dir>2 && dir<6) x=-1;
        if (dir>4 && dir<8) y=1;
        if (dir>0 && dir<4) y=-1;
        return image.getImage().getSubImage(x*16,y*16,size,size);
    }
}
