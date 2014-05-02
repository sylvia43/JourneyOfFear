package game.enemy.blob;

import game.enemy.Enemy;
import game.player.Player;
import game.sprite.EntitySprite;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

public class EnemyRedBlob extends Enemy implements EnemyBlob {
    
    public EnemyRedBlob(Player player) {
        super(player);
        speed = 0.0625;
        health = 15;
        minimapColor = new Color(255,128,128);
    }
    
    @Override
    public String getName() { return "Red Blob"; }
    
    @Override
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.BLOB_RED_RIGHT.getAnim(),
            AnimationLibrary.BLOB_RED_UP.getAnim(),
            AnimationLibrary.BLOB_RED_LEFT.getAnim(),
            AnimationLibrary.BLOB_RED_DOWN.getAnim(),
        };
        sprite.setAnimations(animList);
        initializeMask();
    }
    
    @Override
    public void move(int delta) {
        if (stunTimer>0) {
            sprite.getAnim(spritePointer).setCurrentFrame(0);
            sprite.getAnim(spritePointer).stop();
            x+=(knockbackDX*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            y+=(knockbackDY*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            return;
        }
        
        sprite.getAnim(spritePointer).start();
        
        if (Math.random()*200<1)
            spritePointer = (int)(Math.random()*4);
        
        int dx = 0;
        int dy = 0;
        
        if (spritePointer == 0)
            dx+=speed*delta;
        else if (spritePointer == 1)
            dy-=speed*delta;
        else if (spritePointer == 2)
            dx-=speed*delta;
        else if (spritePointer == 3)
            dy+=speed*delta;
        
        x += dx;
        y += dy;
    }
}
