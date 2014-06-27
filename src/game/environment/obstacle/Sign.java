package game.environment.obstacle;

import game.map.Area;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Color;

public class Sign extends Obstacle {
    
    public static final Color signMinimapColor = new Color(185,147,66);
    
    public Sign() {
        this((int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)));
    }

    public Sign(int x, int y) {
        super(x,y);
        minimapColor = signMinimapColor;
    }
    
    @Override
    public void update(int delta, Area currentArea) { }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.SIGN_SMALL.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
        spriteWidth = sprite.getImage(0).getWidth()*4;
        spriteHeight = sprite.getImage(0).getHeight()*4;
    }
}
