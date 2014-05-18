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
        if(size == 0)
            return;
        
        // DEBUGGING
        if (StateSingleplayer.DEBUG_MODE) {
            rects.add(new Rectangle(sx*64,sy*64,size*64,size*64));
            for (int i=0;i<3-depth;i++)
                System.out.print(' ');
            System.out.println(size + " " + sx + " " + sy);
        }
        // END DEBUGGING
        
        Point[] path = generatePath(sx, sy, ex, ey);
        if(size != 1)
            for(Point p : path)
                recursiveRoad(depth-1,size/3,sx+p.getX()*(size/3),sy+p.getY()*(size/3),ex+p.getX()*(size/3),ey+p.getY()*(size/3));
        else
            for(Point p : path)
                map[sx+p.getX()][sy+p.getY()] = Tile.DIRT_BASIC;
    }
    
    
    
    private Point[] generatePath(int sx, int sy, int ex, int ey) {
        Point[] arr = null;
        if(sx == ex) {
            arr = new Point[] {
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2)
            };
        } else if(sy == ey) {
            arr = new Point[] {
                new Point(0, 0),
                new Point(1, 0),
                new Point(2, 0)
            };
        } else {
            switch((int)(Math.random()*3)) {
                case 0:
                    arr = new Point[] {
                        new Point(0, 0),
                        new Point(1, 1),
                        new Point(2, 2)
                    };
                    break;
                case 1:
                    arr = new Point[] {
                        new Point(0, 0),
                        new Point(1, 0),
                        new Point(2, 1),
                        new Point(2, 2)
                    };
                    break;
                case 2:
                    arr = new Point[] {
                        new Point(0, 0),
                        new Point(0, 1),
                        new Point(1, 2),
                        new Point(2, 2)
                    };
                    break;
            }
        }
        return arr;
    }
    
    class Point {
        
        private final int x;
        private final int y;
        
        public int getX() { return x; }
        public int getY() { return y; }
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
