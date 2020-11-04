
package ACO.Bases;

import ACO.sat.SATAnt;
import ACO.sat.SATInstance;
import ACO.sat.SATSolution;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



/**
 *
 * @author PC
 */
public abstract class Ant implements Comparable<Ant>{
    
    
    protected SATSolution solution;
    protected SATInstance instance;

    public Ant(SATInstance instance) {
        this.instance = instance;
        this.solution=new SATSolution(instance);
    }
    
    
    
    
    /**
     * Méthode qui génère aléatoirement une fourmi
     * @param instance L'instance du problème SAT à associer au fourmis.
     * @return Une fourmi aléatoire .
     */
    public static Ant generateRandomAnt(SATInstance instance){
        SATAnt ant=new SATAnt(instance);
        ant.setSolution(SATSolution.generateRandomSolution(instance));
        return ant;
        
    }
    
    
    
    
    /**
     * Méthode qui choisit aléatoirement une Clause (son numéro/indice) insatisfiable depuis une liste
     * @param listeClause Liste des clauses 
     * @return L'indice/numéro de la clause non satisfiable choisie
     */
    public int pickRandomClause(ArrayList<Integer> listeClause){
        return ThreadLocalRandom.current().nextInt(listeClause.size());
        
    }
    
    
    /**
     * Méthode qui calcule la probabilité relative à l'évaporation de phéromone pour un litéral donné.
     * @param literal
     * @param negationFlag
     * @return La probabilité d'évaporation associée.
     */
    public double calculateProbability(int literal, int negationFlag){
        return calculateWeightedPheromon(literal, negationFlag) / calculateSumOfWeightedPheromon(literal);
    }
    
    
    
    /**
     * Méthode qui calcule le dénominateur de l'expression de calcul de la probabilité pour cette fourmi .
     * @param literal Le litéral à calculer pour .
     * @param negationFlag 0 : si le litéral ,  1 : si sa négation (deuxième colonne)
     * @return La probabilité associée .
     */
    public double calculateWeightedPheromon(int literal, int negationFlag){
        double powerAlpha=Math.pow(instance.getNbrClauses() - instance.getLiteralSatisfictionTable()[literal][negationFlag].size()
                , instance.getPhromon().getAlpha());
        
        double powerBeta=Math.pow(instance.getNbrClauses() - instance.getLiteralSatisfictionTable()[literal][negationFlag].size()
                , instance.getPhromon().getBeta());
        
        return powerAlpha * powerBeta;
    }
    
    /**
     * Méthode qui calcule la somme des probabilité pondérés d'un litéral donné .
     * @param literal
     * @return La somme des probabilités
     */
    public double calculateSumOfWeightedPheromon(int literal){
        
        return calculateWeightedPheromon(literal,0)+calculateWeightedPheromon(literal,1);
    }
    
    
    
    
    /**
     * Méthode qui construit une solution pour la fourmi courante.
     */
    public abstract void buildSolution();
    
    /**
     * Méthode qui recherche les voisins de la fourmi courante en explooitant les fourmis en voisinage.
     */
    public abstract void searchVoisins();
    
    
    /**
     * Méthode pour améliorer la solution actuelle de cette fourmi afin d'améliorer la pheromone à ejecter .
     */
    public abstract void improveSolution(int iterationLimit);
    
    
    
    
    
    
                    //Getters & Setters

    public SATSolution getSolution() {
        return solution;
    }

    public void setSolution(SATSolution solution) {
        this.solution = solution;
    }

    public SATInstance getInstance() {
        return instance;
    }

    public void setInstance(SATInstance instance) {
        this.instance = instance;
    }
    
    
}
