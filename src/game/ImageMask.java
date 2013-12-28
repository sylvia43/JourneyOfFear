package game;

import org.newdawn.slick.Image;

public class ImageMask {
    
    private boolean[][] mask;
    
    public ImageMask(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        mask = new boolean[width][height];
        for (int i=0;i<width;i++) {
            for (int j=0;j<height;j++) {
                mask[i][j] = image.getColor(j,i).getAlpha() == 255;
            }
        }
    }
    
    public boolean[][] getMask() { return mask; }
    
    public boolean intersects(ImageMask other, double tx, double ty, double ox, double oy) {
        boolean[][] otherMask = other.getMask();
        for (int i=0;i<mask.length;i++) {
            for (int j=0;j<mask[i].length;j++) {
                for (int k=0;k<otherMask.length;k++) {
                    for (int l=0;l<otherMask[k].length;l++) {
                        if ((tx+j)>(ox+l-0.5) && (tx+j)<(ox+l+0.5)
                                && (ty+i)>(oy+k-0.5) && (ty+i)<(oy+k+0.5)
                                && mask[i][j] && otherMask[k][l])
                            return true;
                    }
                }
            }
        }
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