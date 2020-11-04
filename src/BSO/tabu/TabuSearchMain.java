
package BSO.tabu;


import BSO.sat.SATInstance;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public abstract class TabuSearchMain<E> {
    
    protected SATInstance instance;
    protected TabuList<E> tabuList;
    protected long maxIterations;
    protected long nbrIterations ;
    protected boolean searchFinished = false;
    
    

    public TabuSearchMain(SATInstance instance,TabuList<E> tabuList) {
        this.instance=instance;
        this.tabuList = tabuList;
        nbrIterations =0;
    }
    
    
    
    
    /**
     * Méthode qui renvoie les voisins d'une solution (point de recherche) donnée.
     * @param pointSolution La solution à traiter
     * @return liste des voisins de la solution données en paramètres
     */
    public abstract ArrayList<E> getVoisins(E pointSolution);
    
    /**
     * Méthode qui détermine si la recherche est terminée
     * @param solution
     * @return True si Maxiteration atteint ou solution optimale trouvé , False sinon
     */
    public abstract boolean finish(E solution);
    
    /**
     * Méthode d'évaluation (fitness function) pour une solution donnée.
     * @param solution
     * @return l'evaluation de la solution donnée en paramètres.
     */
    public  abstract double evaluate(E solution);
    
    
    /**
     * Méthode qui recherche une solution optimal pour le problème
     * @param origin :  la solution initial pour commencer 
     * @return la solution la plus optimal possible trouvée.
     */
    public E search(E origin)
    {
        E bestSol = origin;
        E currentBest = origin;
        tabuList.add(origin);
        while(!finish(currentBest))
        {
            ArrayList<E> neighbors = getVoisins(currentBest);

            for (E neighbor : neighbors)
                if(!tabuList.contains(neighbor) &&(currentBest == null || evaluate(neighbor) < evaluate(currentBest)))
                    currentBest = neighbor;

            if(evaluate(currentBest) < evaluate(bestSol))
                bestSol = currentBest;

            tabuList.add(currentBest);
        }
        return bestSol;
    }

    
    
    
                    // Getters & Setters
    public TabuList<E> getTabuList() {
        return tabuList;
    }

    public void setTabuList(TabuList<E> tabuList) {
        this.tabuList = tabuList;
    }

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
