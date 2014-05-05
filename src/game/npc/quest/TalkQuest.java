package game.npc.quest;

import game.npc.NPC;

public class TalkQuest extends Quest {
    
    private NPC target;
    private boolean complete;
    
    public TalkQuest(NPC target) {
        this.target = target;
    }
    
    // Called by NPC, this object is passed by Player on conversing.
    public void update() {
        complete = true;
    }
    
    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public String getMessage() {
        return "Talk to " + target.getName() + ".";
    }
}
