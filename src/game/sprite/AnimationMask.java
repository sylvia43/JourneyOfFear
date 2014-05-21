package game.sprite;

/** Stores ImageMasks to cover an entire Animation. */
public class AnimationMask {
    
    private ImageMask[] mask;
    
    public AnimationMask(ImageMask[] mask) {
        this.mask = mask;
    }
    
    public ImageMask getImageMask(int index) { return mask[index]; }
}