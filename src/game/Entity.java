package game;

public interface Entity {
    
    public EntityMask getCollisionMask();
    public AttackMask getAttackMask();
}