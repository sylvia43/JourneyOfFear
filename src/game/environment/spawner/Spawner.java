package game.environment.spawner;

import game.enemy.Enemy;
import game.environment.hazard.Hazard;
import game.map.Area;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

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
    public void update(GameContainer container, int delta, Area currentArea) {
        super.update(container,delta,currentArea);
        timer++;
        if (timer>1000.0/delta) {
            attackId++;
            timer = 0;
        }
        if ((int)(Math.random()*chance) == 0)
            currentArea.addEnemy(getSpawnedEnemy(),x,y).init(container);
    }
    
    protected Enemy getSpawnedEnemy() { return null; }
}
