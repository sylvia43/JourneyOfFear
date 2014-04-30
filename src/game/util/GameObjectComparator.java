package game.util;

import java.util.Comparator;

public class GameObjectComparator implements Comparator<GameObject> {
    
    @Override
    public int compare(GameObject o1, GameObject o2) {
        return o1.getDepth()>o2.getDepth() ? 1 : (o1.getDepth()==o2.getDepth()?0:-1);
    }
}
