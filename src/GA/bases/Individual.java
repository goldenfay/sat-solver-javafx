
package GA.bases;

import java.util.BitSet;

/**
 *
 * @author SELMANE
 */
public class Individual {
    protected BitSet chromosome;
    protected double fitness;

    public Individual(BitSet chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeSize) {
        this.chromosome=new BitSet(chromosomeSize);
        for(int i=0;i<chromosomeSize;i++){
            if(Math.random()<0.5) 
                this.chromosome.clear(i);
            else 
                this.chromosome.set(i);
        }
    }
    
    
    
    public void setGene(int index,int value){
        this.chromosome.set(index, value==1);
    }
    
    public int getGene(int index){
        return this.chromosome.get(index)?1:0;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    public int chromosomeLength(){
        return this.chromosome.size();
    }

    @Override
    public String toString() {
        String desc="[";
        for(int i=0;i<chromosome.size();i++) desc+=chromosome.get(i)?"1":"0"+(i==chromosome.size()?"":",");
        
        return desc+"]";
    }
    
    
    
    
    
    
    
    
}
