package game.npc.quest;

public abstract class Quest {
        
    public abstract boolean isComplete();
    public abstract String getMessage();
    
    @Override
    public String toString() {
        return "[" + (isComplete()?"x":" ") + "] " + getMessage();
    }
}
