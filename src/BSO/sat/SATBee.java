
package BSO.sat;

import BSO.bases.Bee;
import BSO.tabu.TabuSearch;

/**
 * Class qui implémente le concept d'une abeille(Bee) du Problème SAT
 * @author PC
 */
public class SATBee  extends Bee<SATSolution>{

    private SATInstance instance;

    
    
    
    public SATBee(SATInstance instance) {
        this.instance = instance;
        
    }
    
    
    @Override
    public SATSolution initialize() {
        return SATSolution.generateRandomSolution(instance);
    }

    @Override
    public SATSolution search(int maxIteration,int maxSize) {
     
        TabuSearch<SATSolution> tSearch=new TabuSearch<SATSolution>(instance,maxIteration,maxSize);
        
        return  tSearch.search(this.getZoneSearch());
    }
    
    
    
                // Getters & Setters 

    public SATInstance getInstance() {
        return instance;
    }

    public void setInstance(SATInstance instance) {
        this.instance = instance;
    }
    
    
}
