package game.enemy;

import game.network.server.EnemyPlayerData;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/** Wrapper around EnemyPlayerData for client to render. */
public class EnemyPlayer {
    
    public EnemyPlayerData data;
    
    public EnemyPlayer(EnemyPlayerData data) {
        this.data = data;
    }
    
    public void render(GameContainer container, Graphics g) {
        Animation anim = AnimationLibrary.ENEMY_PLAYER_PLACEHOLDER.getAnim();
        anim.setCurrentFrame(0);
        anim.draw(data.x,data.y,64,64);
    }
}