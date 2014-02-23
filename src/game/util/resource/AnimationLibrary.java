package game.util.resource;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/** 
 * This class exists to prevent multiple objects from creating duplicate animations,
 * the heap; that's just a waste of memory. Put all resources here so to avoid
 * duplicates in the heap, and make things simpler (except this class).
 */
public enum AnimationLibrary {
    
    // Player sprites.
    PLAYER_RIGHT("player/right.png",166,16,16),
    PLAYER_UP("player/up.png",166,16,16),
    PLAYER_LEFT("player/left.png",166,16,16),
    PLAYER_DOWN("player/down.png",166,16,16),
    
    // Player attack sprites.
    PLAYER_SWORD_SLASH("player/attacks/sword_slash.png",20,48,48),
    
    // Enemy sprites.
    BLOB_RIGHT("blobred/right.png",332,16,16),
    BLOB_UP("blobred/up.png",332,16,16),
    BLOB_DOWN("blobred/down.png",332,16,16),
    BLOB_LEFT("blobred/left.png",332,16,16),
    
    SIRBLOB_RIGHT("blobgreen/right.png",332,16,16), 
    SIRBLOB_UP("blobgreen/up.png",332,16,16),
    SIRBLOB_DOWN("blobgreen/down.png",332,16,16),
    SIRBLOB_LEFT("blobgreen/left.png",332,16,16),
    
    //Environment sprites
    SPIKES("environment/spikes.png",166,32,39),
    SPIKES_MODULAR("environment/spikes_modular.png", 166, 4, 11),
    GREEN_SLIME_PIT("environment/greenslimepit.png",332,16,16),
    PINK_SLIME_PIT("environment/pinkslimepit.png",332,16,16);
    
    private String filepath;
    private Animation anim;
    private int speed;
    private int width;
    private int height;
    
    AnimationLibrary(String filepath, int speed, int width, int height) {
        this.filepath = filepath;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }
    
    public Animation getAnim() throws SlickException {
        if (anim == null)
            anim = ResourceLoader.initializeAnimation(filepath,speed,width,height);
        return anim.copy();  
    }
}
