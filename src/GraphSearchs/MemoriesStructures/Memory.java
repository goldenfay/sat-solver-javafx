
package GraphSearchs.MemoriesStructures;

import GraphSearchs.Bases.Node;
import java.util.LinkedList;

/**
 *
 * @author PC
 */
public abstract class Memory  extends LinkedList<Node>{

    
    public void addNode(Node node){
        this.add(node);
    }
    
    public abstract Node getNext();
    
}
