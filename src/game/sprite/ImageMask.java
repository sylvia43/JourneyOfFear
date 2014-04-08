package game.sprite;

import game.state.StateSingleplayer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageMask {
    
    private boolean[][] mask;
    
    private int x;
    private int y;
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public ImageMask update(int x, int y) {this.x = x; this.y = y; return this; }
    
    public ImageMask(Image image, int x, int y) {
        mask = simplify(getMaskFromImage(image));
        this.x = x;
        this.y = y;
    }
    
    public ImageMask(Rectangle rectangle) {
        mask = getMaskFromRectangle(rectangle);
    }
    
    public boolean[][] getMask() { return mask; }
    
    public boolean intersects(ImageMask other) {
        int ox = other.getX();
        int oy = other.getY();
        boolean[][] otherMask = other.getMask();
        if ((x+mask.length*4<ox)
                || y+mask[0].length*4<oy
                || ox+otherMask.length*4<x
                || oy+otherMask[0].length*4<y)
            return false;
                        boolean up = false;
                boolean down = false;
                boolean left = false;
                boolean right = false;
        for (int i=0;i<mask.length;i++) {
            for (int j=0;j<mask[i].length;j++) {
                if (!mask[i][j])
                    continue;
                for (int k=0;k<otherMask.length;k++) {
                    for (int l=0;l<otherMask[k].length;l++) {
                        if (!otherMask[k][l])
                            continue;
                        if (x+i*4<=ox+k*4+4)
                            right = true;
                        if(x+i*4+4>=ox+k*4)
                            left = true;
                        if (y+j*4<=oy+l*4+4)
                            down = true;
                        if (y+j*4+4>=oy+l*4)
                            up = true;
                        if (up && down && left && right)
                            return true;
                    }
                }
            }
        }
        return false;
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
    
    public void render(Graphics g) {
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
    
    private boolean[][] getMaskFromRectangle(Rectangle r) {
        boolean[][] imageMask = new boolean[r.getWidth()/4][r.getHeight()/4];
        
        x = r.getX1();
        y = r.getY1();
        
        for (int i=0;i<imageMask.length;i++) {
            for (int j=0;j<imageMask[i].length;j++) {
                imageMask[i][j] = true;
            }
        }
        return simplify(imageMask);
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