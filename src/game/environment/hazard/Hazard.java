package game.environment.hazard;

import game.enemy.Enemy;
import game.environment.obstacle.Obstacle;
import game.map.Area;
import game.player.Player;
import game.state.StateSingleplayer;
import java.util.List;

public abstract class Hazard extends Obstacle {
    
    protected List<Enemy> enemies;
    protected Player player;
    
    protected int attackId = 0;
    
    public Hazard(Player player, List<Enemy> enemies) {
        this(player,enemies,(int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)));
    }
    
    public Hazard(Player player, List<Enemy> enemies, int x, int y) {
        super(x,y);
        this.player = player;
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