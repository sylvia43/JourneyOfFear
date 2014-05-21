package game.npc.quest;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all the QuestStages making up a quest.
 * 
 * This should be a linked list... :/
 */
public class QuestSequence {
    
    private Map<Integer,QuestStage> stages;
    private int currentStage;
    private boolean accepted;
    
    public boolean isAccepted() { return accepted; }
    
    public QuestSequence() {
        stages = new HashMap<Integer,QuestStage>();
        currentStage = 0;
        accepted = false;
    }
    
    public QuestStage getCurrentStage() {
        return stages.get(currentStage);
    }
    
    /** Updates the current stage and sets the new current stage. */
    public void update() {
        currentStage = stages.get(currentStage).update();
    }
    
    @Override
    public String toString() {
        return stages.get(currentStage).getQuest().toString();
    }
    
    /** Called by NPC when Player accepts quest. */
    public void accept() {
        accepted = true;
    }
    
    /** Adds a stage to the quest. Mostly for creation.
     * @param pos Position to add the stage to.
     * @param stage Stage to add.
     */
    public void addStage(int pos, QuestStage stage) {
        stages.put(pos,stage);
    }
    /** @return If the quest is completed. */
    public boolean isComplete() {
        return (currentStage + 1) == stages.size();
    }
    
    // Dialogue needs to be stored here too.
}
