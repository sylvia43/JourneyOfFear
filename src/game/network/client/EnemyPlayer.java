package game.network.client;

import game.network.server.EnemyPlayerData;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/** Wrapper around EnemyPlayerData for client to render. */
public class EnemyPlayer {
    
    public EnemyPlayerData data;
    private EntitySprite sprite;
    private int spriteHeight;
    private int spriteWidth;
    private boolean initialized = false;
    
    private List<Animation> attacks;
    private List<AnimationLibrary> attackRaw;
    private Animation attack;
    
    public EnemyPlayer(EnemyPlayerData data) {
        this.data = data;
        attackRaw = new ArrayList<AnimationLibrary>();
        attacks = new ArrayList<Animation>();
        attacks.add(null);
        attacks.add(null);
        attacks.add(null);
        attackRaw.add(AnimationLibrary.ATTACK_AXE_CLEAVE);
        attackRaw.add(AnimationLibrary.ATTACK_SWORD_SLASH);
        attackRaw.add(AnimationLibrary.ATTACK_DAGGER_SLASH);
    }
    
    public void render(Graphics g) {
        
        EnemyPlayerData localData = new EnemyPlayerData(data);
        
        if (!initialized) {
            initializeSprite();
            spriteHeight = sprite.getAnim(localData.dir).getHeight() * 4;
            spriteWidth = sprite.getAnim(localData.dir).getWidth() * 4;
            initialized = true;
        }
        
        sprite.getAnim(localData.dir).setCurrentFrame(localData.frame);
        sprite.getAnim(localData.dir).draw(localData.x-spriteHeight/2,localData.y-spriteWidth/2,64,64);
        
        if (data.weapFrame == -1)
            return;
        
        if (attacks.get(localData.weapType) == null) {
            attacks.set(localData.weapType,attackRaw.get(localData.weapType).getAnim());
        }
        
        attack = attacks.get(localData.weapType);
        attack.stop();
        attack.setCurrentFrame(localData.weapFrame);
        
        if (localData.weapType != 0)
            attack.draw(localData.x-spriteHeight/2-4*attack.getWidth()/3,
                    localData.y-spriteWidth/2-4*attack.getHeight()/3,
                    attack.getWidth()*4,attack.getWidth()*4);
        else
            attack.draw(localData.x-spriteHeight/2-96,
                    localData.y-spriteWidth/2-96,
                    attack.getWidth()*4,attack.getWidth()*4);
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