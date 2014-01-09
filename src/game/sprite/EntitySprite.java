package game.sprite;

import org.newdawn.slick.Animation;

public class EntitySprite {
    
    private Animation[] animation;
    private AnimationMask[] mask;
    
    public EntitySprite(int length) {
        animation = new Animation[length];
        mask = new AnimationMask[length];
    }
    
    public void setAnimations(Animation right, Animation up, Animation left, Animation down) {
        animation[0] = right;
        animation[1] = up;
        animation[2] = left;
        animation[3] = down;
    }
    
    public void setMasks(AnimationMask right, AnimationMask up, AnimationMask left, AnimationMask down) {
        mask[0] = right;
        mask[1] = up;
        mask[2] = left;
        mask[3] = down;
    }
    
    public Animation getAnim(int index) { return animation[index]; }
    public AnimationMask getAnimationMask(int index) { return mask[index]; }
}