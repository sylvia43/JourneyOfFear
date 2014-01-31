package game.util.resource;

public class MusicPlayer {
    
    public static void play(MusicLibrary music) {
        play(music,0.5f);
    }

    public static void play(MusicLibrary music, float volume) {
        play(music,volume,1.0f);
    }
    
    public static void play(MusicLibrary music, float volume, float pitch) {
        music.getMusic().play(pitch,volume);
    }
}