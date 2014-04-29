package game.environment.hazard;

import game.enemy.Enemy;
import game.environment.obstacle.Obstacle;
import game.map.Area;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;

public class Hazard extends Obstacle {
    
    protected List<Enemy> enemies;
    protected Player player;
    
    protected int attackId = 0;
    
    public Hazard(Player player, ArrayList<Enemy> enemies) {
        super();
        this.minimapColor = Color.red;
        this.enemies = enemies;
        this.player = player;
    }
    
    public Hazard(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(x,y);
        this.enemies = enemies;
    }
    
    @Override
    public void update(int delta, Area currentArea) {
        resolveCollision();
    }
    
    protected void resolveCollision() {
        if (mask.intersects(player.getCollisionMask()))
            player.resolveHit(x+64,y+64,2);
        for (Enemy e : enemies) {
            if (mask.intersects(e.getCollisionMask())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}