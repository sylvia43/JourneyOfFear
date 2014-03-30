package game.enemy;

import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyPlayer {
    
    public int x;
    public int y;
    public int client;
    
    public EnemyPlayer(int x, int y, int client) {
        this.x = x;
        this.y = y;
        this.client = client;
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        Animation anim = AnimationLibrary.ENEMY_PLAYER_PLACEHOLDER.getAnim();
        anim.setCurrentFrame(0);
        anim.draw(x,y,64,64);
    }
}