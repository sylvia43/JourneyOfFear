package game.environment;

import game.enemy.Enemy;
import game.player.Player;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Tree extends Obstacle {

    protected Player player;
    protected int attackId = 0;
    protected boolean wasClosed = true;

    public Tree(Player player) throws SlickException {
        super();
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }

    public Tree(Player player,int x,int y) throws SlickException {
        super(x,y);
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }

    public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }

    public void render(GameContainer container,Graphics g) throws SlickException {
        sprite.draw(x,y,256,192);
        mask.render(g);
    }

    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.TREE_LARGE.getAnim();
        mask = new Rectangle(x,y,x+256,x+192);
        sprite.setDuration(0,1000);
    }
    
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
