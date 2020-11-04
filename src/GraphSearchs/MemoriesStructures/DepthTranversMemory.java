
package GraphSearchs.MemoriesStructures;

/**
 *
 * @author PC
 */
public class DepthTranversMemory extends Memory{

    @Override
    public GraphSearchs.Bases.Node getNext() {
        return this.getLast();
    }

    
    
    
}
