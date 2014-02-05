package game.util.resource;

// This class exists to prevent multiple objects from creating duplicate images,

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

// the heap, that's just a waste of memory. Put all resources here so to avoid
// duplicates in the heap, and make things simpler (except this class).
//yolo

public enum AnimationLibrary {
    
    // Player sprites.
    PLAYER_RIGHT("player/right.png",166),
    PLAYER_UP("player/up.png",166),
    PLAYER_LEFT("player/left.png",166),
    PLAYER_DOWN("player/down.png",166),
    
    // Player attack sprites.
    PLAYER_SWORD_SLASH("player/attacks/sword_slash.png",20),
    
    // Enemy sprites.
    BLOB_RIGHT("blobred/right.png",332),
    BLOB_UP("blobred/up.png",332),
    BLOB_DOWN("blobred/down.png",332),
    BLOB_LEFT("blobred/left.png",332),
    
    SIRBLOB_RIGHT("blobgreen/right.png",332),
    SIRBLOB_UP("blobgreen/up.png",332),
    SIRBLOB_DOWN("blobgreen/down.png",332),
    SIRBLOB_LEFT("blobgreen/left.png",332);
    
    private String filepath;
    private Animation anim;
    private int speed;
    
    AnimationLibrary(String filepath, int speed) {
        this.filepath = filepath;
        this.speed = speed;
    }
    
    public Animation getAnim(int size) throws SlickException {
        if (anim == null) {
            anim = ResourceLoader.initializeAnimation(filepath,speed,size);
        }
        return anim.copy();
    }
    
    public Animation getAnim() throws SlickException {
        return getAnim(16);  
    }
}