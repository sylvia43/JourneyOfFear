package game.environment;

import game.enemy.Enemy;
import game.enemy.EnemyBlob;
import game.player.Player;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;

public class PinkSlimePit extends Spawner {
    
    public PinkSlimePit(Player player, ArrayList<Enemy> enemies) {
        super(player,enemies,3000);
    }
    
    public PinkSlimePit(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y,3000);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.PINK_SLIME_PIT.getAnim();
        mask = new ImageMask(sprite.getImage(0));
        this.sprite.setDuration(0,1000);
    }
    
    protected Enemy getSpawnedEnemy() {
        return new EnemyBlob(player);
    }
    
    protected void resolveCollision() {
        if (mask.intersects(player.getCollisionMask(),x,y,player.getX(),player.getY()))
            player.resolveHit(x+64,y+64,2);
        for (Enemy e : enemies) {
            if (e instanceof EnemyBlob)
                continue;
            if (mask.intersects(e.getCollisionMask(),x,y,e.getX(),e.getY())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
