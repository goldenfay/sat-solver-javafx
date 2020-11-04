
package GA.sat;

import GA.bases.GADelegate;
import GA.bases.Individual;
import GA.bases.Population;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PC
 */
public class SATAgent implements GADelegate{
    private SATInstance instance;
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int elitCounter;
    private String crossoverMethode;

    
    
    public SATAgent(SATInstance instance, int populationSize, double crossoverRate, double mutationRate, int elitCounter) {
        this.instance = instance;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitCounter = elitCounter;
        this.crossoverMethode="Unipoint";
    }
    
    public SATAgent(SATInstance instance, int populationSize, double crossoverRate, double mutationRate, int elitCounter,String crossoverMethod) {
        this.instance = instance;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitCounter = elitCounter;
        this.crossoverMethode=crossoverMethod;
    }

    
    
    
    
    
    @Override
    public double calculateFitness(Individual individual) {
        return 0;
    }

    @Override
    public Individual selectParent(Population population, String selectionMethode) {
        ArrayList<Individual> individuals = population.getIndividuals();
        
          // Simuler la procédure de tourner la roue et choisir aléatoirement un individu
        double Pfitness=population.getPopulationFitness();
        double wheelPosition=Math.random()*Pfitness;
        double spin=0;
          
            // Tourner et choisir l'individu parent
        for(Individual indiv:individuals){
            spin+=indiv.getFitness();
            if(spin>=wheelPosition) return indiv;
        }  
        
        return individuals.get(individuals.size()-1);
            
    }

    
    
    @Override
    public Population crossoverPopulation(Population population) {
        Population newPopulation=new Population(population.size(),population.getIndividual(0).chromosomeLength());
        
        for(int i=0;i<population.size();i++){
            
            Individual parent1=population.getFittestIndividual(i);
            int chromosomeSize=parent1.chromosomeLength();
            Individual child=new SATIndividual(instance,chromosomeSize );
            Individual parent2=selectParent(population, crossoverMethode);
            
            double Rc=Math.random();
              // Vérifier si cet individu doit subir à un croisement
            if(this.crossoverRate> Rc&& i>this.elitCounter){
                
                int crossPoint1,crossPoint2=chromosomeSize-1;
                if(this.crossoverMethode.equals("Unipoint")){
                      // Choisir le centre comme point de croisement,donc le fils aura moitié de père1 moitié de père2
                      
                      crossPoint1=crossPoint2/2;
                }
                else{
                    crossPoint1=new Random(crossPoint2).nextInt();
                    crossPoint2=new Random().nextInt(chromosomeSize-1)+crossPoint1;
                }
                
                for(int j=0;j<chromosomeSize;j++){
                    if(j<crossPoint1) 
                        child.setGene(j, parent1.getGene(j));
                    else
                        if(j<crossPoint2)
                            child.setGene(j, parent2.getGene(j));
                        else
                            child.setGene(j, parent1.getGene(j));
                }
                
                newPopulation.setIndividual(i, child);
            }  
            else{
                
                
                
            }
            
        }
        
        
        return newPopulation;
                
    }

    @Override
    public Population mutatePopulation(Population population) {
       Population newPopulation=new Population(population.size(),population.getIndividual(0).chromosomeLength());
       
       for(int i=0;i<population.size();i++){
           
           Individual individual=population.getFittestIndividual(i);
           
           for(int j=0;j<individual.chromosomeLength();j++){
               
                 // Ne pas faire de mutation pour les élites
               if(i>=this.elitCounter){
                   
                   double Rm=Math.random();
                   
                     // Tester si il doit subir à une mutation
                   if(this.mutationRate>Rm){
                       individual.setGene(j, individual.getGene(j)==1?0:1);
                   }  
                   
               }  
               
               
           }
       
           newPopulation.setIndividual(i, individual);
       }
       
       return newPopulation;
    }
    
    
    
    
    
                //Getters & Setters

    public SATInstance getInstance() {
        return instance;
    }

    public void setInstance(SATInstance instance) {
        this.instance = instance;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getElitCounter() {
        return elitCounter;
    }

    public void setElitCounter(int elitCounter) {
        this.elitCounter = elitCounter;
    }

    public String getCrossoverMethode() {
        return crossoverMethode;
    }

    public void setCrossoverMethode(String crossoverMethode) {
        this.crossoverMethode = crossoverMethode;
    }
    
    
    
    
    
    
 
    
}
