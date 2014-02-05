package game.util.resource;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public enum MusicLibrary {
    
    DEMON_WITHIN("demon_within.ogg"),
    EVIL_SHOE("evil_shoe.ogg"),
    HARES_RABBITS_BUNNIES("hares_rabbits_bunnies.ogg"),
    HULL_INTEGRITY("hull_integrity_critical.ogg"),
    LAVENDER_TOWN("lavender_town.ogg"),
    LEL("lel.ogg"),
    MAJESTIC_BEAGLE("majestic_beagle.ogg"),
    MONKEY_BLADDERS("monkey_bladders.ogg"),
    RABID_RABBIT("rabid_rabbit.ogg"),
    ROBOT_UVULA("robot_uvula.ogg"),
    SPACE_BALLS("space_balls.ogg");
    
    private Music music;
    private String filepath;
    private boolean queued;
    private boolean loading;
    
    public boolean isLoading() {
        return loading;
    }
    
    public boolean isPlaying() {
        return (bound()&&music.playing()) || queued;
    }
    
    public boolean bound() {
        return music!=null;
    }
    
    public void bind() {
        try {
            music = ResourceLoader.initializeMusic(filepath);
        } catch (SlickException e) {
            System.out.println("Error loading sound: " + e);
        }
    }
    
    public void playMusic() {
        queued = true;
        Thread bindMusic = new Thread(new Runnable() {
            public void run() {
                loading = true;
                if (!bound())
                    bind();
                music.play();
                loading = false;
            }
        });
        bindMusic.start();
    }
    
    public void stop() {
        queued = false;
        music.stop();
        release();
    }
    
    public void release() {
        music = null;
    }
    
    MusicLibrary(String filepath) {
        this.filepath = filepath;
    }
}