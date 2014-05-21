package game.npc.quest;

import game.npc.NPC;

/** Quest to converse with an NPC. */
public class TalkQuest extends Quest {
    
    private NPC target;
    private boolean complete;
    
    public TalkQuest(NPC target) {
        this.target = target;
    }
    
    /**
     * This TalkQuest is passed by Player on conversing with an NPC. The NPC
     * calls this method.
     */
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
