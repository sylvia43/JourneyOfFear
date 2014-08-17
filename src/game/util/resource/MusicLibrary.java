package game.util.resource;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/** Stores music. */
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
    IM_NOT_POSITIVE("im_not_positive.ogg"),
    ICED_MUSIC("iced_music.ogg"),
    BASED_GOD_HARMOR("based_god_humor.ogg"),
    SPACE_BALLS("space_balls.ogg"); 
    
    private Music music;
    private String filepath;
    private boolean queued;
    private boolean loading;
    
    public static final float VOLUME = 0.4f;
    
    public boolean isLoading() {
        return loading;
    }
    
    private boolean bound() { return music!=null; }
    public boolean isPlaying() { return (bound() && music.playing() && music.getPosition() != 0.0f) || queued; }
    public boolean isPaused() { return music != null && music.playing(); }
    
    public void pause() { music.pause(); }
    public void resume() { music.resume(); }
    public void restart() { music.play(1.0f,VOLUME); }
    public void release() { music = null; }
    
    public void bind() {
        try {
            music = ResourceLoader.initializeMusic(filepath);
        } catch (SlickException e) {
            System.out.println("Error loading sound: " + e);
        }
    }
    
    public void stop() {
        queued = false;
        music.stop();
        release();
    }
        
    MusicLibrary(String filepath) {
        this.filepath = filepath;
    }
    
/** My first thread ^_^ */
    public void playMusic() {
        queued = true;
        Thread bindMusic = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean playing = false;
                if (playing == false) {
                    loading = true;
                    if (!bound())
                        bind();
                    try {
                        // Delay of 2 seconds between songs
                        Thread.sleep(2000);
                        music.play(1.0f, VOLUME);
                        // Delay after starting to ensure that getPosition()
                        // returns > 0.0f
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Error playing music: " + e);
                    }
                    loading = false;
                    playing = music.playing();
                }
                queued = false;
            }
        });
        bindMusic.start();
        }
}
