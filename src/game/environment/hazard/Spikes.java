package game.environment.hazard;

import game.enemy.Enemy;
import game.player.Player;
import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class Spikes extends Hazard {
    
    protected boolean wasClosed = true;
    
    public Spikes(Player player, ArrayList<Enemy> enemies) {
        super(player,enemies);
        miniWidth = 6;
        miniHeight = 6;
    }
    
    public Spikes(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y);
        miniWidth = 6;
        miniHeight = 6;
    }
    
    @Override
    public void render(Graphics g) {
        sprite.draw(x,y,128,156);
        mask.render(g);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.SPIKES.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
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
        if (mask.intersects(player.getCollisionMask()))
            player.resolveHit(x+64,y+64,5);
        for (Enemy e : enemies) {
            if (mask.intersects(e.getCollisionMask())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
