package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DumbEnemy {
    
    private int x;
    private int y;
    
    private Animation up;
    
    private String spritePath;
    
    private ImageMask mask;
    private Rectangle attackMask;
    
    public DumbEnemy() {
        this("resources/misc/enemy_blank.png");
    }
    
    public DumbEnemy(String spritePath) {
        this.spritePath = spritePath;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void init(GameContainer container) throws SlickException {
        up = ResourceLoader.initializeAnimation(spritePath);
        mask = new ImageMask(ResourceLoader.initializeImage(spritePath));
        x = (int)(Math.random()*(SlickGame.VIEW_SIZE_X));
        y = (int)(Math.random()*(SlickGame.VIEW_SIZE_Y));
        attackMask = new Rectangle(x,y,x+16,y+16);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        up.draw(x,y,64,64);
    }

    public ImageMask getCollisionMask() {
        return mask;
    }

    public Rectangle getAttackMask() {
        return attackMask;
    }
}
