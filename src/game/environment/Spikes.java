package game.environment;

import game.enemy.Enemy;
import game.player.Player;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Spikes extends Hazard {
    
    protected Player player;
    protected int attackId = 0;
    protected boolean wasClosed = true;
    
    public Spikes(Player player) {
        super();  
        this.player = player;
    }
    
    public Spikes(Player player, int x, int y) {
        super(x,y);
        this.player = player;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,92);
    }
    
    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.SPIKES.getAnim();
        mask = createMask();
        sprite.setDuration(0,1000);
    }
  
    @Override
    protected void resolveCollision() {
        if (sprite.getFrame() == 0) {
            wasClosed = true;
            return;
        }
        if (wasClosed) {
            wasClosed = false;
            attackId++;
        }
        if (getCollisionMask().intersects(player.getCollisionMask(),x,y,player.getX(),player.getY()))
            player.resolveHit(x,y,2);
        for (Enemy e : enemies) {
            if (getCollisionMask().intersects(e.getCollisionMask(),x,y,e.getX(),e.getY())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
