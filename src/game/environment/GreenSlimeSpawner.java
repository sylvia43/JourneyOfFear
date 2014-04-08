package game.environment;

import game.enemy.Enemy;
import game.enemy.EnemyGreenSlime;
import game.enemy.EnemySlime;
import game.player.Player;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;

public class GreenSlimeSpawner extends Spawner {
    
    public GreenSlimeSpawner(Player player, ArrayList<Enemy> enemies) {
        super(player,enemies,3000);
    }
    
    public GreenSlimeSpawner(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y,3000);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.GREEN_SLIME_PIT.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
        this.sprite.setDuration(0,1000);
    }
    
    protected Enemy getSpawnedEnemy() {
        return new EnemyGreenSlime(player);
    }
    
    protected void resolveCollision() {
        if (mask.intersects(player.getCollisionMask()))
            player.resolveHit(x+64,y+64,2);
        for (Enemy e : enemies) {
            if (e instanceof EnemySlime)
                continue;
            if (mask.intersects(e.getCollisionMask())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
