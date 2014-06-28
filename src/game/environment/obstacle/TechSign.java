package game.environment.obstacle;

import game.hud.HUD;
import game.hud.MessageWindow;
import game.map.Area;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Color;

public class TechSign extends Obstacle {
    
    public static final Color SIGN_COLOR = new Color(185,147,66);
    private MessageWindow messageWindow;
    private boolean messageOpen;
    
    public TechSign() {
        this((int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)));
    }

    public TechSign(int x, int y) {
        super(x,y);
        messageOpen = false;
        minimapColor = SIGN_COLOR;
    }
    
    @Override
    public void update(int delta, Area currentArea) { }
    
    public boolean updateMessageWindow(int delta) {
        return messageWindowInitialized() && messageWindow.update(delta);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.SIGN_SMALL.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
        spriteWidth = sprite.getImage(0).getWidth()*4;
        spriteHeight = sprite.getImage(0).getHeight()*4;
    }

    public HUD getMessageWindow() {
        if (messageWindow == null)
            messageWindow = new MessageWindow("You're reading\na sign!",500);
        return messageWindow;
    }
    
    public void resetTimer() {
        messageWindow.resetTimer();
    }

    public boolean messageWindowInitialized() {
        return messageWindow != null;
    }

    public void openWindow() {
        messageOpen = true;
    }

    public void closeWindow() {
        messageOpen = false;
    }

    public boolean messageWindowOpen() {
        return messageOpen;
    }
}