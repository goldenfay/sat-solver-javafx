
package BSO.sat;

import BSO.bases.HeapDanceList;

/**
 * Class qui implemente concrètement la Liste Dances selon la politique Heap
 * @author PC
 */
public class SATHeapDanceList extends HeapDanceList<SATSolution>{

    private double minEvaluation;
    private int maxDances;

    public SATHeapDanceList(int size) {
        super(size);
    }

    @Override
    public double evaluate(SATSolution solution) {
        double val = solution.getEvaluation();
        if(val < minEvaluation)
        {
            minEvaluation = val;
            System.out.println("minimum so far: "+minEvaluation);
        }
        return val;
    }
    
    
    
    
    
                //Getters & Setters

    public double getMinEvaluation() {
        return minEvaluation;
    }

    public void setMinEvaluation(double minEvaluation) {
        this.minEvaluation = minEvaluation;
    }

    public int getMaxDances() {
        return maxDances;
    }

    public void setMaxDances(int maxDances) {
        this.maxDances = maxDances;
    }
    
    
    
    
    
    
    
    
    
    
}
