package game.environment;

import game.enemy.EnemySmartBlob;
import game.map.Area;
import game.player.Player;
import game.state.StatePlaying;
import game.util.resource.AnimationLibrary;
import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class GreenSlimePit extends Hazard {

    protected Player player;
    protected int attackId = 0;
    protected boolean wasClosed = true;

    public GreenSlimePit(Player player) {
        super();
        this.player = player;
        this.x = (int) (Math.random() * StatePlaying.WORLD_SIZE_X);
        this.y = (int) (Math.random() * StatePlaying.WORLD_SIZE_Y);
    }

    public void update(GameContainer container, int delta, Area currentArea) {
        Random r = new Random();
        int randnum = r.nextInt(5000);
        if (randnum == 0) {
            try {
                currentArea.addEnemy(new EnemySmartBlob(player), x, y).init(container);
            } catch (SlickException ex) {

            }
        }
    }

    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.GREEN_SLIME_PIT.getAnim();
        mask = createMask();
        this.sprite.setDuration(0, 1000);
    }

    @Override
    protected void resolveCollision() {

    }
}
