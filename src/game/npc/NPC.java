package game.npc;

import game.enemy.Enemy;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
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
    
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getDepth() { return y; }
    
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
    
    public abstract void init();
    
    public abstract void update(int delta);
    
    @Override
    public void render(Graphics g) {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.draw(x-spriteWidth/2,y-spriteHeight/2,64,64);
    }
        
    protected void speak(Graphics g, Enemy e, int count) {
        g.drawString("Kill " + count + 
                e.getName() + (count == 1 ? "s " : " "), x, y+spriteHeight/2);
    }
    
    protected abstract void initializeSprite();
    protected abstract AnimationMask initializeMask(int index);
}
