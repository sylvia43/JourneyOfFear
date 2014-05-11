package game.map.gen;

import game.map.Tile;

public class RoadGenerator extends MapGenerator {
    
    public RoadGenerator(Tile[][] map) {
        super(map);
    }
    
    @Override
    public void generateMap() {
        int depth = (int)Math.max(Math.floor(Math.pow(map.length,1.0/3.0)),
                Math.floor(Math.pow(map[0].length,1.0/3.0)));
                
        recursiveRoad(depth,27,0,0,-1,-1,1,1);
        
    }
    
    public void recursiveRoad(int depth, int size, int x, int y, int sx, int sy, int ex, int ey) {
        
        for (int i=0;i<3-depth;i++)
            System.out.print(' ');
        System.out.println(size + " " + x + " " + y);
        
        Point[] path = generatePath(sx,sy,ex,ey);
        
        if (depth == 0) {
            map[x][y] = Tile.DIRT_BASIC;
            return;
        }
        
        for (int i=0;i<path.length;i++) {
            Point p = path[i];
            Point last;
            Point next;
            
            if (i==0)
                last = new Point(sx,sy);
            else
                last = path[i-1];
            
            if (i==path.length-1)
                next = new Point(ex,ey);
            else
                next = path[i+1];
            
            recursiveRoad(depth-1,size/3,x+p.getX()*size/3,y+p.getY()*size/3,last.getX(),
                    last.getY(),next.getX(),next.getY());
        }
    }
    
    private Point[] generatePath(int sx, int sy, int ex, int ey) {
        Point[] arr = null;
        
        // Ends are next to each other (forming V).
        if (sx*ex+sy*ey == 2) {
            switch ((int)(Math.random()*3)) {
                case 0:
                    arr = new Point[3];
                    arr[0] = new Point(0,0);
                    arr[1] = new Point(1,1);
                    arr[2] = new Point(2,2);
                    break;
                case 1:
                    arr = new Point[4];
                    arr[0] = new Point(0,0);
                    arr[1] = new Point(0,1);
                    arr[2] = new Point(1,2);
                    arr[3] = new Point(2,2);
                    break;
                case 2:
                    arr = new Point[4];
                    arr[0] = new Point(0,0);
                    arr[1] = new Point(1,0);
                    arr[2] = new Point(2,1);
                    arr[3] = new Point(2,2);
                    break;
            }
            return arr;
        } else if (sx*ex+sy*ey == 0) {
            arr = new Point[3];
            arr[0] = new Point(0,0);
            arr[1] = new Point(1,1);
            arr[2] = new Point(0,2);
            return arr;
        }
        
        arr = new Point[3];
        
        arr[0] = new Point(0,0);
        arr[1] = new Point(1,1);
        arr[2] = new Point(2,2);
        
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
