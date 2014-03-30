package game.sprite;

import org.newdawn.slick.Animation;

public class EntitySprite {
    
    private Animation[] animation;
    private AnimationMask[] mask;
    
    public EntitySprite(int length) {
        animation = new Animation[length];
        mask = new AnimationMask[length];
    }
    
    public void setAnimations(Animation[] animList) {
        animation = animList.clone();
    }
    
    public void setMasks(AnimationMask[] animMaskList) {
        mask = animMaskList.clone();
    }
    
    public Animation getAnim(int index) { return animation[index]; }
    public AnimationMask getAnimationMask(int index) { return mask[index]; }
}