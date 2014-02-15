package game.player;

import game.enemy.Enemy;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.sprite.Rectangle;
import game.state.StatePlaying;
import game.util.Options;
import game.util.resource.AnimationLibrary;
import game.util.resource.ResourceLoader;
import game.util.resource.SoundLibrary;
import game.util.resource.SoundPlayer;
import game.util.server.DataPacket;
import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player {

    private EntitySprite sprite;

    private Animation sword;
    private Animation bow;

    private int spritePointer;
    
    private int x = 640;
    private int y = 512;
    private final double speed = 0.5;
    
    private int camX;
    private int camY;
    private int delta;
    
    private final int MAX_HEALTH = 10;
    private Health playerHealth = new Health(MAX_HEALTH);
    
    private ArrayList<Enemy> enemies;
    
    private final int ATTACK_SPEED = 10;
    private final int SWORD_DELAY = 400;
    
    private int direction;
    
    private boolean attacking;
    private int attackTimer;
    private int attackDelay;

    private int currentAttackId = 0;
    private int attackId = 0;
    private boolean attackHit;
    private boolean isHit;
    private boolean damageBlink;
    private boolean invulnerable = false;
    private int invulnerabilityTimer = 0;
    private int stunTimer;
    private int knockbackDX;
    private int knockbackDY;
    private final int DAMAGE_BLINK_TIME = 200;
    private final int KNOCKBACK_DISTANCE = 200;
    private final int STUN_DURATION = 400;
    
    //How slippery knockback is. Less means more slide.
    private final int KNOCKBACK_MULTIPLIER = 30;
    private final int INVULNERABILITY_DURATION = DAMAGE_BLINK_TIME*3;
        
    private final int id;

    public int getX() { return x; }
    public int getY() { return y; }
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame());
    }

    public Rectangle getAttackMask() {
        if (!attacking)
            return null;
        
        int dx = (int) Math.round(Math.sin(Math.toRadians((sword.getFrame()+2)*45)));
        int dy = (int) Math.round(Math.cos(Math.toRadians((sword.getFrame()+2)*45)));
        
        return new Rectangle(x+64*dx,y+64*dy,x+64*dx+64,y+64*dy+64);
    }
    
    public DataPacket getPacket() {
        DataPacket packet = new DataPacket();
        packet.add(x,0);
        packet.add(y,4);
        packet.add(id,8);
        return packet;
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public Player() {
        this.id = this.hashCode();
    }
    
    public void init(GameContainer container) throws SlickException {
        initializeSprite();
        spritePointer = 3;
        attacking = false;
        attackDelay = 0;
    }
    
    public void update(GameContainer container, int delta) {
        this.delta = delta;
        resolveInvulnerability(delta); //and knockback
        movePlayer(container.getInput(), delta);
        resolveCollision();
        resolveAttack(container.getInput(), delta);
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.draw(x,y,64,64,damageBlink?Color.red:Color.white);
        playerHealth.render(camX, camY);
        if (attacking)
            sword.draw(x-64,y-64,192,192);
        if (StatePlaying.DEBUG_MODE)
            renderDebugInfo(g);
        isHit = false;
    }
    
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    public void updateViewPort(int camX, int camY) {
        this.camX = camX;
        this.camY = camY;
    }
    
    private void initializeSprite() throws SlickException {
        sprite = new EntitySprite(4);
        Animation[] animList = {
            AnimationLibrary.PLAYER_RIGHT.getAnim(),
            AnimationLibrary.PLAYER_UP.getAnim(),
            AnimationLibrary.PLAYER_LEFT.getAnim(),
            AnimationLibrary.PLAYER_DOWN.getAnim(),
        };
        sprite.setAnimations(animList);
        sprite.setMasks(
            initializeMask(0),
            initializeMask(1),
            initializeMask(2),
            initializeMask(3)
        );
        initializeSword();
        initializeBow();
    }
    
    private void initializeSword() throws SlickException {
        sword = AnimationLibrary.PLAYER_SWORD_SLASH.getAnim(48);
        sword.stop();
    }
    
    private void initializeBow() throws SlickException {
        bow = ResourceLoader.initializeAnimation("player/attacks/bow_pull.png", ATTACK_SPEED * 6, 20);
        bow.stop();
    }
    
    private AnimationMask initializeMask(int index) {
        ImageMask[] masks = new ImageMask[4];
        for (int i=0;i<4;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i));
        }
        return new AnimationMask(masks);
    }
    
    private void movePlayer(Input input, int delta) {
        if (stunTimer>0) {
            sprite.getAnim(spritePointer).setCurrentFrame(1);
            sprite.getAnim(spritePointer).stop();
            x+=(knockbackDX*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            y+=(knockbackDY*stunTimer)/(KNOCKBACK_DISTANCE*KNOCKBACK_MULTIPLIER);
            return;
        }
        
        boolean DnHl = input.isKeyDown(Options.MOVE_DOWN);
        boolean DnPr = input.isKeyPressed(Options.MOVE_DOWN);
        boolean UpHl = input.isKeyDown(Options.MOVE_UP);
        boolean UpPr = input.isKeyPressed(Options.MOVE_UP);
        boolean LfHl = input.isKeyDown(Options.MOVE_LEFT);
        boolean LfPr = input.isKeyPressed(Options.MOVE_LEFT);
        boolean RiHl = input.isKeyDown(Options.MOVE_RIGHT);
        boolean RiPr = input.isKeyPressed(Options.MOVE_RIGHT);
        
        if ((DnHl || DnHl) && (UpHl || UpHl)) {
            UpHl = false;
            DnHl = false;
        }
        
        if ((LfHl || LfPr) && (RiHl || RiPr)) {
            LfHl = false;
            RiHl = false;
        }
        
        if (DnPr) {
            spritePointer = 3;
        } else {
            sprite.getAnim(3).stop();
        }
        if (DnHl) {
            sprite.getAnim(3).start();
            y += speed*delta;
            if (!UpHl && !LfHl && !RiHl) {
                spritePointer = 3;
            }
        } else {
            sprite.getAnim(3).setCurrentFrame(1);
        }
        
        if (RiPr) {
            spritePointer = 0;
        } else {
            sprite.getAnim(0).stop();
        }
        if (RiHl) {
            sprite.getAnim(0).start();
            x += speed*delta;
            if (!UpHl && !LfHl && !DnHl) {
                spritePointer = 0;
            }
        } else {
            sprite.getAnim(0).setCurrentFrame(1);
        }
        
        if (UpPr) {
            spritePointer = 1;
        } else {
            sprite.getAnim(1).stop();
        }
        if (UpHl) {
            sprite.getAnim(1).start();
            y -= speed*delta;
            if (!DnHl && !LfHl && !RiHl) {
                spritePointer = 1;
            }
        } else {
            sprite.getAnim(1).setCurrentFrame(1);
        }
        
        if (LfPr) {
            spritePointer = 2;
        } else {
            sprite.getAnim(2).stop();
        }
        if (LfHl) {
            sprite.getAnim(2).start();
            x -= speed*delta;
            if (!UpHl && !DnHl && !RiHl) {
                spritePointer = 2;
            }
        } else {
            sprite.getAnim(2).setCurrentFrame(1);
        }
    }
    
    private void resolveCollision() {
        for (Enemy e : enemies) {
            if(getCollisionMask().intersects(e.getCollisionMask(),x,y,e.getX(),e.getY()))
                resolveHit(e.getX(),e.getY());
        }
    }
    
    private void resolveAttack(Input input, int delta) {
        if ((input.isKeyDown(Options.ATTACK_UP)
                || input.isKeyDown(Options.ATTACK_DOWN)
                || input.isKeyDown(Options.ATTACK_LEFT)
                || input.isKeyDown(Options.ATTACK_RIGHT))
                && !attacking && attackDelay < 1 && !invulnerable) {
            getAttackDirection(input);
            direction = (direction+6)%8;
            attack(direction);
        }
        if (attackTimer<500)
            attackTimer+=delta;
        attackDelay-=delta;
        if (attackTimer > ATTACK_SPEED*6+160) {
            attacking = false;
        }
        resolveAttackHit();
    }
    
    private void resolveAttackHit() {
        attackHit = false;
        for (Enemy e : enemies) {
            if(e.getCollisionMask().intersects(getAttackMask(),e.getX(),e.getY())) {
                e.resolveHit(x,y,currentAttackId);
                attackHit = true;
            }
        }
    }
    
    public void resolveHit(int ox, int oy) {
        isHit = true;
        if (!invulnerable) {
            invulnerable = true; //Deal damage here somewhere.
            invulnerabilityTimer = INVULNERABILITY_DURATION;
            initializeKnockback(x-ox,y-oy);
            playerHealth.damage(1);
        }
    }
    
        public void resolveHit(int ox, int oy, int damage) {
        isHit = true;
        if (!invulnerable) {
            invulnerable = true; //Deal damage here somewhere.
            invulnerabilityTimer = INVULNERABILITY_DURATION;
            initializeKnockback(x-ox,y-oy);
            playerHealth.damage(damage);
        }
    }
    
    public void makeInvulnerable(int duration) {
        invulnerable = true;
        invulnerabilityTimer = duration;
    }
    
    private void initializeKnockback(int dx, int dy) {
        if (stunTimer<=0) {
            knockbackDX=(int)(KNOCKBACK_DISTANCE*Math.cos(Math.atan2(dy,dx)));
            knockbackDY=(int)(KNOCKBACK_DISTANCE*Math.sin(Math.atan2(dy,dx)));
            stunTimer = STUN_DURATION;
        }
    }
    
    private void resolveInvulnerability(int delta) {
        invulnerabilityTimer -= delta;
        if (invulnerabilityTimer<1 && (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0) {
            invulnerable = false;
            invulnerabilityTimer = 0;
        }
        if (stunTimer>0)
            stunTimer -= delta;
        damageBlink = false;
        if (invulnerable)
            damageBlink = (invulnerabilityTimer/DAMAGE_BLINK_TIME)%2 == 0;
    }
    
    private void getAttackDirection(Input input) {
        if (input.isKeyDown(Options.ATTACK_RIGHT))
            direction = 0;
        else if (input.isKeyDown(Options.ATTACK_UP))
            direction = 2;
        else if (input.isKeyDown(Options.ATTACK_LEFT))
            direction = 4;
        else if (input.isKeyDown(Options.ATTACK_DOWN))
            direction = 6;
    }
    
    private void attack(int direction) {
        currentAttackId = getAttackId();
        attacking = true;
        attackTimer = 0;
        attackDelay = sword.getDuration(0)*2 + SWORD_DELAY;
        sword.restart();
        sword.setCurrentFrame(direction);
        sword.stopAt((direction + 10) % 8);
        SoundPlayer.play(SoundLibrary.values()[(int)(3*Math.random())]);
    }
    
    private void renderDebugInfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("delat: " + String.valueOf(delta),10+camX,24+camY);
        g.drawString("x: " + String.valueOf(x),10+camX,38+camY);
        g.drawString("y: " + String.valueOf(y),10+camX,52+camY);
        g.drawString(attacking?"Attacking":"Not attacking",10+camX,66+camY);
        g.drawString(String.valueOf(attackTimer),10+camX,80+camY);
        g.drawString(isHit?"Hit":"Not Hit",10+camX,94+camY);
        g.drawString(attackHit?"Hitting!":"Not Hitting",10+camX,108+camY);
        if (StatePlaying.DEBUG_COLLISION) {
            getCollisionMask().draw(x,y,g);
            if (attacking) {
                g.setColor(Color.red);
                Rectangle r = getAttackMask();
                g.drawRect(r.getX1(),r.getY1(),r.getWidth(),r.getHeight());
            }
        }
    }

    private int getAttackId() {
        attackId = attackId>Integer.MAX_VALUE-1?0:attackId+1;
        return attackId;
    }
}