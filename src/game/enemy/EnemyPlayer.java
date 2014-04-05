package game.enemy;

import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EnemyPlayer {
    
    private int x;
    private int y;
    private int client;
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getClient() { return client; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    private Animation anim = AnimationLibrary.ENEMY_PLAYER_PLACEHOLDER.getAnim();
    
    public EnemyPlayer(int x, int y, int client) {
        this.x = x;
        this.y = y;
        this.client = client;
    }
    
    public void render(GameContainer container, Graphics g) {
        anim.setCurrentFrame(0);
        anim.draw(x,y,64,64);
    }
}