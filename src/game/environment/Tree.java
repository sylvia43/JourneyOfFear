package game.environment;

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
    protected Rectangle mask;
    
    public Tree(Player player) {
        super();  
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }
    
    public Tree(Player player, int x, int y) {
        super(x,y);
        this.player = player;
        miniWidth = 3;
        miniHeight = 6;
    }
    
     public void init(GameContainer container) throws SlickException {
        initializeSprite();
    }
     
    public void render(GameContainer container, Graphics g) throws SlickException {
        sprite.draw(x,y,64,128);
    }
    public boolean testForCollision(int x, int y, Player player) {    
      //  mask.
      // return (mask.(player.getCollisionMask(),player.getX() + x,player.getY() + y));
        return (x > mask.getX1() && y > mask.getY1()&& x < mask.getX2() && y < mask.getY2());
        
    }
    
    @Override
    protected void initializeSprite() throws SlickException {
        sprite = AnimationLibrary.TREE1.getAnim();
        mask = new Rectangle(x,y,x+sprite.getImage(0).getWidth()*4,y+sprite.getImage(0).getHeight()*4);
        sprite.setDuration(0,1000);
    }
  
    @Override
    protected void resolveCollision() {
    
    }
    
}
