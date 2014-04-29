package game.npc;

import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class NPC extends GameObject {
    
    protected int x;
    protected int y;
    
    protected int spritePointer;
    
    protected Color minimapColor;
    
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    
    protected EntitySprite sprite;
    
    public NPC() {
        this((int)(Math.random()*StateMultiplayer.WORLD_SIZE_X),
                (int)(Math.random()*StateMultiplayer.WORLD_SIZE_Y));
    }
    
    public NPC(int x, int y) {
        this.x = x;
        this.y = y;
        minimapColor = Color.blue;
        spritePointer = 0;
    }
    
    public void init() {
        initializeSprite();
    }
    
    public void update(int delta) {
        
    }
    
    @Override
    public void render(Graphics g) {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.draw(x,y,64,64);
    }
    
    private void initializeSprite() {
        sprite = new EntitySprite(4);
        
        Animation[] animList = {
            AnimationLibrary.PLAYER_RIGHT.getAnim(),
            AnimationLibrary.PLAYER_UP.getAnim(),
            AnimationLibrary.PLAYER_LEFT.getAnim(),
            AnimationLibrary.PLAYER_DOWN.getAnim()
        };
        sprite.setAnimations(animList);
        
        AnimationMask[] animMaskList = {
            initializeMask(0),
            initializeMask(1),
            initializeMask(2),
            initializeMask(3)
        };
        sprite.setMasks(animMaskList);
    }
    
    private AnimationMask initializeMask(int index) {
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
}
