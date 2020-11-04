/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BSO.bases;

import BSO.tabu.TabuList;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public abstract class BSO<E> {
    
    protected DanceList<E> dances;
    protected TabuList<E> tabuList;

    public BSO(DanceList<E> dances,TabuList tabulist) {
        this.dances = dances;
        this.tabuList=tabulist;
    }
    
    
    
    /**
     * Méthode qui détermine si la recherche est terminée et le but voulu est atteint
     * @param solution La solution à vérifier si elle satisfait les conditions de fin de recherche
     * @return <code>True</code> : si la recherche est finie, <code>False</code> sinon .
     */
    abstract  boolean isFinished(E solution);
    /**
     * Méthode qui cherche les zonez de recherches (searchArea) à partir d'une solution
     * @param solution La solution origin
     * @return Une liste de solution (zone de recherche).
     */
    abstract  ArrayList<Bee<E>> getPointsToSearch(E solution);
    
    /**
     * 
     * @param initialBee
     * @return 
     */
    abstract  E lookForSolution( Bee<E> initialBee);
    
    
    
}
