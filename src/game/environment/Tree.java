package game.environment;

import game.sprite.ImageMask;
import game.util.resource.AnimationLibrary;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Tree extends Obstacle {
        
    public Tree() {
        super();
        miniWidth = 3;
        miniHeight = 6;
    }

    public Tree(int x, int y) {
        super(x,y);
        miniWidth = 3;
        miniHeight = 6;
    }
    
    @Override
    public void render(GameContainer container, Graphics g) {
        sprite.draw(x,y,256,192);
        g.setColor(Color.cyan);
        mask.draw(x,y,g);
    }

    @Override
    protected void initializeSprite() {
        sprite = AnimationLibrary.TREE_LARGE.getAnim();
        mask = new ImageMask(sprite.getImage(0));
        sprite.setDuration(0,1000);
    }
}
