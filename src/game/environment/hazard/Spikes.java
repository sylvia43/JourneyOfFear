package game.environment.hazard;

import game.enemy.Enemy;
import game.player.Player;
import game.sprite.ImageMask;
import game.state.StateSingleplayer;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;

public class Spikes extends Hazard {
    
    protected boolean wasClosed = true;
    
    private int yOffset = 12;
    
    @Override public int getDepth() { return 0; }
    
    public Spikes(Player player, ArrayList<Enemy> enemies) {
        this(player,enemies,(int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateSingleplayer.WORLD_SIZE_Y)));
    }
    
    public Spikes(Player player, ArrayList<Enemy> enemies, int x, int y) {
        super(player,enemies,x,y);
        miniWidth = 6;
        miniHeight = 6;
        mask.update(x-spriteWidth/2,y-spriteHeight/2-yOffset);
    }
    
    @Override
    public void render(Graphics g) {
        sprite.draw(x-spriteWidth/2,y-spriteHeight/2-yOffset,128,156);
        mask.render(g);
        if (StateSingleplayer.DEBUG_COLLISION) {
            g.drawRect(x-8,y-8,16,16);
        }
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.SPIKES.getAnim();
        spriteWidth = sprite.getImage(0).getWidth()*4;
        spriteHeight = sprite.getImage(0).getHeight()*4;
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
            player.resolveHit(x,y,5);
        for (Enemy e : enemies) {
            if (mask.intersects(e.getCollisionMask())) {
                e.resolveHit(x,y,attackId,2);
            }
        }
    }
}
