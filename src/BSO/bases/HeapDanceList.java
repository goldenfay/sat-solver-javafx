
package BSO.bases;

/**
 * Class qui implémente une liste Dances selon la politique de Tas(Heap)
 * @author PC
 */
public abstract class HeapDanceList<E extends Comparable>  implements DanceList<E>{
 
    private Heap<E> heap;
    private double minEvaluation=-1;
    
    
    

    public HeapDanceList(int size) {
        
        heap=new Heap<E>(size);
    }



    @Override
    public  E getBest(){
        return heap.getRoot(); // La racine est toujours le plus grand élèment
    }

    
    
    @Override
    public  void add(E solution){
        heap.add(solution);
    }

    public double getMinEvaluation() {
        return minEvaluation;
    }

    public void setMinEvaluation(double minEvaluation) {
        this.minEvaluation = minEvaluation;
    }
    
    
    
    
    
}
