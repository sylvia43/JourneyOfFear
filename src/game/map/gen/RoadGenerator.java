package game.map.gen;

import game.map.Tile;
import game.state.StateSingleplayer;
import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class RoadGenerator extends MapGenerator {
    
    public static ArrayList<Shape> rects = new ArrayList<>();
    
    public RoadGenerator(Tile[][] map) {
        super(map);
    }
    
    @Override
    public void generateMap() {
        int depth = (int)Math.max(Math.floor(Math.pow(map.length,1.0/3.0)),
                Math.floor(Math.pow(map[0].length,1.0/3.0)));
                
        recursiveRoad(depth,27,0,0,2,2);
    }
    
    public void recursiveRoad(int depth, int size, int sx, int sy, int ex, int ey) {
        // DEBUGGING
        if (StateSingleplayer.DEBUG_MODE) {
            rects.add(new Rectangle(sx*64,sy*64,size*64,size*64));
            for (int i=0;i<3-depth;i++)
                System.out.print(' ');
            System.out.println(size + " " + sx + " " + sy);
        }
        // END DEBUGGING
        
        Point[] path = generatePath(sx, sy, ex, ey);
        if(size != 1) {
            for(Point p : path)
                recursiveRoad(depth-1,size/3,sx+p.getX()*(size/3),sy+p.getY()*(size/3),ex+p.getX()*(size/3),ey+p.getY()*(size/3));
        }
        else {
            for(Point p : path) {
                if(sx+p.getX() >= 27 || sy+p.getY() >= 27)
                    return;
                map[sx+p.getX()][sy+p.getY()] = Tile.DIRT_BASIC;
            }
        }
    }
    
    private Point[] generatePath(int sx, int sy, int ex, int ey) {
        ArrayList<Point> arr = new ArrayList<>();
        while(arr.size() != 3 && arr.size() != 4) {
            arr = new ArrayList<>();
            Point p = new Point(0,0,3,3);
            while(!p.getSubPoints().isEmpty()) {
                arr.add(p);
                p = p.getSubPoint((int)(Math.random()*p.getSubPoints().size()));
            }
            if(arr.isEmpty())
                arr.add(p);
        }
        return arr.toArray(new Point[arr.size()]);
    }
    
    class Point {
        
        private final int x;
        private final int y;
        private ArrayList<Point> subPoints;
        
        public int getX() { return x; }
        public int getY() { return y; }
        public ArrayList<Point> getSubPoints() { return subPoints; }
        public Point getSubPoint(int index) { return subPoints.get(index); }
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Point(int x, int y, int xbound, int ybound) {
            this.x = x;
            this.y = y;
            subPoints = new ArrayList<>();
            setupPoints(xbound, ybound);
        }
        
        private void setupPoints(int xbound, int ybound) {
            for(int i = 0; i < 2; i++)
                for(int j = 0; j < 2; j++)
                    if(!(i == 0 && j == 0))
                        if(x+i < xbound && y+j < ybound)
                            subPoints.add(new Point(x+i,y+j,xbound,ybound));
        }
    }
}
