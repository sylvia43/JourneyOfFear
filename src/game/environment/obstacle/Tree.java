package game.environment.obstacle;

import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Tree extends SolidObstacle {
    
    private Rectangle collisionMask;
    
    public Tree() {
        super();
        miniWidth = 3;
        miniHeight = 6;
        collisionMask = new Rectangle(x+108,y+108,x+256-108,y+148);
    }

    public Tree(int x, int y) {
        super(x,y);
        miniWidth = 3;
        miniHeight = 6;
        collisionMask = new Rectangle(x+108,y+108,x+256-108,y+148);
    }
    
    @Override
    public void render(GameContainer container, Graphics g) {
        sprite.draw(x,y,256,192);
        g.setColor(Color.cyan);
        mask.render(g);
        g.setColor(Color.red);
        collisionMask.render(g);
    }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.TREE_LARGE.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
        sprite.setDuration(0,1000);
    }
    
    @Override
    public int canMoveSteps(Rectangle otherMask, int steps, int dx, int dy) {
        if (collisionMask.intersectsShifted(otherMask,dx*steps,dy*steps) != 2)
            return steps;
        
        for (int i=0;i<steps;i++)
            if (collisionMask.intersectsShifted(otherMask,dx*i,dy*i) != 2)
                return i;
        return steps;
    }
}
