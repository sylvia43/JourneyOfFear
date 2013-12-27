package game;

public class AnimationMask {
    
    ImageMask[] mask;
    
    public AnimationMask(ImageMask[] mask) {
        this.mask = mask;
    }
    
    public ImageMask getMask(int index) { return mask[index]; }
}