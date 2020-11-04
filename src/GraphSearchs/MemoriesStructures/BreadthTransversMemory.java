
package GraphSearchs.MemoriesStructures;

import GraphSearchs.Bases.Node;

/**
 *
 * @author PC
 */
public class BreadthTransversMemory extends Memory{

    @Override
    public Node getNext() {
        Node node=this.getFirst();
        this.removeFirst();
        return node;

    }

    

   
    
}
