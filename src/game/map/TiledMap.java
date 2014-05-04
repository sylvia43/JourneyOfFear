package game.map;

import java.util.Random;

public class TiledMap {
    
    private Area wrapper;
    private Tile[][] map;
    private int width;
    private int height;
    private int rx; //Road Start (X)
    private int ry; //Road Start (Y)
    private int rxe; //Road End (X)
    private int rye; //Catcher in the (Y)
    private Random rand;
    
    public Tile getTile(int x, int y) {
        if (x<0 || y<0 || x>map.length || y>map[x].length)
            return null;
        return map[x][y];
    }
    
    public int[][] getRoadCoords() {
        return new int[][] {
            { rx, ry },
            { rxe, rye },
        };
    }
    
    public TiledMap(int _width, int _height, Area _wrapper) {
        width = _width;
        height = _height;
        wrapper = _wrapper;
        
        map = new Tile[_width][_height];
        rand = new Random();
    }
    
    public TiledMap(int _width, int _height, int _seed, Area _wrapper) {
        this(_width, _height, _wrapper);
        rand = new Random(_seed);
    }
    
    public void init() {
        fillStandardGrass();
        boolean axis = (int)(Math.random() * 2) == 1;
        if(axis)
            generateRoad((rx = (int)(Math.random() * width)), (ry = 0),
                    (rxe = (int)(Math.random() * width)), (rye = height));
        else
            generateRoad((rx = 0), (ry = (int)(Math.random() * height)),
                    (rxe = width), (rye = (int)(Math.random() * height)));
    }
        
    private void fillStandardGrass() {
        fill(Tile.GRASS_BASIC);
        randomize(Tile.GRASS_VARIANT,0.2);
        randomize(Tile.GRASS_SHIFT,0.1);
        randomize(Tile.GRASS_BOLD,0.05);
        randomize(Tile.GRASS_FLOWER,0.01);
    }
        
    private void fill(Tile tile) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                map[i][j] = tile;
    }
    
    private void randomize(Tile tile, double chance) {
        for(int i=0;i<map.length;i++)
            for(int j=0;j<map[i].length;j++)
                if (Math.random()<chance)
                    map[i][j] = tile;
    }
    
    private void generateRoad(int x, int y, int ex, int ey) {
        int[][] random = new int[map.length][map[0].length];
        
        for (int i=0;i<random.length;i++)
            for (int j=0;j<random[0].length;j++)
                random[i][j] = rand.nextInt(8);
        
        int[][] weights = new int[map.length][map[0].length];
        int total;
        int count;
        for (int i=0;i<random.length;i++) {
            for (int j=0;j<random[0].length;j++) {
                total = 0;
                count = 0;
                for (int k=-1;k<2;k++) {
                    for (int l=-1;l<2;l++) {
                        if (i+k<0 || j+l<0 || i+k>=width || j+l>=height)
                            continue;
                        total += random[i+k][j+l];
                        count++;
                    }
                }
                weights[i][j] = total/count;
            }
        }
        
        boolean[][] traversed = new boolean[width][height];
        
        recursiveRoad(0,0,0,weights,traversed);
    }

    private void recursiveRoad(int x, int y, int length, int[][] weights, boolean[][] traversed) {
        map[x][y] = Tile.DIRT_BASIC;
        
        /*
        for (int i=0;i<traversed.length;i++) {
            for (int j=0;j<traversed[0].length;j++) {
                System.out.print(traversed[i][j]?"X":" ");
            }
            System.out.println();
        }
        
        if (length>200 && (x==0 || y==0 || x==width || y==height)) {
            map[x][y] = Tile.STONE_BASIC;
            return;
        }
        */
        
        int mx = x;
        int my = y;
        int min = 1000;
        
        for (int i=-1;i<=1;i++) {
            for (int j=-1;j<=1;j++) {
                if ((i==0 && j==0) || x+i<0 || y+j<0 || x+i>=width || y+j>=height || traversed[x+i][y+j])
                    continue;
                
                int raw = weights[x+i][y+j];
                
                int weighted = raw;
                
                if (Math.abs(i)==1 && Math.abs(j)==1)
                    weighted -= 10;
                else if (Math.abs(i)==1 || Math.abs(j)==1)
                    weighted -= 5;
                
                for (int k=-1;k<2;k++) {
                    for (int l=-1;l<2;l++) {
                        if (x+k<0 || y+l<0 || x+k>=width || y+l>=height)
                            continue;
                        if (traversed[x+k][y+l])
                            weighted += 50;
                    }
                }
                
                if (weighted < min) {
                    min = weighted;
                    mx = x+i;
                    my = y+j;
                }
            }
        }
        
        if (mx==x && my==y) {
            mx += 1;
            my += 1;
        }
        
        if (mx>=width || my>=height)
            return;
        if (mx>=width)
            mx = width-1;
        if (my>=height)
            my = height-1;
        
        traversed[mx][my] = true;
        recursiveRoad(mx,my,length+1,weights,traversed);
    }
}