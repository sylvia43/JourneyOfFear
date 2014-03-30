package game.util.resource;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public enum SoundLibrary {
    
    SWORD_SWING_1("swing1.ogg"),
    SWORD_SWING_2("swing2.ogg"),
    SWORD_SWING_3("swing3.ogg"),
    SWORD_HIT("hit_sound.ogg");
    
    private String filepath;
    private Sound sound;
    
    public Sound getSound() {
        if (sound == null) {
            try {
            this.sound = ResourceLoader.initializeSound(filepath);
            } catch (SlickException e) {
                System.out.println("Error loading resources! " + e);
                throw new RuntimeException("Error loading resources! " + e);
            }
        }
        return sound;
    }
    
    SoundLibrary(String filepath) {
        this.filepath = filepath;
    }
}