package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class DumbEnemy implements Collidable, Attackable {
    
    private double x;
    private double y;
    
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
        x = Math.random()*(container.getWidth()/4-16);
        y = Math.random()*(container.getHeight()/4-16);
        attackMask = new Rectangle(x,y,x+16,y+16);
    }
    
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        up.draw((int)(x*4),(int)(y*4),64,64);
    }

    public ImageMask getCollisionMask() {
        return mask;
    }

    public Rectangle getAttackMask() {
        return attackMask;
    }
}
