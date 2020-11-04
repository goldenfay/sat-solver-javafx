
package GraphSearchs.MemoriesStructures;

import GraphSearchs.Bases.*;
import GraphSearchs.SAT.SATEvaluator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author PC
 */
public class HeuristiqueMemory extends Memory{

    private SATEvaluator evaluator;
    private Hashtable<Node,Double> evaluationTable;

    public HeuristiqueMemory(SATEvaluator evaluator) {
        this.evaluator = evaluator;
        this.evaluationTable=new Hashtable<>();
    }
    
    
    
    
    
    
    @Override
    public Node getNext() {
        return this.get(0); // récupérer le premier élement car la liste est triée
    }

    @Override
    public void addNode(GraphSearchs.Bases.Node node) {
        super.add(node);
        this.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return ((Double)evaluator.evaluate(o1)).compareTo((Double) evaluator.evaluate(o2));
                
            }
        });

        


    }

    
    
    
    
}
