package game.util;

import game.util.resource.MusicLibrary;

public class Soundtrack {
    
    private int currentTrack = -1;
    private MusicLibrary[] music;
    private MusicLibrary currentMusic = null;
        
    public void init() {
        music = MusicLibrary.values();
        playNewTrack();
    }
    
    public void update() {
        if (!currentMusic.isPlaying())
            playNewTrack();
    }
    
    public void playNext() {
        if (currentMusic != null && !currentMusic.isLoading())
            playNewTrack();
    }
    
    private void playNewTrack() {
        if (currentMusic != null)
            currentMusic.stop();
        
        int newTrack = (int)(music.length*Math.random());
        
        while (newTrack == currentTrack) {
            newTrack = (int)(music.length*Math.random());
        }
        currentTrack = newTrack;
        
        currentMusic = music[newTrack];
        currentMusic.playMusic();
    }
}
