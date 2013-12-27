package game;

public class AttackMask {

    public boolean intersects(EntityMask other) {
        return true;
    }

    public boolean intersects(AttackMask other) {
        return true;
    }
}
