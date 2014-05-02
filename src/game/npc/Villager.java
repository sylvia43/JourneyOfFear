package game.npc;

import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;

public class Villager extends NPC {

    @Override
    public void init() {
        initializeSprite();
        spriteHeight = sprite.getAnim(spritePointer).getHeight() * 4;
        spriteWidth = sprite.getAnim(spritePointer).getWidth() * 4;
    }

    @Override
    public void update(int delta) {
    }
    
    @Override
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        
        Animation[] animList = {
            AnimationLibrary.PLAYER_RIGHT.getAnim(),
            AnimationLibrary.PLAYER_UP.getAnim(),
            AnimationLibrary.PLAYER_LEFT.getAnim(),
            AnimationLibrary.PLAYER_DOWN.getAnim()
        };
        sprite.setAnimations(animList);
        
        AnimationMask[] animMaskList = {
            initializeMask(0),
            initializeMask(1),
            initializeMask(2),
            initializeMask(3)
        };
        sprite.setMasks(animMaskList);
    }
    
    @Override
    protected AnimationMask initializeMask(int index) {
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
}
