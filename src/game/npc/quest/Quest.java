package game.npc.quest;

/** A task (usually given by a NPC) for reward. */
public abstract class Quest {
        
    public abstract boolean isComplete();
    public abstract String getMessage();
    
    @Override
    public String toString() {
        return getMessage();
    }
}
