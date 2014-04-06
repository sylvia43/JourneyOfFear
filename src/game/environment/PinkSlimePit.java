package game.environment;

import game.enemy.Enemy;
import game.enemy.EnemyBlob;
import game.player.Player;
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
        mask = createMask();
        this.sprite.setDuration(0,1000);
    }
    
    protected Enemy getSpawnedEnemy() {
        return new EnemyBlob(player);
    }
}
