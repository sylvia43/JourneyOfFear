package game.util;

import java.util.Comparator;

public class GameObjectComparator implements Comparator<GameObject> {
    
    @Override
    public int compare(GameObject o1, GameObject o2) {
        return o1.getY()>o2.getY() ? 1 : (o1.getY()==o2.getY()?0:-1);
    }
}
