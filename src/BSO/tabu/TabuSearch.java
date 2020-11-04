
package BSO.tabu;


import BSO.sat.SATInstance;
import BSO.sat.SATSolution;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PC
 */
public class TabuSearch<E> extends TabuSearchMain<E> {

    
    
    public TabuSearch(SATInstance instance,long maximumIteration, long maxListSize) {
        super(instance,new TabuList<E>(maxListSize));
        this.maxIterations=maximumIteration;
        
    }

    
    
    
    
    
    @Override
    public ArrayList<E> getVoisins(E pointSolution) {
        
        ArrayList<E> voisins=new ArrayList<E>();
        ArrayList<Integer> chosedBits=new ArrayList<Integer>();
        
        Random random=new Random(0);
        while(chosedBits.size()<this.instance.getNbrVariables()){
            int randIndex=random.nextInt(instance.getNbrVariables());
            if(!chosedBits.contains(randIndex)) chosedBits.add(randIndex);
        }
        
        return voisins;
    }

    @Override
    public boolean finish(E solution) {
        return this.nbrIterations >=this.maxIterations || this.searchFinished;
    }

    @Override
    public double evaluate(E solution) {
        double eval=((SATSolution)solution ).getEvaluation();
        if(eval==0) setSearchFinished(true);
        return eval;
    }
    
    
    
    
}
