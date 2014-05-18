package game.npc.quest;

import java.util.HashMap;
import java.util.Map;

public class QuestSequence {
    
    private Map<Integer,QuestStage> stages;
    private int currentStage;
    
    public QuestSequence() {
        stages = new HashMap<Integer,QuestStage>();
        currentStage = 0;
    }
    
    public void update() {
        currentStage = stages.get(currentStage).update();
    }
    
    public void addStage(int pos, QuestStage stage) {
        stages.put(pos,stage);
    }
    
    @Override
    public String toString() {
        return stages.get(currentStage).getQuest().toString();
    }
    
    // Dialogue needs to be stored here too.
}
