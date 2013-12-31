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
    
    public boolean intersects(ImageMask other, double tx, double ty, double ox, double oy) {
        boolean[][] otherMask = other.getMask();
        if ((tx+mask.length>ox) //May have mixed up mask and mask[0].
                || ty+mask[0].length>oy
                || ox+otherMask.length>tx
                || oy+otherMask[0].length>ty)
            return false;
        
        for (int i=0;i<mask.length*4;i+=4)
            for (int j=0;j<mask[i].length*4;j+=4)
                for (int k=0;k<otherMask.length*4;k+=4)
                    for (int l=0;l<otherMask[k].length*4;l+=4)
                        if ((tx+j)>(ox+l-2) && (tx+j)<(ox+l+2)
                                && (ty+i)>(oy+k-2) && (ty+i)<(oy+k+2)
                                && mask[i][j] && otherMask[k][l])
                            return true;
        return false;
    }

    public boolean intersects(Rectangle other) {
        return true;
    }
    
    public String toString(boolean[][] array) {
        String s = "";
        for (boolean[] inner : array) {
            for (int j=0;j<inner.length;j++) {
                s += inner[j]?"1":"0";
            }
            s+="\n";
        }
        return s;
    }
    
    public String toString() {
        return toString(this.mask);
    }
}