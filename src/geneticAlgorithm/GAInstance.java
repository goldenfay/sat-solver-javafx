package geneticAlgorithm;

import GraphSearchs.SAT.SATEvaluator;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static satproblem.SATPROBLEM.generateRandomSolution;

/**
 *
 * @author mac
 */
public class GAInstance 
{
    public static int nbIndividuals=0;
    public static double oldPopulationFitness=0;
    
    ArrayList<Individual> individualsList = new ArrayList();
    
    public ArrayList<Individual> getIndividualsList()
    {
        return individualsList;
    }
    public void setIndividualsList(ArrayList<Individual> individualsList)
    {
        this.individualsList =individualsList ;
    }
    
    
    public void initialize(int nbIndividuals)
    {
        //MAKING OUR INITIAL POPULATION BY CREATING A RANDOM NUMBER OF INDIVIDUALS 
        ArrayList<Integer> solution;
        Individual indiv;
        for(int i =0; i<nbIndividuals;i++)
        {
            solution = generateRandomSolution();
            indiv = new Individual(solution);
            this.individualsList.add(indiv);
        }
        
        //UPDATING THE NUMBER OF INDIVIDUALS
        GAInstance.nbIndividuals = individualsList.size();   
    }
    
    /**
     * Méthode qui evalue le Fitness de la population
     * @return Le Fitness de la population
     */
    public double getPopulationFitness(){
        double fitness=0;
        for(int i=0;i<this.individualsList.size();i++) fitness+=individualsList.get(i).getEvaluationResult();
        
        return fitness;
    }
    
    //FUNCTION USED TO VERIFY IF THE ACTUAL POPULATION CONTAINS A SOLUTION 
    public boolean containsSolution(SATEvaluator evaluator)
    {
        for(int i =0; i<this.individualsList.size(); i++)
        {
            if(evaluator.individualIsGoal(this.individualsList.get(i))) return true;
        }
        return false;
    }
    //FUNCTION THAT FIND THE BEST SOLUTION IN THE POPULATION
    public Individual getBestIndividual(SATEvaluator evaluator)
    {
        int ibest = 0;
        double best = 0;
         for(int i =0; i<this.individualsList.size(); i++)
        {
            if(evaluator.evaluateIndividual(this.individualsList.get(i))>best)
            {
                best = evaluator.evaluateIndividual(this.individualsList.get(i));
                ibest = i;
            }
        }
         return this.individualsList.get(ibest);
    }
    
    public Individual getOptimalSolution()
    {
         double bestResult = 0;
         Individual indiv = new Individual() ;
        for(int i=0;i<this.individualsList.size();i++)
        {
          if(this.individualsList.get(i).getEvaluationResult()>=bestResult)
          {
              bestResult = this.individualsList.get(i).getEvaluationResult();
              indiv =this.individualsList.get(i);
          }
        }
        return indiv;
    }
    
    
    
