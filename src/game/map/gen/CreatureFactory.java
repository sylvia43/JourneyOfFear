package game.map.gen;

import game.enemy.Enemy;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;

public class CreatureFactory {
    
    private CreatureFactoryType type;
    
    public CreatureFactory(CreatureFactoryType type) {
        this.type = type;
    }
    
    public List<Enemy> getEnemies(Player p) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        
        for (int i=0;i<type.enemies;i++) {
            
        }
        
        return enemies;
    }
}
