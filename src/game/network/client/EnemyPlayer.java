package game.network.client;

import game.network.server.EnemyPlayerData;
import game.player.attack.Attack;
import game.player.attack.AttackAxeCleave;
import game.player.attack.AttackDaggerSlash;
import game.player.attack.AttackSwordSlash;
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
    
    private List<Attack> attacks;
    private Attack attack;
    
    public EnemyPlayer(EnemyPlayerData newData) {
        data = newData;
        attacks = new ArrayList<Attack>();
        attacks.add(AttackAxeCleave.create());
        attacks.add(AttackSwordSlash.create());
        attacks.add(AttackDaggerSlash.create());
        attack = attacks.get(data.weapType);
    }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(data.dir)
                .getImageMask(sprite.getAnim(data.dir).getFrame()).update(data.x-spriteWidth/2,data.y-spriteHeight/2);
    }

    public ImageMask getAttackMask() {
        return null;//attack.getMask(data.x-spriteWidth/2,data.y-spriteHeight/2);
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
        
        if (localData.weapFrame == -1)
            return;
        
        
        attack = attacks.get(localData.weapType);
        
        if (attacks.get(localData.weapType).anim == null) {
            attack.init();
        }
        
        attack.attacking = true;
        
        attack = attacks.get(localData.weapType);
        attack.anim.stop();
        attack.anim.setCurrentFrame(localData.weapFrame);
        
        attack.render(localData.x-spriteWidth/2,localData.y-spriteHeight/2);
        
        /*
        if (localData.weapType != 0)
            attack.anim.draw(localData.x-spriteHeight/2-4*attack.anim.getWidth()/3,
                    localData.y-spriteWidth/2-4*attack.anim.getHeight()/3,
                    attack.anim.getWidth()*4,attack.anim.getWidth()*4);
        else
            attack.anim.draw(localData.x-spriteHeight/2-96,
                    localData.y-spriteWidth/2-96,
                    attack.anim.getWidth()*4,attack.anim.getWidth()*4);
        */
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