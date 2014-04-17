package game.enemy;

import game.player.Player;
import game.player.attack.Attack;
import game.state.StateMultiplayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class AttackingEnemy extends Enemy {
    
    protected Attack attack;
    
    protected static final int ATTACK_SPEED = 10;
    protected static final int SWORD_DELAY = 800;
    
    public AttackingEnemy(Player player) {
        super(player);
    }
    
    //Game loop methods
    @Override
    public void init(GameContainer container) {
        initializeVariables();
        initializeSprite();
        initializeAttack();
    }
    
    @Override
    public void update(GameContainer container, int delta) {
        resolveInvulnerability(delta);
        move(delta);
        resolveAttack(delta);
        isHit = false;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) {
        sprite.getAnim(spritePointer).draw(x,y,64,64);
        renderAttack();
        if (StateMultiplayer.DEBUG_MODE)
            renderDebugInfo(g);
    }
    
    protected abstract void initializeAttack();
    protected abstract void resolveAttack(int delta);
    protected abstract void renderAttack();
}
