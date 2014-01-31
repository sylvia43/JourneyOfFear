package game.util.resource;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public enum MusicLibrary {
    
    SPACE_BALLS("space_balls.ogg");
    
    private Music sound;
    
    public Music getMusic() { return sound; }
    
    MusicLibrary(String filepath) {
        try {
            this.sound = ResourceLoader.initializeMusic(filepath);
        } catch (SlickException e) {
            System.out.println("Error loading sound: " + e);
        }
    }
}