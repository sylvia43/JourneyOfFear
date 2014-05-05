package game.npc.npcutils;

import game.enemy.EnemyType;
import game.npc.NPC;
import game.npc.quest.KillQuest;
import game.npc.quest.QuestSequence;
import game.npc.quest.QuestStage;
import game.npc.quest.TalkQuest;

public class QuestGenerator {
    
    public static QuestSequence generateQuest(NPC npc) {
        QuestSequence quest = new QuestSequence();
        
        KillQuest killQuest = new KillQuest(EnemyType.BLOB,5);
        TalkQuest talkQuest = new TalkQuest(npc);
        
        quest.addStage(0,new QuestStage(killQuest,0,1));
        quest.addStage(1,new QuestStage(talkQuest,1,2));
        
        return quest;
    }
}
