package game.environment;

import game.enemy.Enemy;
import game.player.Player;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Spikes extends Hazard {
    
    protected Player player;
    protected int attackId = 0;
    protected boolean wasClosed = true;
    protected Rectangle mask;
    
    public Spikes(Player player) {
        super();  
        this.player = player;
        miniWidth = 6;
        miniHeight = 6;
    }
    
    public Spikes(Player player, int x, int y) {
        super(x,y);
        this.player = player;
        miniWidth = 6;
        miniHeight = 6;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,128,156);
    }
    
    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.SPIKES.getAnim();
        mask = new Rectangle(x,y,x+sprite.getImage(0).getWidth()*4,y+sprite.getImage(0).getHeight()*4);
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
        if (mask.intersects(player.getCollisionMask(),player.getX(),player.getY()))
            player.resolveHit(x+32,y+32,2);
        for (Enemy e : enemies) {
            if (mask.intersects(e.getCollisionMask(),e.getX(),e.getY())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
