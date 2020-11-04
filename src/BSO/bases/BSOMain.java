
package BSO.bases;

import BSO.sat.SATBee;
import BSO.sat.SATSolution;
import BSO.tabu.TabuList;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public abstract class BSOMain<E>  extends BSO{
    
    protected long maxIterations;
    protected long nbrIterations;
    protected final long MAXSIZE;
    protected boolean searchFinished=false;

    public BSOMain( DanceList<E> dances,TabuList tabulist,long maxIterations, long MAXSIZE) {
        super(dances,tabulist);
        this.maxIterations = maxIterations;
        this.MAXSIZE = MAXSIZE;
        this.nbrIterations=0;
    }
    
    
    
    
    public abstract boolean isValideSolution(SATSolution solution);

    @Override
    public E lookForSolution(Bee initialBee){
        
        E sol = (E) initialBee.initialize();
        E bestSolution = sol;
        double evaluationBest = dances.evaluate(sol);
        while (!isFinished(sol))
        {
            tabuList.add(sol);
            ArrayList<Bee<E>> bees = this.getPointsToSearch(sol);

            for(Bee<E> bee : bees){
               // if(bee.search((int)maxIterations,(int)MAXSIZE)==null) System.out.println("kjfkjdfhkjhflkjdhlkfjhdfkgjhlkj");
                 dances.add( bee.search((int)maxIterations,(int)MAXSIZE) );
            //System.out.println((SATBee)bee);
            }
               

            do {
                sol = (E) dances.getBest();
                if(isValideSolution((SATSolution) sol)) System.out.println("Solution found !! \n \n \n \n \n ");
                //System.out.println(sol);
            }while (sol != null && tabuList.contains(sol));

            if(sol == null)
                sol = (E) initialBee.initialize();

            double evaluation = dances.evaluate(sol);
            if(evaluation < evaluationBest) {
                bestSolution = sol;
                evaluationBest = evaluation;
            }
            this.nbrIterations++;
        }
        return bestSolution;
    }

    @Override
    public boolean isFinished(Object solution){
        
        return (this.nbrIterations >=maxIterations || this.searchFinished )|| isValideSolution((SATSolution) solution);
    }

    
    
    
    
    
    
    
                    //Getters and Setters
    public long getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(long maxIterations) {
        this.maxIterations = maxIterations;
    }

    public long getNbrIterations() {
        return nbrIterations;
    }

    public void setNbrIterations(long nbrIterations) {
        this.nbrIterations = nbrIterations;
    }

    public boolean isSearchFinished() {
        return searchFinished;
    }

    public void setSearchFinished(boolean searchFinished) {
        this.searchFinished = searchFinished;
    }
    
    
    
    
    
    
    
    
    
    
}
