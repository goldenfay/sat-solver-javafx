
package satproblem;

import GUI.Controllers.Blind_Execution_GUIController;
import GUI.Controllers.Execution_GUIController;

/**
 *
 * @author PC
 */
public interface ExecutionController {
    
    /**
     * Méthode qui permet de lancer l'execution de la recherche de solution .
     */
    public abstract void launch();
    
    
    /**
     * Méthode qui traite les modification et l'affichage des résultats d'execution
     * @param resultsController
     */
    public abstract void showResults(Execution_GUIController resultsController);

}
