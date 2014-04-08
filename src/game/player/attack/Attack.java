package game.player.attack;

import game.enemy.Enemy;
import game.sprite.Rectangle;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class Attack {
    
    protected List<Enemy> enemies;
    
    protected Animation sword;
    
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public abstract Rectangle getMask(int x, int y);
    public abstract void init();
    public abstract void render(int x, int y);
    public abstract void resolveAttack(Input input, int delta, int x, int y, boolean canAttack);
    public abstract void renderMask(int x, int y, Graphics g);
    public abstract void renderDebugInfo(int camX, int camY, Graphics g);
}