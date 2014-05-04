package game.npc;

import game.npc.npcutils.Routine;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.state.StateMultiplayer;
import game.util.GameObject;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class NPC extends GameObject {
    
    protected int x;
    protected int y;
    
    protected int spritePointer;
    protected int spriteHeight;
    protected int spriteWidth;
    
    protected Color minimapColor;
    
    protected Routine routine;
    
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getDepth() { return y; }
    public Color getColor() { return minimapColor; }
    
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
        spritePointer = 3;
        spriteHeight = sprite.getAnim(spritePointer).getHeight() * 4;
        spriteWidth = sprite.getAnim(spritePointer).getWidth() * 4;
    }
    
    public abstract void update(int delta);
    
    @Override
    public void render(Graphics g) {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.setCurrentFrame(1);
        currentSprite.stop();
        currentSprite.draw(x-spriteWidth/2,y-spriteHeight/2,64,64);
    }
    
    protected abstract void initializeSprite();
    
    protected AnimationMask initializeMask(int index) {
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
}
