
package BSO.bases;

/**
 *
 * @author PC
 */
public interface DanceList<E> {
    
    /**
     * Méthode qui ajoute une solution à la liste Dances
     * @param solution 
     */
    public abstract void add(E solution);
    
    
    
    /**
     * Méthode qui trouve la meilleur solution dans la table Dances.
     * @return La meilleur solution dans la table Dances .
     */
    public abstract E getBest();
    
    
    
    /**
     * Méthode qui évalue la qualité d'une solution donnée en paramètres .
     * @param solution
     * @return Une valeur réel représentant la valeur d'evaluation de la solution .
     */
    public abstract double evaluate(E solution);
}
