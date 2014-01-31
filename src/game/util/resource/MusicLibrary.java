package game.util.resource;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public enum MusicLibrary {
    
    SPACE_BALLS("space_balls.ogg"),
    HARES_RABBITS_BUNNIES("hares_rabbits_bunnies.ogg"),
    LAVENDER_TOWN("lavender_town.ogg"),
    LEL("lel.ogg"),
    MONKEY_BLADDERS("monkey_bladders.ogg"),
    RABID_RABBIT("rabid_rabbit.ogg"),
    ROBOT_UVULA("robot_uvula.ogg"),
    EVIL_SHOE("evil_shoe.ogg");
    
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