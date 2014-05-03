package game.npc;

import game.npc.npcutils.QuestSequence;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.util.resource.AnimationLibrary;
import java.util.List;
import org.newdawn.slick.Animation;

public class QuestNPC extends NPC {
    
    private List<QuestSequence> quests;
    
    @Override
    public void update(int delta) {
        
    }
    
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
