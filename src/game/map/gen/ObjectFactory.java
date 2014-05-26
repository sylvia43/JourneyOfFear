package game.map.gen;

import game.enemy.Enemy;
import game.environment.hazard.Spikes;
import game.environment.obstacle.Obstacle;
import game.environment.obstacle.Tree;
import game.environment.spawner.GreenSlimeSpawner;
import game.environment.spawner.PinkSlimeSpawner;
import game.npc.NPC;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;

/** Generates Enemies, NPCs, and Obstacles.  */
public class ObjectFactory {
    
    private ObjectFactoryType type;
    
    public ObjectFactory(ObjectFactoryType type) {
        this.type = type;
    }
    
    public List<Enemy> getEnemies(Player p) {
        List<Enemy> enemies = new ArrayList<Enemy>();
        
        for (int i=0;i<type.enemies;i++) {
            int difficulty = type.difficulty+(int)(Math.random()*7-3);
            enemies.add(Enemy.getEnemyByDifficulty(difficulty,p));
        }
        
        return enemies;
    }
    
    public List<NPC> getNPCS() {
        List<NPC> npcs = new ArrayList<NPC>();
                    
        for (int i=0;i<type.npcs;i++) {
            npcs.add(new NPC());
        }
        
        return npcs;
    }
    
    public List<Obstacle> getObstacles(Player player, List<Enemy> enemies) {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();
        
        if (!type.obstacles)
            return obstacles;
        
        obstacles.add(new Spikes(player,enemies));           
        obstacles.add(new GreenSlimeSpawner(player,enemies));
        obstacles.add(new PinkSlimeSpawner(player,enemies));
        obstacles.add(new Tree());
        
        return obstacles;
    }
}
