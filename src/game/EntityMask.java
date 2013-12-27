package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityMask {
    
    private boolean[][] mask = new boolean[16][16];
    
    public EntityMask() {
        Image image = null;
        try {
            image = ResourceLoader.initializeImage("enemy_blank.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        for (int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                mask[i][j] = image.getColor(i,j).getAlpha() == 255;
            }
        }
    }
    
    public EntityMask(Image image) {
        for (int i=0;i<16;i++) {
            for (int j=0;j<16;j++) {
                mask[i][j] = image.getColor(j,i).getAlpha() == 255;
            }
        }
    }
    
    public String toString(boolean[][] array) {
        String s = "";
        for(int i=0;i<array.length;i++) {
            for(int j=0;j<array[i].length;j++) {
                s+=array[i][j]?"1":"0";
                //if(j<array[i].length-1) s+=" ";
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