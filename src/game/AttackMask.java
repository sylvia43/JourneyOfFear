package game;

public class AttackMask {

    public boolean intersects(ImageMask other) {
        return true;
    }

    public boolean intersects(AttackMask other) {
        return true;
    }
}
