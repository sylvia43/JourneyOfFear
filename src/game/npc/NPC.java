package game.npc;

import game.npc.npcutils.QuestGenerator;
import game.npc.quest.QuestSequence;
import game.npc.utils.Routine;
import game.sprite.AnimationMask;
import game.sprite.EntitySprite;
import game.sprite.ImageMask;
import game.state.StateSingleplayer;
import game.util.GameObject;
import game.util.resource.AnimationLibrary;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class NPC extends GameObject {
    
    protected int x;
    protected int y;
    
    protected int spritePointer;
    protected int spriteHeight;
    protected int spriteWidth;
    
    protected Color minimapColor;
    
    protected Routine routine;
    
    private List<QuestSequence> quests;
    
    protected String name;
    
    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public int getDepth() { return y; }
    @Override public Color getColor() { return minimapColor; }
    public String getName() { return name; }
    
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }
    
    protected EntitySprite sprite;
    
    public ImageMask getCollisionMask() {
        return sprite.getAnimationMask(spritePointer)
                .getImageMask(sprite.getAnim(spritePointer).getFrame()).update(x-spriteWidth/2,y-spriteHeight/2);
    }
    
    public NPC() {
        this((int)(Math.random()*StateSingleplayer.WORLD_SIZE_X),
                (int)(Math.random()*StateSingleplayer.WORLD_SIZE_Y));
    }
    
    public NPC(int x, int y) {
        this.x = x;
        this.y = y;
        minimapColor = Color.blue;
        spritePointer = 0;
        name = "NPC";
        quests = new ArrayList<QuestSequence>();
        quests.add(QuestGenerator.generateQuest(this));
    }
    
    public void init() {
        initializeSprite();
        spritePointer = 3;
        spriteHeight = sprite.getAnim(spritePointer).getHeight() * 4;
        spriteWidth = sprite.getAnim(spritePointer).getWidth() * 4;
    }
    
    public void update(int delta) { };
    
    public QuestSequence converse() {
        return quests.get(0);
    }
    
    @Override
    public void render(Graphics g) {
        Animation currentSprite = sprite.getAnim(spritePointer);
        currentSprite.setCurrentFrame(1);
        currentSprite.stop();
        currentSprite.draw(x-spriteWidth/2,y-spriteHeight/2,64,64);
    }
    
    protected void initializeSprite() {
        sprite = new EntitySprite(4);
        
        Animation[] animList = {
            AnimationLibrary.PLAYER_RIGHT.getAnim(),
            AnimationLibrary.PLAYER_UP.getAnim(),
            AnimationLibrary.PLAYER_LEFT.getAnim(),
            AnimationLibrary.PLAYER_DOWN.getAnim()
        };
        sprite.setAnimations(animList);
        
        AnimationMask[] animMaskList = {
            initializeMask(0),
            initializeMask(1),
            initializeMask(2),
            initializeMask(3)
        };
        sprite.setMasks(animMaskList);
    }
    
    protected AnimationMask initializeMask(int index) {
        int frames = sprite.getAnim(index).getFrameCount();
        ImageMask[] masks = new ImageMask[frames];
        for (int i=0;i<frames;i++) {
            masks[i] = new ImageMask(sprite.getAnim(index).getImage(i),x,y);
        }
        return new AnimationMask(masks);
    }
}
