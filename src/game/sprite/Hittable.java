package game.sprite;

/** Represents a hittable object. */
public interface Hittable {
    
    public int getX();
    public int getY();
    
    public ImageMask getCollisionMask();
    public void resolveHit(int x, int y, int attackId, int damage, double mult);
}