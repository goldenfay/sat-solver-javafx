
package ACO.tabu;


import java.util.ArrayList;

/**
 * Class qui implémente la structure de le liste Tabu
 * @author PC
 */
public class TabuList<E> {
    
    private ArrayList<E> tabuList;
    private long maxSize;

    
    
    public TabuList(long maxSize) {
        this.maxSize = maxSize;
        tabuList=new ArrayList<E>();
    }
    
    
    public boolean contains(E solution) {
        for (E visited : tabuList)
            if(solution.equals(visited))
                return true;
        return false;
    }

    
    public void add(E solution) {
        tabuList.add(solution);
        if(maxSize >= 0 && tabuList.size() > maxSize)
            tabuList.remove(0);
    }
    
    
    
}
