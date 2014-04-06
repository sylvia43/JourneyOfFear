package game.util;

import java.util.Comparator;

public abstract class GameObject implements Comparator {
    
    public abstract int getY();
    
    @Override
    public int compare(Object c1, Object c2) {
        if (c1==null || c2==null)
            return 0;
        if (!(c1 instanceof GameObject) || !(c2 instanceof GameObject))
            return 0;
        
        GameObject o1 = (GameObject) c1;
        GameObject o2 = (GameObject) c2;
        
        if (o1.getY() < o2.getY())
            return 1;
        else if (o1.getY() == o2.getY())
            return 0;
        else
            return -1;
    }
}