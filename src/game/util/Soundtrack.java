package game.util;

import game.util.resource.MusicLibrary;

public class Soundtrack {
    
    private int currentTrackIndex = -1;
    private MusicLibrary[] music;
    private MusicLibrary currentMusic = null;
    
    public void pause() {
        if (currentMusic != null && !currentMusic.isLoading()) {
            if (currentMusic.isPaused())
                currentMusic.pause();
            else
                currentMusic.resume();
        }
    }
    
    public void pauseNoStart() {
        if (currentMusic != null && !currentMusic.isLoading() && currentMusic.isPaused())
            currentMusic.pause();
    }
    
    public void restart() {
        if (currentMusic != null && !currentMusic.isLoading())
            currentMusic.restart();
    }
    
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
        
        while (newTrack == currentTrackIndex) {
            newTrack = (int)(music.length*Math.random());
        }
        currentTrackIndex = newTrack;
        
        currentMusic = music[newTrack];
        currentMusic.playMusic();
    }
}
