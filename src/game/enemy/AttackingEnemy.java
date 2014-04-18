package game.enemy;

import game.player.Player;
import game.player.attack.Attack;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class AttackingEnemy extends Enemy {
    
    protected Attack attack;
    
    public AttackingEnemy(Player player) {
        super(player);
    }
    
    @Override
    public void init(GameContainer container) {
        super.init(container);
        initializeAttack();
    }
    
    @Override
    public void update(GameContainer container, int delta) {
        super.update(container,delta);
        resolveAttack(delta);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) {
        super.render(container,g);
        renderAttack();
    }
    
    protected abstract void initializeAttack();
    protected abstract void resolveAttack(int delta);
    protected abstract void renderAttack();
}
