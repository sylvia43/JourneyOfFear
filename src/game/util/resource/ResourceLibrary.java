package game.util.resource;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

// This class exists to prevent multiple objects from creating duplicate images,
// saving memory. If there are 500 enemies and they each own different images in
// the heap, that's just a waste of memory. Put all resources here so to avoid
// duplicates in the heap, and make things simpler (except this class).

public class ResourceLibrary {
    
    // Player sprites.
    private static Animation player_right = null;
    private static Animation player_up = null;
    private static Animation player_left = null;
    private static Animation player_down = null;
    
    // Attack sprites.
    private static Animation normal_sword_slash = null;
    
    // Enemy sprites.
    private static Animation enemy_blob_right = null;
    private static Animation enemy_blob_up = null;
    private static Animation enemy_blob_down = null;
    private static Animation enemy_blob_left = null;
    
    private static Animation enemy_sirblob_right = null;
    private static Animation enemy_sirblob_up = null;
    private static Animation enemy_sirblob_down = null;
    private static Animation enemy_sirblob_left = null;
    // The format of these getters is basically:
    // If it is not initialized, initialize and return it.
    // Otherwise just return it.
    
    
    // Public methods.
    
    public static Animation getPlayerRight() throws SlickException {
        return getAnim(player_right,"player/right.png",166);
    }
    
    public static Animation getPlayerUp() throws SlickException {
        return getAnim(player_up,"player/up.png",166);
    }
    
    public static Animation getPlayerLeft() throws SlickException {
        return getAnim(player_left,"player/left.png",166);

    }
    
    public static Animation getPlayerDown() throws SlickException {
        return getAnim(player_down,"player/down.png",166);
    }
    
    public static Animation getNormalSword(int speed) throws SlickException {
        return getAnim(normal_sword_slash,"player/attacks/sword_slash.png",speed,48);
    }
    
    public static Animation getBlobRight() throws SlickException {
        return getAnim(enemy_blob_right,"blobred/right.png",166);
    }

    public static Animation getBlobUp() throws SlickException {
        return getAnim(enemy_blob_up,"blobred/up.png",166);
    }
    
    public static Animation getBlobLeft() throws SlickException {
        return getAnim(enemy_blob_left,"blobred/left.png",166);
    }
    
    public static Animation getBlobDown() throws SlickException {
        return getAnim(enemy_blob_down,"blobred/down.png",166);
    }
    
    public static Animation getSirBlobRight() throws SlickException {
        return getAnim(enemy_sirblob_right,"blobredsir/right.png",166);
    }

    public static Animation getSirBlobUp() throws SlickException {
        return getAnim(enemy_sirblob_up,"blobredsir/up.png",166);
    }
    
    public static Animation getSirBlobLeft() throws SlickException {
        return getAnim(enemy_sirblob_left,"blobredsir/left.png",166);
    }
    
    public static Animation getSirBlobDown() throws SlickException {
        return getAnim(enemy_sirblob_down,"blobredsir/down.png",166);
    }
    
    // Helper methods.
    
    private static Animation getAnim(Animation anim, String filepath, int speed, int size) throws SlickException {
        if (anim==null)
            anim = ResourceLoader.initializeAnimation(filepath,speed,size);
        return anim;
    }
    
    private static Animation getAnim(Animation anim, String filepath, int speed) throws SlickException {
        return getAnim(anim,filepath,speed,16);  
    }
}