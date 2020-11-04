
package ACO.sat;

import ACO.Bases.Ant;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author PC
 */
public class SATAnt extends Ant{

    private boolean[] traitement;
    private double probability;
    
    
    public SATAnt(SATInstance instance, double probability) {
        super(instance);
        traitement=new boolean[instance.getNbrVariables()];
        this.probability=probability;
    }
    
    public SATAnt(SATInstance instance) {
        super(instance);
        traitement=new boolean[instance.getNbrVariables()];
        this.probability=0.5;
    }

    
    
    
    
    @Override
    public void buildSolution() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchVoisins() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void improveSolution(int iterationLimit) {
        
        
        for(int i=0;i<iterationLimit;i++){
             //duppliquer la solution actuelle
            SATSolution EffectiveSolution=this.getSolution().clone();
            
             // Si cette solution n'est pas valide ou meilleur :
            if(this.getSolution().getNBROfClausedSatified()!=this.getInstance().getNbrClauses()){
                
                Random random=new Random();
                 //récupérer la liste des clauses non satisfiables
                ArrayList<Integer> unsatisfiedClauses=this.getSolution().getUnsatisfiedClauseList();
                
                  //Choisir une clause au hasard parmi eux
                int clauseToModify=pickRandomClause(unsatisfiedClauses);
                  // Choisir un litéral au hasard pour le changer
                int variableToChange=random.nextInt(3);
                  
                EffectiveSolution.invertBit(variableToChange);
                 // Si la nouvelle solution est meuilleur que celle-ci , l'affecter à la solution de la fourmi actuelle.
                if(EffectiveSolution.getEvaluation()<this.getSolution().getEvaluation())
                    this.setSolution(EffectiveSolution);
                
            }
            
            
            
        }
    }

    @Override
    public int compareTo(Ant o) {
        
        return (int) (this.getSolution().getEvaluation() - o.getSolution().getEvaluation());
    }
    
    
    
    
                // Getters & Setters

    public boolean[] getTraitement() {
        return traitement;
    }

    public void setTraitement(boolean[] traitement) {
        this.traitement = traitement;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
    
    
}
