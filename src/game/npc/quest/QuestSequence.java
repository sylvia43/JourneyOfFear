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
    
    public String update() {
        currentStage = stages.get(currentStage).update();
        return stages.get(currentStage).getStatus();
    }
    
    public void addStage(int pos, QuestStage stage) {
        stages.put(pos,stage);
    }
    
    // Dialogue needs to be stored here too.
}
