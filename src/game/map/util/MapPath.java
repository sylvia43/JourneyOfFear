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
    
    public int pathFind() {
        return 0;
    }
}
