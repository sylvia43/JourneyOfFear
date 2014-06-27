package game.environment.obstacle;

import game.map.Area;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StateMultiplayer;
import game.util.resource.AnimationLibrary;

public class Tree extends SolidObstacle {
    
    private Rectangle collisionMask;
    
    public Tree() {
        this((int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)),
                (int)(Math.random()*(StateMultiplayer.WORLD_SIZE_Y)));
    }

    public Tree(int x, int y) {
        super(x,y);
        miniWidth = 3;
        miniHeight = 6;
        collisionMask = new Rectangle(x-16,y-16,x+16,y+16);
        mask.update(x-spriteWidth/2,y-spriteHeight/2);
    }
    
    @Override
    public void update(int delta, Area currentArea) { }
    
    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.TREE_LARGE.getAnim();
        mask = new ImageMask(sprite.getImage(0),x,y);
        sprite.setDuration(0,1000);
        spriteWidth = sprite.getImage(0).getWidth()*4;
        spriteHeight = sprite.getImage(0).getHeight()*4;
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
