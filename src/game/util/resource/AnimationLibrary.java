package game.util.resource;

import game.error.ResourceException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/** Stores animations. */
public enum AnimationLibrary {
    
    // Player sprites.
    PLAYER_RIGHT(ImageLibrary.PLAYER_RIGHT,166,16,16),
    PLAYER_UP(ImageLibrary.PLAYER_UP,166,16,16),
    PLAYER_LEFT(ImageLibrary.PLAYER_LEFT,166,16,16),
    PLAYER_DOWN(ImageLibrary.PLAYER_DOWN,166,16,16),
    
    // Attack sprites.
    ATTACK_SWORD_SLASH(ImageLibrary.ATTACK_SWORD_SLASH,20,48,48),
    ATTACK_SWORD_STAB(ImageLibrary.ATTACK_SWORD_STAB,20,48,48),
    ATTACK_DAGGER_SLASH(ImageLibrary.ATTACK_DAGGER_SLASH,30,48,48),
    ATTACK_AXE_CLEAVE(ImageLibrary.ATTACK_AXE_CLEAVE,55,64,64),
    
    // Enemy sprites.
    BLOB_RED_RIGHT(ImageLibrary.BLOB_RED_RIGHT,166,16,16),
    BLOB_RED_UP(ImageLibrary.BLOB_RED_UP,166,16,16),
    BLOB_RED_DOWN(ImageLibrary.BLOB_RED_DOWN,166,16,16),
    BLOB_RED_LEFT(ImageLibrary.BLOB_RED_LEFT,166,16,16),
    
    BLOB_GREEN_RIGHT(ImageLibrary.BLOB_GREEN_RIGHT,166,16,16), 
    BLOB_GREEN_UP(ImageLibrary.BLOB_GREEN_UP,166,16,16),
    BLOB_GREEN_DOWN(ImageLibrary.BLOB_GREEN_DOWN,166,16,16),
    BLOB_GREEN_LEFT(ImageLibrary.BLOB_GREEN_LEFT,166,16,16),
    
    MUTANT_RIGHT(ImageLibrary.MUTANT_RIGHT,166,16,16),
    MUTANT_UP(ImageLibrary.MUTANT_UP,166,16,16),
    MUTANT_DOWN(ImageLibrary.MUTANT_DOWN,166,16,16),
    MUTANT_LEFT(ImageLibrary.MUTANT_LEFT,166,16,16),
    
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
                throw new ResourceException("Error loading resources! " + e);
            }
        }
        return anim.copy();  
    }
}
