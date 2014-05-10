package game.map.util;

public class MapPath {
    
    private int[][] weights;
    int sx, sy, ex, ey;
    
    public MapPath(int _sx, int _sy, int _ex, int _ey, int[][] _weights) {
        sx = _sx;
        sy = _sy;
        ex = _ex;
        ey = _ey;
        weights = _weights;
    }
    
    public boolean[][] pathFind(int x, int y, boolean[][] past) {
        if(x == ex && y == ey)
            return past;
        past[x][y] = true;
        for(int i = 0; i < 2; i++)
            for(int j = -1; j < 2; j++)
                if(x+i < weights.length && (y+j < weights[0].length && y+j >= 0))
                    past = pathFind(x+i, y+j, past);
    }
}
