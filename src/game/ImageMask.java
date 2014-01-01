package game;

import org.newdawn.slick.Image;

public class ImageMask {
    
    private boolean[][] mask;
    
    public ImageMask(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        boolean[][] startMask = new boolean[width][height];
        
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                startMask[i][j] = image.getColor(j,i).getAlpha() == 255;
            }
        }
        
        boolean[][] newMask = startMask;
        /* Broken code to simplify the mask.
        for (int i=1;i<width-1;i++) {
            for (int j=1;j<height-1;j++) {
                if (startMask[i][j]
                        && (i<16||startMask[i+1][j])
                        && (i<16&&j>0||startMask[i+1][j-1])
                        && (i<16&&j<16||startMask[i+1][j+1])
                        && (j<16||startMask[i][j+1])
                        && (i>0&&j<16||startMask[i-1][j+1])
                        && (i>0||startMask[i-1][j])
                        && (i>0&&j>0||startMask[i-1][j-1])
                        && (j>0||startMask[i][j-1]))
                    newMask[i][j] = false;
            }
        }
        */
        mask = newMask;
    }
    
    public boolean[][] getMask() { return mask; }
    
    public boolean intersects(ImageMask other, int tx, int ty, int ox, int oy) {
        boolean[][] otherMask = other.getMask();
        if ((tx+mask.length*4<ox) //May have mixed up mask and mask[0].
                || ty+mask[0].length*4<oy
                || ox+otherMask.length*4<tx
                || oy+otherMask[0].length*4<ty)
            return false;
        
        //THIS ISN'T WORKING
        for (int i=0;i<mask.length;i++)
            for (int j=0;j<mask[i].length;j++)
                for (int k=0;k<otherMask.length;k++)
                    for (int l=0;l<otherMask[k].length;l++)
                        if ((tx+i*4)>=(ox+k*4-2) && (tx+i*4)<=(ox+k*4+2)
                                && (ty+j*4)>=(oy+l*4-2) && (ty+j*4)<=(oy+l*4+2)
                                && mask[i][j] && otherMask[k][l])
                            return true;
        return false;
    }

    public boolean intersects(Rectangle other) {
        return true;
    }
    
    public String toString(boolean[][] array) {
        String s = "";
        for (int i=0;i<array.length;i++) {
            for (int j=0;j<array[i].length;j++) {
                s += array[i][j]?"1":"0";
            }
            s+="\n";
        }
        return s;
    }
    
    public String toString() {
        return toString(this.mask);
    }
}