package game.environment;

import game.player.Player;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Tree extends Obstacle {
    
    public Tree(Player player) throws SlickException {
        super();
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }

    public Tree(Player player, int x, int y) throws SlickException {
        super(x,y);
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }
    
    @Override
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
        
    }
}
