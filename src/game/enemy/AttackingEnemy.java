package game.enemy;

import game.player.Player;
import game.player.attack.Attack;
import org.newdawn.slick.Graphics;

public abstract class AttackingEnemy extends Enemy {
    
    protected Attack attack;
    
    public AttackingEnemy(Player player) {
        super(player);
    }
    
    @Override
    public void init() {
        super.init();
        initializeAttack();
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
        resolveAttack(delta);
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        renderAttack();
    }
    
    protected abstract void initializeAttack();
    protected abstract void resolveAttack(int delta);
    protected abstract void renderAttack();
}
