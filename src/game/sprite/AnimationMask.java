package game.sprite;

public class AnimationMask {
    
    private ImageMask[] mask;
    
    public AnimationMask(ImageMask[] mask) {
        this.mask = mask;
    }
    
    public ImageMask getImageMask(int index) { return mask[index]; }
}