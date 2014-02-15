package game.environment;

import game.player.Player;
import game.state.StatePlaying;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.SlickException;

public class Spikes extends Hazard {
    
protected Player player;

    public Spikes(Player player) {
        super();  
        this.player = player;
        this.x = (int)(Math.random()*StatePlaying.WORLD_SIZE_X);
        this.y = (int)(Math.random()*StatePlaying.WORLD_SIZE_Y);
    }
    
    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.SPIKES.getAnim();
        mask = createMask();
        this.sprite.setDuration(0,1000);
    }
 
    @Override
    protected void resolveCollision() {
        if (sprite.getFrame() != 0 && getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY()))
            player.resolveHit(x,y);
    }
}
