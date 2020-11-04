
package PSO1;

import GraphSearchs.SAT.SATEvaluator;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mac
 */
public class PSOInstance
{
    ArrayList<Integer> GBest; 
    ArrayList<Particle> particlesList;
    Random rand = new Random();
    
    public int c1;
    public int c2;
    public int r1;
    public int r2;
    public int w ;
    
    public PSOInstance()
    {
        particlesList= new ArrayList<>();
        this.GBest= new ArrayList();
        this.r1 = rand.nextInt(3);
        this.r2 = rand.nextInt(3);
        this.w = rand.nextInt(3);               
        this.c1 = rand.nextInt(3);
        this.c2 = rand.nextInt(3);
    }
    
    
    public void setGBest(ArrayList<Integer> Gbest)
    {
        this.GBest=Gbest;
    }
    public ArrayList<Integer> getGBest()
    {
        return this.GBest;
    }
    public ArrayList<Particle> getParticlesList()
    {
        return this.particlesList;
    }
    
   
    public static ArrayList<Integer> generateRandomSolution(){
        ArrayList<Integer> randomSol=new ArrayList<Integer>();
        Random rand;
        for(int i=0;i<SATEvaluator.nbrVariables;i++){
            rand=new Random();
            randomSol.add(rand.nextInt(2));
  
        }
        return  randomSol;
    }
    
    //Initializing the group of particles
    public void initialize(SATEvaluator evaluator,  int nbParticles)
    {   System.out.println("Initializing ... \n ");
        ArrayList<Integer> position;
        
        ArrayList<Integer> Gbest = new ArrayList<>();
        Particle P;
        for(int i =0; i<nbParticles;i++)
        {
            position = generateRandomSolution();
            P = new Particle();
            P.setVelocity(rand.nextInt(position.size()));
            P.setPosition(position);
            P.setPBest(position);
            Particle tempPart = new Particle();
            tempPart.setPosition(Gbest);
            if(evaluator.evaluateParticle(P)>evaluator.evaluateParticle(tempPart))
            {
                Gbest = P.getPosition() ;
            }
            
            this.particlesList.add(P);            
        }
        
        this.setGBest(GBest);
        
        for(int j =0;j<particlesList.size();j++)
        {
           System.out.println(particlesList.get(j).getPosition());
        }
    }
    
    //RETURN THE PARTICLE WHICH IS A SOLUTION TO THE PROBLEM IF IT'S EXISTS
    public Particle containsGoalParticle(SATEvaluator evaluator)         
    {
        for(int i=0; i<this.particlesList.size(); i++)
        {
            if(evaluator.particleIsGoal(this.particlesList.get(i)))
            {
                return this.particlesList.get(i);
            }
        }
        return null;
    }
    
    
    //RETURNS THE PARTICLE THAT HAS THE BEST EVALUATION 
    public Particle getBestParticle(SATEvaluator evaluator)
    {
        int bestPartIndex = 0;
        int bestEvaluation = 0;
        for(int i =0; i<this.particlesList.size();i++)
        {
            if((evaluator.evaluateParticle(this.getParticlesList().get(i)))>bestEvaluation)
            {
                bestPartIndex=i;
                bestEvaluation= evaluator.evaluateParticle(this.getParticlesList().get(i));               
            }
        }
        return this.getParticlesList().get(bestPartIndex);
    }
}
