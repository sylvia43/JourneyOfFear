package game.player.attack;

import game.enemy.Enemy;
import game.sprite.Hittable;
import game.sprite.Rectangle;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public abstract class Attack {
    
    protected List<Enemy> enemies;
    
    protected Animation sword;
    
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public abstract Rectangle getMask(int x, int y);
    public abstract void init();
    public abstract void render(int x, int y);
    public abstract boolean canAttack();
    public abstract void attack(int direction, boolean sound);
    public abstract void resolveAttack(int delta, int x, int y);
    public abstract void resolveAttackHit(Hittable other, int x, int y, int ox, int oy);
    public abstract void renderMask(int x, int y, Graphics g);
    public abstract void renderDebugInfo(int camX, int camY, Graphics g);
}