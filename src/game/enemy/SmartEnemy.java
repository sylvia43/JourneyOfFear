package game.enemy;

import game.ability.Ability;
import game.ability.AbilityDodge;
import game.player.Player;

public abstract class SmartEnemy extends AttackingEnemy {
    
    private Ability a;
    
    public SmartEnemy(Player player) {
        super(player);
        a = new AbilityDodge();
    }
    
    public abstract void beSmart(int delta);
    
    @Override
    public void update(int delta) {
        super.update(delta);
        beSmart(delta);
    }
    
    protected void avoidAttacks(int delta) {
        if(player.getAttack().isAttacking() &&
                Math.sqrt((x-player.getX())*(x-player.getX())+(y-player.getY())*(y-player.getY())) <= 192)
            a.use(x-player.getX(),y-player.getY());
        a.update(delta,this,stunTimer>0);
    }
}
