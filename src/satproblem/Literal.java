
package satproblem;

import java.util.ArrayList;
import java.util.Random;

/** Classe Literal représentant la structure de Litéral , utilisé dans la construction des clauses
 *
 * @author Selmane
 */
public class Literal {
    private boolean val;
    private int index; // numéro d'ordre dans la solution choisie
    
    public Literal(Literal l){
        this.val=l.val;
    }
    public Literal(boolean val,int i){
        this.val=val;
        this.index=i;
    }
    
                //Getters & Setters
    public boolean getVal(){
        return val;
    }
    public int getIndex(){
        return index;
    }
    
    
    public void setVal(boolean value){
        this.val=value;
    }
    public void setIndex(int index){
        this.index=index;
    }
    
    /**
     *  Méthode qui retourne le contraire du litéral
     * @return La négation du  litéral
     */
    public boolean contraire(){
        return !val;
    }
    
    
    
    /**
     * Méthode qui compare si ce litéral a la même valeur avec un autre
     *   utilisée dans la satisfaction des clauses
     * @param l : Litéral à comparer avec
     * @return  true.false
     */
    
    public boolean hasSameValue(Literal l){
        return l.getVal()==this.val;
    }
    /**
     * Méthode qui choisi un litéral au hasard parmi un ensemble de litéraux
     * @param ListeLiteraux : un ensemble de litereaux dont on choisi un au hasard
     * @return le litéral aléatoire choisi
     */
    public static Literal randomLiteral(ArrayList<Literal> ListeLiteraux){
        
        int random=new Random().nextInt(ListeLiteraux.size());
        return ListeLiteraux.get(random);
    }
    
    
    public int nbrClausesSatisfiable(ArrayList<Clause> listeClauses){
       // Must be implemented
        return 0;
        
    }
    
    
    
    public String toString(){
        return val?"x"+this.index:"!"+"x"+this.index;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Literal other = (Literal) obj;
        if (this.val != other.val) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }
    
    
    
}
