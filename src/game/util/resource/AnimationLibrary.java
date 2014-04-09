package game.util.resource;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

public enum AnimationLibrary {
    
    // Player sprites.
    PLAYER_RIGHT(ImageLibrary.PLAYER_RIGHT,166,16,16),
    PLAYER_UP(ImageLibrary.PLAYER_UP,166,16,16),
    PLAYER_LEFT(ImageLibrary.PLAYER_LEFT,166,16,16),
    PLAYER_DOWN(ImageLibrary.PLAYER_DOWN,166,16,16),
    
    // Attack sprites.
    ATTACK_SWORD_SLASH(ImageLibrary.ATTACK_SWORD_SLASH,20,48,48),
    ATTACK_DAGGER_SLASH(ImageLibrary.ATTACK_DAGGER_SLASH,30,48,48),
    ATTACK_AXE_CLEAVE(ImageLibrary.ATTACK_AXE_CLEAVE,55,64,64),
    
    // Enemy sprites.
    BLOB_RIGHT(ImageLibrary.BLOB_RIGHT,332,16,16),
    BLOB_UP(ImageLibrary.BLOB_UP,332,16,16),
    BLOB_DOWN(ImageLibrary.BLOB_DOWN,332,16,16),
    BLOB_LEFT(ImageLibrary.BLOB_LEFT,332,16,16),
    
    SIRBLOB_RIGHT(ImageLibrary.SIRBLOB_RIGHT,332,16,16), 
    SIRBLOB_UP(ImageLibrary.SIRBLOB_UP,332,16,16),
    SIRBLOB_DOWN(ImageLibrary.SIRBLOB_DOWN,332,16,16),
    SIRBLOB_LEFT(ImageLibrary.SIRBLOB_LEFT,332,16,16),
    
    ENEMY_PLAYER_PLACEHOLDER(ImageLibrary.TEST,166,16,16),
    
    //Environment sprites
    SPIKES(ImageLibrary.SPIKES,166,32,39),
    SPIKES_MODULAR(ImageLibrary.SPIKES_MODULAR,166,4,11),
    GREEN_SLIME_PIT(ImageLibrary.GREEN_SLIME_PIT,332,16,16),
    PINK_SLIME_PIT(ImageLibrary.PINK_SLIME_PIT,332,16,16),
    TREE_LARGE(ImageLibrary.TREE_LARGE,332,64,48);
    
    private ImageLibrary image;
    private Animation anim;
    private int speed;
    private int width;
    private int height;
    
    AnimationLibrary(ImageLibrary image, int speed, int width, int height) {
        this.image = image;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    
    public Animation getAnim() {
        if (anim == null) {
            try {
                anim = ResourceLoader.initializeAnimation(image.getImage(),speed,width,height);
            } catch (SlickException e) {
                System.out.println("Error loading resources! " + e);
                throw new RuntimeException("Error loading resources! " + e);
            }
        }
        return anim.copy();  
    }
}
