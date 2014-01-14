package game.util;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public enum SoundEffect {
    SWORD_HIT("sword_hit.ogg");
    
    private Sound sound;
    
    SoundEffect(String filepath) {
        try {
            this.sound = ResourceLoader.initializeSound(filepath);
        } catch (SlickException e) { }
    }
}