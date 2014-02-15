package game.environment;

import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Spikes extends Hazards {
    
    public Spikes(Player player){
        super(player);
    }
    
    
    protected void testCollision(){
        collisionHit = player.getCollisionMask().intersects(getCollisionMask(),player.getX(),player.getY());
        if (collisionHit)
            player.resolveHit(x,y);
    }
    public Rectangle getCollisionMask() {
        
        return new Rectangle(x,y,x+64,y+64);
        
    }
}

