package game.util.resource;

// This class exists to prevent multiple objects from creating duplicate images,

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

// the heap, that's just a waste of memory. Put all resources here so to avoid
// duplicates in the heap, and make things simpler (except this class).

public enum AnimationLibrary {
    /*

    return getAnim(normal_sword_slash,"player/attacks/sword_slash.png",speed,48);
    return getAnim(enemy_blob_right,"blobred/right.png",332);
    return getAnim(enemy_blob_up,"blobred/up.png",332);
    return getAnim(enemy_blob_left,"blobred/left.png",332);
    return getAnim(enemy_blob_down,"blobred/down.png",332);
    return getAnim(enemy_sirblob_right,"blobredsir/right.png",332);
    return getAnim(enemy_sirblob_up,"blobredsir/up.png",332);
    return getAnim(enemy_sirblob_left,"blobredsir/left.png",332);
    return getAnim(enemy_sirblob_down,"blobredsir/down.png",332);*/
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
    
    SIRBLOB_RIGHT("blobredsir/right.png",332),
    SIRBLOB_UP("blobredsir/up.png",332),
    SIRBLOB_DOWN("blobredsir/down.png",332),
    SIRBLOB_LEFT("blobredsir/left.png",332);
    
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
            return anim;
        }
        return anim.copy();
    }
    
    public Animation getAnim() throws SlickException {
        return getAnim(16);  
    }
}