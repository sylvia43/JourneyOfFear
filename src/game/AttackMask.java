package game;

public class AttackMask implements Mask {

    public boolean intersects(EntityMask other) {
        return true;
    }

    public boolean intersects(AttackMask other) {
        return true;
    }
}
