package game.sprite;

import game.state.StateSingleplayer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageMask {
    
    private boolean[][] mask;
    
    public ImageMask(Image image) {                
        mask = simplify(getMaskFromImage(image));
    }
    
    public ImageMask(Rectangle rectangle) {
        mask = getMaskFromRectangle(rectangle);
    }
    
    public boolean[][] getMask() { return mask; }
    
    public boolean intersects(ImageMask other, int tx, int ty, int ox, int oy) {
        boolean[][] otherMask = other.getMask();
        if ((tx+mask.length*4<ox)
                || ty+mask[0].length*4<oy
                || ox+otherMask.length*4<tx
                || oy+otherMask[0].length*4<ty)
            return false;
        
        for (int i=0;i<mask.length;i++)
            for (int j=0;j<mask[i].length;j++)
                if (mask[i][j])
                    for (int k=0;k<otherMask.length;k++)
                        for (int l=0;l<otherMask[k].length;l++)
                            if (otherMask[k][l]
                                    && (tx+i*4)<=(ox+k*4+4) && (tx+i*4+4)>=(ox+k*4)
                                    && (ty+j*4)<=(oy+l*4+4) && (ty+j*4+4)>=(oy+l*4))
                                return true;
        return false;
    }

    public boolean intersects(Rectangle other, int ix, int iy) {
        if (other == null)
            return false;
        return this.intersects(new ImageMask(other),ix,iy,other.getX1(),other.getY1());
    }
    
    public String toString(boolean[][] array) {
        StringBuilder s = new StringBuilder();
        for (int i=0;i<array.length;i++) {
            for (int j=0;j<array[i].length;j++) {
                s.append(array[j][i]?"X":" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public void draw(int x, int y, Graphics g) {
        if (!StateSingleplayer.DEBUG_COLLISION)
            return;
        for (int i=0;i<mask.length;i++)
            for (int j=0;j<mask[i].length;j++)
                if (mask[i][j])
                    g.drawRect(x+4*i,y+4*j,4,4);
    }
    
    @Override
    public String toString() {
        return toString(this.mask);
    }
    
    private boolean[][] getMaskFromImage(Image image) {
        boolean[][] imageMask = new boolean[image.getWidth()][image.getHeight()];
        
        for (int i=0;i<imageMask.length;i++) {
            for (int j=0;j<imageMask[i].length;j++) {
                imageMask[i][j] = image.getColor(i,j).getAlpha() == 255;
            }
        }
        return imageMask;
    }
    
    private boolean[][] getMaskFromRectangle(Rectangle rectangle) {
        boolean[][] imageMask = new boolean[rectangle.getWidth()/4][rectangle.getHeight()/4];
        
        for (int i=0;i<imageMask.length;i++) {
            for (int j=0;j<imageMask[i].length;j++) {
                imageMask[i][j] = true;
            }
        }
        return imageMask;
    }
    
    private boolean[][] simplify(boolean[][] startMask) {
        int width = startMask.length;
        int height = startMask[0].length;
        
        boolean[][] simplifiedMask = new boolean[width][height];
        
        for (int i=0;i<width;i++) {
            System.arraycopy(startMask[i], 0, simplifiedMask[i], 0, height);
        }
        
        for (int i=1;i<width-1;i++) {
            for (int j=1;j<height-1;j++) {
                simplifiedMask[i][j] = startMask[i][j]
                        && !((startMask[i+1][j])
                        && (startMask[i+1][j-1])
                        && (startMask[i+1][j+1])
                        && (startMask[i][j+1])
                        && (startMask[i-1][j+1])
                        && (startMask[i-1][j])
                        && (startMask[i-1][j-1])
                        && (startMask[i][j-1]));
            }
        }
        return simplifiedMask;
    }
}