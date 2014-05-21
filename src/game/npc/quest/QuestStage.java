package game.npc.quest;

/** Stores a Quest and the index of the next stage in the sequence. */
public class QuestStage {
    
    private Quest quest;
    private int currentPos;
    private int nextPos;
    
    public QuestStage(Quest quest, int currentPos, int nextPos) {
        this.quest = quest;
        this.currentPos = currentPos;
        this.nextPos = nextPos;
    }
    
    /** @return The position of the correct QuestStage (this or next if done). */
    public int update() {
        return quest.isComplete() ? nextPos : currentPos;
    }
    
    public Quest getQuest() {
        return quest;
    }
}
