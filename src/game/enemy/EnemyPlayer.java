package game.enemy;

import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class EnemyPlayer {
    
    public int x;
    public int y;
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        Animation anim = AnimationLibrary.PLAYER_DOWN.getAnim();
        anim.setCurrentFrame(1);
        anim.draw(x,y,64,64);
    }
}