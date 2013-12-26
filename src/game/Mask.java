package game;

public interface Mask {
    
    public boolean intersects(EntityMask other);
    public boolean intersects(AttackMask other);
}
