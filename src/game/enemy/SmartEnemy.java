package game.enemy;

import game.player.Player;
import org.newdawn.slick.GameContainer;

public abstract class SmartEnemy extends AttackingEnemy {

    public SmartEnemy(Player player) {
        super(player);
    }
    
    @Override
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        avoidAttacks(delta);
        move(delta);
        resolveAttack(delta);
        isHit = false;
    }
    
    protected void avoidAttacks(int delta) {
        if(player.getAttack().isAttacking() && !isHit &&
                Math.sqrt(Math.pow(x-player.getX(),2)+Math.pow(y-player.getY(), 2)) <= 192)
            initializeKnockback(x-player.getX(), y-player.getY(), 0.5);
    }
    
    protected void avoidObjects(int delta) {
        //To be implemented...
    }
}
