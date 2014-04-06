package game.environment;

import game.enemy.EnemySmartBlob;
import game.map.Area;
import game.player.Player;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.GameContainer;

public class GreenSlimePit extends Obstacle {
    
    public GreenSlimePit(Player player) {
        super();
        this.player = player;
        this.x = (int) (Math.random() * StateMultiplayer.WORLD_SIZE_X);
        this.y = (int) (Math.random() * StateMultiplayer.WORLD_SIZE_Y);
    }
    
    @Override
    public void update(GameContainer container, int delta, Area currentArea) {
        if (currentArea.getEnemies().size()>25)
            return;
        
        if ((int)(Math.random()*3000) == 0)
            currentArea.addEnemy(new EnemySmartBlob(player),x,y).init(container);
    }

    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.GREEN_SLIME_PIT.getAnim();
        mask = createMask();
        this.sprite.setDuration(0, 1000);
    }
}
