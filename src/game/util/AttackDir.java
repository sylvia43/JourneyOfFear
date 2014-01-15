package game.util;

public class AttackDir {
    
    private boolean bit1;
    private boolean bit2;
    
    public static AttackDir right = new AttackDir(false,false);
    public static AttackDir up = new AttackDir(false,true);
    public static AttackDir left = new AttackDir(true,false);
    public static AttackDir down = new AttackDir(true,true);
    
    private AttackDir(boolean b1, boolean b2) {
        bit1 = b1;
        bit2 = b2;
    }
}