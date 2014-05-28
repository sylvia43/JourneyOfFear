package game.enemy;

import game.network.server.EnemyPlayerData;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/** Wrapper around EnemyPlayerData for client to render. */
public class EnemyPlayer {
    
    public EnemyPlayerData data;
    private EntitySprite sprite;
    private int spriteHeight;
    private int spriteWidth;
    private boolean initialized = false;
    
    public EnemyPlayer(EnemyPlayerData data) {
        this.data = data;
    }
    
    public void render(GameContainer container, Graphics g) {
        if (!initialized) {
            initializeSprite();
            spriteHeight = sprite.getAnim(data.dir).getHeight() * 4;
            spriteWidth = sprite.getAnim(data.dir).getWidth() * 4;
            initialized = true;
        }
        
        sprite.getAnim(data.dir).setCurrentFrame(data.frame);
        
        sprite.getAnim(data.dir).draw(data.x-spriteHeight/2,data.y-spriteWidth/2,64,64);
    }
    
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.PLAYER_RIGHT.getAnim(),
            AnimationLibrary.PLAYER_UP.getAnim(),
            AnimationLibrary.PLAYER_LEFT.getAnim(),
            AnimationLibrary.PLAYER_DOWN.getAnim(),
        };
        for (Animation a : animList) {
            a.stop();
        }
        sprite.setAnimations(animList);
        initializeMask();
    }
    
    protected void initializeMask() {
        AnimationMask[] animMaskList = {
            createMask(0),
            createMask(1),
            createMask(2),
            createMask(3)
        };
        sprite.setMasks(animMaskList);
    }
    
    protected AnimationMask createMask(int index) {
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),data.x-32,data.y-32);
        }
        return new AnimationMask(masks);
    }
}