
package BSO.bases;

import BSO.sat.SATBee;
import BSO.sat.SATInstance;
import BSO.sat.SATSolution;
import BSO.tabu.TabuList;
import GUI.Controllers.Execution_GUIController;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.scene.text.Text;
import satproblem.Clause;
import satproblem.ExecutionController;

/**
 *
 * @author PC
 */
public class BSOEvaluator extends BSOMain<SATSolution>{

    private SATInstance instance;
    private int nbrBees;
    private int flip;
    
    
    
    
    public BSOEvaluator(SATInstance instance,TabuList tabulist,DanceList dances, long maxIterations, long MAXSIZE) {
        super(dances,tabulist, maxIterations, MAXSIZE);
        this.instance=instance;
        this.nbrBees=5;
        this.flip=5;
        
    }
    public BSOEvaluator(SATInstance instance,TabuList tabulist,DanceList dances, long maxIterations, long MAXSIZE, int nbrBees,int flip) {
        super(dances,tabulist, maxIterations, MAXSIZE);
        this.instance=instance;
        this.nbrBees=nbrBees;
        this.flip=flip;
        
    }

    
    
    
    
    
    @Override
    ArrayList<Bee<SATSolution>> getPointsToSearch(Object solution) {
        SATSolution sol=(SATSolution) solution;
        //System.out.println(sol.size()+"  "+sol.getConfig().size() );
        ArrayList<Bee<SATSolution>> list = new ArrayList<Bee<SATSolution>>();
        Bee effectiveBee=new SATBee(instance);
        effectiveBee.setZoneSearch(sol);
        list.add(effectiveBee);
        for (int i = 0; i < flip && i < nbrBees; i++) {
            SATSolution newSearchPoint = sol.clone();
            int p = 0;
            do {
                newSearchPoint.invertBit(p*flip+i);
                p++;
            }while(p*flip+i < newSearchPoint.size());
            
            System.out.println(newSearchPoint);
            effectiveBee.setZoneSearch(newSearchPoint);
            list.add(effectiveBee);
        }
        return list;
    }

    
    
    
    @Override
    public boolean isValideSolution(SATSolution solution){
        
       for(int i=0;i<instance.getNbrClauses();i++){
        
           ArrayList<Integer> literalList=new ArrayList<Integer>() ;
           for(int j=0;j<instance.getMATRICE_CNF()[i].length;j++) 
               if(instance.getMATRICE_CNF()[i][j]!=0)literalList.add(instance.getMATRICE_CNF()[i][j]);
           Clause ci=new Clause("C"+(i+1),literalList );
           
           if(!ci.estSatisfiable(solution.asList())) return false;
        }
       return true;
    }
    
    
    
    
    
    
    public SATSolution lookForSolution(Bee initialBee, Execution_GUIController execController){
        
        SATSolution sol = (SATSolution) initialBee.initialize();
        SATSolution bestSolution = sol;
        double evaluationBest = dances.evaluate(sol);
        while (!isFinished(sol))
        {
            tabuList.add(sol);
            ArrayList<Bee<SATSolution>> bees = this.getPointsToSearch(sol);

            for(Bee<SATSolution> bee : bees){
               // if(bee.search((int)maxIterations,(int)MAXSIZE)==null) System.out.println("kjfkjdfhkjhflkjdhlkfjhdfkgjhlkj");
                 dances.add( bee.search((int)maxIterations,(int)MAXSIZE) );
            //System.out.println((SATBee)bee);
            }
               

            do {
                sol = (SATSolution) dances.getBest();
                //if(isValideSolution((SATSolution) sol)) System.out.println("Solution found !! \n \n \n \n \n ");
                execController.setBesSolutionFound(bestSolution);
                execController.setCurrectSol_text(new Text(sol.toString()));
            }while (sol != null && tabuList.contains(sol));

            if(sol == null)
                sol = (SATSolution) initialBee.initialize();

            double evaluation = dances.evaluate(sol);
            if(evaluation < evaluationBest) {
                bestSolution = sol;
                evaluationBest = evaluation;
            }
            this.nbrIterations++;
        }
        return bestSolution;

      
    }
    
    
    
    
    
    
                    // Getters and Setters
    public SATInstance getInstance() {
        return instance;
    }

    public void setInstance(SATInstance instance) {
        this.instance = instance;
    }

    public int getNbrBees() {
        return nbrBees;
    }

    public void setNbrBees(int nbrBees) {
        this.nbrBees = nbrBees;
    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    

    
    
    
    
    
    
    
    
    
}
