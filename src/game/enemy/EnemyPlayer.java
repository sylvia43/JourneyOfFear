package game.enemy;

import game.network.server.EnemyPlayerData;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class EnemyPlayer extends EnemyPlayerData {
    
    private Animation anim = null;
    
    public EnemyPlayer(int id, int x, int y) {
        super(x,y,id);
    }
    
    public void render(GameContainer container, Graphics g) {
        if (anim == null)
             anim = AnimationLibrary.ENEMY_PLAYER_PLACEHOLDER.getAnim();
        anim.setCurrentFrame(0);
        anim.draw(x,y,64,64);
    }
}