package game.environment;

import game.enemy.Enemy;
import game.map.Area;
import game.player.Player;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import org.newdawn.slick.GameContainer;

public class Spawner extends Hazard {
    
    protected int chance;
    
    private int timer = 0;
    
    public Spawner(Player player, ArrayList<Enemy> enemies, int chance) {
        super(player,enemies);
        this.chance = chance;
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

    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.GREEN_SLIME_PIT.getAnim();
        mask = createMask();
        this.sprite.setDuration(0, 1000);
    }

    protected Enemy getSpawnedEnemy() { return null; }
    
    @Override
    protected void resolveCollision() {
        if (mask.intersects(player.getCollisionMask(),player.getX(),player.getY()))
            player.resolveHit(x+64,y+64,2);
        for (Enemy e : enemies) {
            if (mask.intersects(e.getCollisionMask(),e.getX(),e.getY())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
