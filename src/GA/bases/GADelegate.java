
package GA.bases;

/**
 *
 * @author PC
 */
public interface GADelegate {
    
    /**
     * Méthode de 'fitness' (objective function) qui évalue l'appropriété d'un individu .
     * @param individual: L'individu à évaluer .
     * @return l'évaluation de l'individu donné en paramètres.
     */
    public  abstract double calculateFitness(Individual individual);
    
    
    /**
     * Méthode qui permet de faire la selection d'individu depuis une populationn
     * @param population: La population dont l'individu sera choisi
     * @param selectionMethode: Méthode de selection(roulette wheel ...).
     * @return / L'individu selectionné selon les critère abordés.
     */
    public abstract Individual selectParent(Population population,String selectionMethode);
    
    
    /**
     * Méthode qui fait le croisement d'une population, en se basant sur une taux de croisement.
     * @param population: La population à traiter
     * @return La population apres avoir établit le croisement.
     */
    public abstract Population crossoverPopulation(Population population);
    
    /**
     * Méthode dédiée au traitement de la mutation d'une population en se basant sur un taux de mutation.
     * @param population: La population à traiter
     * @return  La population après la mutation établie.
     */
    public abstract Population mutatePopulation(Population population);
}
