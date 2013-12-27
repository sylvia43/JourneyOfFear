package game;

public class AnimationMask {
    
    EntityMask[] mask;
    
    public AnimationMask(EntityMask[] mask) {
        this.mask = mask;
    }
    
    public EntityMask getMask(int index) { return mask[index]; }
}