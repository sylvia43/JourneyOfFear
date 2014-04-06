package game.environment;

import game.enemy.Enemy;
import game.enemy.EnemyBlob;
import game.map.Area;
import game.player.Player;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;

public class PinkSlimePit extends Hazard {

    public PinkSlimePit(Player player, ArrayList<Enemy> enemies) {
        super(player,enemies);
    }
    
    public PinkSlimePit(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y);
    }
    
    @Override
    public void update(GameContainer container, int delta, Area currentArea) {
        if (currentArea.getEnemies().size()>25)
            return;
        
        if ((int)(Math.random()*3000) == 0)
            currentArea.addEnemy(new EnemyBlob(player),x,y).init(container);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.PINK_SLIME_PIT.getAnim();
        mask = createMask();
        this.sprite.setDuration(0, 1000);
    }
}
