package game;

import org.newdawn.slick.Animation;

public class EntitySprite {
    
    private Animation[] animation = new Animation[4];
    private AnimationMask[] mask = new AnimationMask[4];
    
    public EntitySprite() {
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
    public AnimationMask getMask(int index) { return mask[index]; }
}