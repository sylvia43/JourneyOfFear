package game.util.resource;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public enum MusicLibrary {
    
    DEMON_WITHIN("demon_within.ogg"),
    EVIL_SHOE("evil_shoe.ogg"),
    HARES_RABBITS_BUNNIES("hares_rabbits_bunnies.ogg"),
    HULL_INTEGRITY("hull_integrity_critical.ogg"),
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
    
    public static final float VOLUME = 0.4f;
    
    public boolean isLoading() {
        return loading;
    }
    
    public boolean isPlaying() { return (bound()&&music.playing()) || queued; }
    
    public boolean isPaused() { return music != null && music.playing(); }
    
    public void pause() { music.pause(); }
    
    public void resume() { music.resume(); }

    public void restart() { music.play(1.0f,VOLUME); }
    
    public boolean bound() { return music!=null; }
    
    public void release() { music = null; }
    
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
                long start = System.currentTimeMillis();
                if (!bound())
                    bind();
                long end = System.currentTimeMillis();
                if ((end-start)<2000) {
                    try {
                        Thread.sleep(2000-end+start);
                    } catch (InterruptedException e) {
                        System.out.println("Error playing music: " + e);
                    }
                }
                music.play(1.0f,VOLUME);
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
        
    MusicLibrary(String filepath) {
        this.filepath = filepath;
    }
}
