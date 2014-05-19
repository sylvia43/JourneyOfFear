package game.npc.quest;

import java.util.HashMap;
import java.util.Map;

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
    
    public QuestSequence update() {
        currentStage = stages.get(currentStage).update();
        return this;
    }
    
    public void addStage(int pos, QuestStage stage) {
        stages.put(pos,stage);
    }
    
    public boolean isComplete() {
        return (currentStage + 1) == stages.size();
    }
    
    @Override
    public String toString() {
        return stages.get(currentStage).getQuest().toString();
    }
    
    public QuestSequence accept() {
        accepted = true;
        return this;
    }
    
    // Dialogue needs to be stored here too.
}
