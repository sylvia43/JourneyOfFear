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
        print(mask);
    }
    
    private void print(boolean[][] array) {
        for(int i=0;i<array.length;i++) {
            for(int j=0;j<array[i].length;j++) {
                System.out.print(array[i][j]?"1":"0");
                if(j<array[i].length-1) System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    public boolean intersects(EntityMask other) {
        return true;
    }

    public boolean intersects(AttackMask other) {
        return true;
    }
}