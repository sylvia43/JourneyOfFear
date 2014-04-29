package game.environment.spawner;

import game.enemy.Enemy;
import game.environment.hazard.Hazard;
import game.map.Area;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.Color;

public class Spawner extends Hazard {
    
    protected int chance;
    
    private int timer = 0;
    
    public Spawner(Player player, ArrayList<Enemy> enemies, int chance) {
        super(player,enemies);
        this.chance = chance;
        this.minimapColor = Color.magenta;
    }
    
    public Spawner(Player player, ArrayList<Enemy> enemies, int x, int y, int chance) {
        super(player,enemies,x,y);
        this.chance = chance;
    }
    
    @Override
    public void update(int delta, Area currentArea) {
        super.update(delta,currentArea);
        timer++;
        if (timer>1000.0/delta) {
            attackId++;
            timer = 0;
        }
        if ((int)(Math.random()*chance) == 0)
            currentArea.addEnemy(getSpawnedEnemy()).init();
    }
    
    protected Enemy getSpawnedEnemy() { return null; }
}
