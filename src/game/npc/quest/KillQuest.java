package game.npc.quest;

import game.enemy.Enemy;
import game.enemy.EnemyType;

public class KillQuest extends Quest {
    
    private EnemyType type;
    private int num;
    
    public KillQuest(EnemyType type, int num) {
        this.type = type;
        this.num = num;
    }
    
    public String update(Enemy e) {
        if (e.isType(type))
            num--;
        return num + " " + type.getName(num) + " remaining.";
    }
    
    @Override
    public boolean isComplete() {
        return num <= 0;
    }

    @Override
    public String getMessage() {
        return "Kill " + num + " " + type.getName(num);
    }
}
