
package GraphSearchs.Bases;

/**
 *
 * @author PC
 */
public interface Estimator {
    
    /**
     * 
     * @param node
     * @return L'estimation de la valeur d'heuristique du noeud
     */
    public abstract double estimate(Node node);
}
