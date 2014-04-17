package game.enemy;

import game.player.Player;
import org.newdawn.slick.GameContainer;

public abstract class SmartEnemy extends AttackingEnemy {
    
    protected int runawayTimer = 0;

    public SmartEnemy(Player player) {
        super(player);
    }
    
    @Override
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        avoidAttacks(delta);
        move(delta);
        resolveAttack(delta);
    }
    
    protected void avoidAttacks(int delta) {
        if(health < 10 && player.getAttack().isAttacking() &&
                !attack.isAttacking() && runawayTimer == 0 &&
                Math.sqrt(Math.pow(x-player.getX(),2)+Math.pow(y-player.getY(), 2)) <= 160) {
            initializeKnockback(x-player.getX(), y-player.getY(), 0.5);
            runawayTimer = 1000;
        }
        else if(runawayTimer > 0)
            runawayTimer = Math.max(runawayTimer - delta, 0);
    }
    
    protected void avoidObjects(int delta) {
        //To be implemented...
    }
}
