package game.environment;

import game.player.Player;
import game.sprite.EntitySprite;
import game.state.StatePlaying;
import game.util.resource.AnimationLibrary;
import game.util.resource.SoundLibrary;
import game.util.resource.SoundPlayer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Spikes extends Hazards {
    
 protected Player player;

    public Spikes(Player player) {
        super();
        this.player=player;
        this.x=(int)(Math.random()*StatePlaying.WORLD_SIZE_X);
        this.y=(int)(Math.random()*StatePlaying.WORLD_SIZE_Y);
        this.animationSpeed = 500;
    }
    
    @Override
    protected void initializeVariables() {
        spritePointer = 3;
    }
    
    @Override
    protected void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.SPIKES.getAnim(),
           
        };
        sprite.setAnimations(animList);
        initializeMask();
    }
    
 
    @Override
    protected void resolveCollision() {
        boolean isHit = getCollisionMask()
                .intersects(player.getCollisionMask(),x,y,player.getX(),player.getY());
        if (isHit)
            player.resolveHit(x,y);
    }
 
    
    @Override
    protected void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("x: " + String.valueOf(x),10+x+64,38+y+64);
        g.drawString("y: " + String.valueOf(y),10+x+64,52+y+64);
        
        if (StatePlaying.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
        }
    }
}
