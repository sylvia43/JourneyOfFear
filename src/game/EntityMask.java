package game;

import org.newdawn.slick.Image;

public class EntityMask implements Mask {
    
    private boolean[][] mask = new boolean[16][16];
    
    public EntityMask(Image image) {
        for (int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                mask[i][j] = image.getColor(i,j).getAlpha() == 255;
            }
        }
    }
    
    public String toString(boolean[][] array) {
        String s = null;
        for(int i=0;i<array.length;i++) {
            for(int j=0;j<array[i].length;j++) {
                s+=array[i][j]?"1":"0";
                if(j<array[i].length-1) s+=" ";
            }
            s+="\n";
        }
        return s;
    }
    
    public String toString() {
        return toString(this.getMask());
    }
    
    public boolean intersects(EntityMask other) {
        return true;
    }

    public boolean intersects(AttackMask other) {
        return true;
    }
    
    public boolean[][] getMask() {
        return mask;
    }
}