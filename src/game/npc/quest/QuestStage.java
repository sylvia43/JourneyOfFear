package game.npc.quest;

public class QuestStage {
    
    private Quest quest;
    private int currentPos;
    private int nextPos;
    
    public QuestStage(Quest quest, int currentPos, int nextPos) {
        this.quest = quest;
        this.currentPos = currentPos;
        this.nextPos = nextPos;
    }
    
    public int update() {
        return quest.isComplete() ? nextPos : currentPos;
    }
    
    public Quest getQuest() {
        return quest;
    }
}
