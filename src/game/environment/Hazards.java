package game.environment;

import game.player.Player;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Hazards {
    protected EntitySprite sprite;
    protected String spritepath;
    protected int spritePointer;
    protected int animationSpeed;
    protected boolean collisionHit;
    
    protected int x;
    protected int y;
    
    protected Player player;
    
    public Hazards(Player player) {
        this.player = player;
    }
    
    //getters
    public int getX() { return x; }
    public int getY() { return y; }
    
    //these should be overriden
    protected void testCollision() {}
}
