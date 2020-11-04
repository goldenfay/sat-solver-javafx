
package GA.bases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author PC
 */
public class Population {
    private ArrayList<Individual> population;
    private double populationFitness;

    
    
    public Population(int populationSize,int chromosomeSize) {
        this.population=new ArrayList<Individual>();
        
        for(int i=0;i<populationSize;i++)
            this.population.add(new Individual(chromosomeSize));
    }
    
    
    
    
    
    public static Population initPopulation(int populationSize,int chromosomeSize){
        return new Population(populationSize, chromosomeSize);
    }
    
    
    
    
    
    public Individual getFittestIndividual(int index){
        
        Collections.sort(population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return Double.compare(o1.getFitness(),o2.getFitness());
            }
        });
        return population.get(index);
    }
    
    
    
    
    
    
    
    
    public ArrayList<Individual> getIndividuals(){
        return this.population;
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }
    
    
    
    public Individual getIndividual(int index){
        return this.population.get(index);
    }
    
    
    public void setIndividual(int index,Individual individual){
        this.population.set(index, individual);
    }
    public int size(){
        return this.population.size();
    }
    
    
    /**
     * Méthode qui mélange la population de manière aléatoire
     */
    public void shuffle(){
        
        Random random=new Random();
        
        for(int i=population.size()-1;i>0;i--){
            int target=random.nextInt(i+1);
            
              //Inverser les deux individus choisis
            Individual temp=population.get(target);
            population.set(target, population.get(i));
            population.set(i, temp);
        }
    }

    @Override
    public String toString() {
        String desc="";
        for(Individual indiv:population) desc+=indiv.toString()+"  Fitness : "+indiv.getFitness()+"\n";
        return desc;
    }
    
    
    
    
    
    
    
    
    
}
