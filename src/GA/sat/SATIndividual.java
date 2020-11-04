
package GA.sat;

import GA.bases.Individual;
import java.util.BitSet;

/**
 *
 * @author PC
 */
public class SATIndividual extends Individual {
    
    private SATInstance instance;
    public SATIndividual(SATInstance instance,BitSet chromosome) {
        
        super(chromosome);
        this.instance=instance;
    }

    public SATIndividual(SATInstance instance, int chromosomeSize) {
        super(chromosomeSize);
        this.instance = instance;
    }
    
    
    
    
    
    
    private int getNBROfClausedSatified(){
        int nbr=0;
        
        for(int[] line:instance.getMATRICE_CNF()){
            
            for(int i=0;i<line.length;i++){
                if( (this.chromosome.get( Math.abs(line[i])-1 )&& line[i]>0  ) ||
                      (!this.chromosome.get( Math.abs(line[i])-1 )&& line[i]<0  )  ) 
                    nbr++;
            }
            
        }
        
        
        return nbr;
    }
    
    
    public double calculateFitness(){
        this.setFitness(this.getNBROfClausedSatified()/instance.getNbrClauses());
        return this.getFitness();
    }
    
    
    
}
