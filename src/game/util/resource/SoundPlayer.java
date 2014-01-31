package game.util.resource;

public class SoundPlayer {
    
    public static void play(SoundLibrary sound) {
        play(sound,0.5f);
    }

    public static void play(SoundLibrary sound, float volume) {
        play(sound,volume,1.0f);
    }
    
    public static void play(SoundLibrary sound, float volume, float pitch) {
        sound.getSound().play(pitch,volume);
    }
}