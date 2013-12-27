package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Enemy implements Collidable, Attackable {
    
    private String name;
    private int currentHp;
    
    private double x;
    private double y;
    private int maxHp;
    
    private Animation left;
    private Animation up;
    private Animation down;
    private Animation right;
    
    private String spritePath;
    
    private ImageMask mask;
    private Rectangle attackMask;
    
    public Enemy() {
        this("enemy_blank.png",1,"Block");
    }
    
    public Enemy(String spritePath, int hp, String name) {
        this.spritePath = spritePath;
        this.maxHp = this.currentHp = hp;
        this.name = name;
    }
    
    public void init(GameContainer container) throws SlickException {
        left = right = up = down = ResourceLoader.initializeAnimation(spritePath);
        mask = new ImageMask(ResourceLoader.initializeImage(spritePath));
        x = Math.random()*container.getWidth()/4;
        y = Math.random()*container.getHeight()/4;
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
