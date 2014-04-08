package game.sprite;

public abstract class Hittable {
    
    public abstract ImageMask getCollisionMask();
    public abstract void resolveHit(int x, int y, int attackId);
}