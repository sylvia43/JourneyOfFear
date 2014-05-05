package game.npc;

<<<<<<< HEAD
<<<<<<< HEAD
import game.npc.utils.QuestSequence;
=======
=======
import game.npc.npcutils.QuestGenerator;
>>>>>>> 5610d9b... Need to figure out how to implement this with lowest debt...
import game.npc.quest.QuestSequence;
>>>>>>> 6c71f03... Working on Quest. No use of the word 'Factory' allowed.
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.state.StateSingleplayer;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;

public class QuestNPC extends NPC {
    
    private List<QuestSequence> quests;
    
    public QuestNPC() {
        this((int)(Math.random()*StateSingleplayer.WORLD_SIZE_X),
                (int)(Math.random()*StateSingleplayer.WORLD_SIZE_Y));
    }
    
    public QuestNPC(int x, int y) {
        super(x,y);
        quests = new ArrayList<QuestSequence>();
        quests.add(QuestGenerator.generateQuest(this));
    }
    
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
