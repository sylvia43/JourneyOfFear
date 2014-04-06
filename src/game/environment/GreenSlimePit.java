package game.environment;

import game.enemy.Enemy;
import game.enemy.EnemySmartBlob;
import game.player.Player;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;

public class GreenSlimePit extends Spawner {
    
    public GreenSlimePit(Player player, ArrayList<Enemy> enemies) {
        super(player,enemies,3000);
    }
    
    public GreenSlimePit(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y,3000);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.GREEN_SLIME_PIT.getAnim();
        mask = new ImageMask(sprite.getImage(0));
        this.sprite.setDuration(0,1000);
    }
    
    protected Enemy getSpawnedEnemy() {
        return new EnemySmartBlob(player);
    }
    
    protected void resolveCollision() {
        if (mask.intersects(player.getCollisionMask(),x,y,player.getX(),player.getY()))
            player.resolveHit(x+64,y+64,2);
        for (Enemy e : enemies) {
            if (e instanceof EnemySmartBlob)
                continue;
            if (mask.intersects(e.getCollisionMask(),x,y,e.getX(),e.getY())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