    // GET THE PARENTS WITH THE BEST EVALUATION TO MAKE THE CROSSOVER BETWEEN THEM
    public ArrayList<Individual> getNewParents()
    {
        Individual parent1=null;
        Individual parent2=null;
        
        ArrayList<Individual> parents = new ArrayList();
        int maxResult = 0;
        
       
        parent1=this.individualsList.get(ThreadLocalRandom.current().nextInt(this.individualsList.size()));
        maxResult = 0;
       
        parent2=this.individualsList.get(ThreadLocalRandom.current().nextInt(this.individualsList.size()));
        
        System.out.println();
        parents.add(parent1);
        parents.add(parent2);
        return parents; 
    }
    
    
    //CROSSING BETWEEN INDIVIDUALS OF THE POPULATION
    public void crossOver(SATEvaluator evaluator, int RC, int tC, int tM)           
    {        
        ArrayList<Individual> parents  ;
        Individual parent1;
        Individual parent2;
        Individual child1 ;
        Individual child2;
        int crossOverPoint ;
        nbIndividuals=this.getIndividualsList().size();
        for(int var=0; var<((RC*nbIndividuals)/100); var++)
        {
             //SELECTION OF THE TWO BEST INDIVIDUALS 
             parents = this.getNewParents() ;
             parent1 = parents.get(0);
             parent2 = parents.get(1);
             child1  = new Individual();
             child2  = new Individual();
             crossOverPoint = ThreadLocalRandom.current().nextInt(parent1.getGeneticData().size());

       
            if((parent1.getGeneticData().size()%2) == 1)
            {

                for(int i = 0; i<=crossOverPoint ; i++)
                {
                    child1.getGeneticData().add(i, parent1.getGeneticData().get(i));
                }

                for(int i = 0; i<=crossOverPoint ; i++)
                {
                    child2.getGeneticData().add(i, parent2.getGeneticData().get(i));
                }

                for(int i = crossOverPoint+1 ; i<parent1.getGeneticData().size() ; i++)
                {
                    child2.getGeneticData().add(i, parent1.getGeneticData().get(i));
                }

                for( int i = crossOverPoint+1; i<parent1.getGeneticData().size() ; i++)
                {
                    child1.getGeneticData().add(i, parent2.getGeneticData().get(i));
                }
            }
            else
            {


                for(int i = 0; i<crossOverPoint ; i++)
                {
                    child1.getGeneticData().add(i, parent1.getGeneticData().get(i));
                }

                for(int i = 0; i<crossOverPoint ; i++)
                {
                    child2.getGeneticData().add(i, parent2.getGeneticData().get(i));
                }

                for(int i = crossOverPoint ; i<parent1.getGeneticData().size() ; i++)
                {
                    child2.getGeneticData().add(i, parent1.getGeneticData().get(i));
                }

                for( int i = crossOverPoint; i<parent1.getGeneticData().size() ; i++)
                {
                    child1.getGeneticData().add(i, parent2.getGeneticData().get(i));
                }
            }
            
            //CALCULATE THE EVALUATION OF THE NEW INDIVIDUALS (CHILD 1 AND CHILD 2)
             double evaluationResult ;
             evaluationResult = 0;
             
             evaluationResult = evaluator.evaluateIndividual(child1);
             child1.setEvaluationResult(evaluationResult);
             
             evaluationResult = evaluator.evaluateIndividual(child2);
             child2.setEvaluationResult(evaluationResult);
           
            this.individualsList.add(child1); this.individualsList.add(child2);
        }
        System.out.println("Le nombre d'individus aprés croisemnt est est "+ this.getIndividualsList().size());
    }
    
    
    //FUNCTION THAT INVERSES A RANDOM BIT OF A NUMBER OF INDIVIDUALS
    public void mutate(SATEvaluator evaluator, int RM)
    {
        Random R2 = new Random();
        int randIndivIndext;
        double evaluationResult ;
        //RANDOM MUTATION POINT 
        Random R1 = new Random();
        int var = this.getIndividualsList().get(0).getGeneticData().size() ;
        int randMutPoint  = R1.nextInt(var);
        
        int temp = (RM*this.getIndividualsList().size())/100;
        
        for(int i = 0; i<temp ; i++)
        {
           randIndivIndext = R2.nextInt(this.getIndividualsList().size());
           if(this.getIndividualsList().get(randIndivIndext).getGeneticData().get(randMutPoint)==1)
           {
               this.getIndividualsList().get(randIndivIndext).getGeneticData().set(randMutPoint,0);
           }
           else
           {
               this.getIndividualsList().get(randIndivIndext).getGeneticData().set(randMutPoint,1);
           }
           
        //UPDATING EVALUATION RESULT AFTER MUTATION
        evaluationResult = 0;             
        evaluationResult = evaluator.evaluateIndividual(this.getIndividualsList().get(randIndivIndext));
        this.getIndividualsList().get(randIndivIndext).setEvaluationResult(evaluationResult);
        
        }
    }
    
    
    //FUNCTION USED TO GET WEAKEST INDIVIDUAL POSITION IN THE POPULATION
    public int getWeakestIndividualIndex()
    {
        int worstIndiIndex = 0;
        double worstResult = this.getIndividualsList().get(0).getEvaluationResult();
        
        for(int i =0; i<this.getIndividualsList().size(); i++)
        {
            if(this.getIndividualsList().get(i).getEvaluationResult()<worstResult)
            {
                worstIndiIndex=i;
            }
        }
        
       return worstIndiIndex;
    }
    
    //FUNCTION USED TO DELETE A NUMBER OF WEAK INDIVIDUALS
    public void deleteWeakestIndividuals(int numberToDelete)
    {
       for(int i =0; i<numberToDelete; i++)
       {
           this.getIndividualsList().remove(getWeakestIndividualIndex());
       }
    }

    public boolean stagnation() {
        
        
        
        return this.getPopulationFitness()==oldPopulationFitness;
    }
}
