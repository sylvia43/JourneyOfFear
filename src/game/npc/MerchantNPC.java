package game.npc;

import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;

public class MerchantNPC extends NPC {
    
    @Override
    protected void initializeSprite() {
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
}
