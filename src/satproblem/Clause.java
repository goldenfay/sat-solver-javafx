package satproblem;

import java.util.ArrayList;

/**
 *  Classe représentant la structure de clause (disjonction de litéreaux)
 * @author Selmane
 */
public class Clause {
    private ArrayList<Literal> litéreaux;
    private String clauseName;
    
    
   
    public Clause(Clause c){
        this.litéreaux=(ArrayList<Literal>) c.getLitereaux().clone();
        this.clauseName=c.getClauseName();
    }
    public Clause(String nom,ArrayList<Integer> litereaux){
        this.clauseName=nom;
        litéreaux=new ArrayList<Literal>();
        for(int i=0;i<litereaux.size();i++)
            litéreaux.add(new Literal(( (Integer)  litereaux.get(i)>0),Math.abs((litereaux.get(i) ))) );
        
    }
    
    
    
    public ArrayList<Literal> getLitereaux(){
        return litéreaux;
    }
    
    public String getClauseName(){
        return clauseName;
    }
    
    
    public Clause contraireClause(){
        Clause reverse=new Clause(this);
        ArrayList<Literal> liste = reverse.getLitereaux();
        for(int i=0;i<liste.size();i++){
            liste.get(i).setVal(liste.get(i).contraire());
        } 
        return reverse;
    }
    
    /**
     * 
     * @param listeLitChoisi : liste de litéreaux choisi comme solution
     * @return 
     */
    public boolean estClauseSatisfiable(ArrayList<Literal> listeLitChoisi){
        for(Literal lit:litéreaux){
            
            if(lit.hasSameValue(listeLitChoisi.get(lit.getIndex()-1))) return true;
        }
        return false;
    }
    
    
    /**
     * 
     * @param listeLitChoisi
     * @return 
     */
    
    public boolean estSatisfiable(ArrayList<Integer> listeLitChoisi){
        for(Literal lit:litéreaux){
            
            if( (lit.getVal()&&listeLitChoisi.get(lit.getIndex()-1)>0) || (lit.getVal()==false&&listeLitChoisi.get(lit.getIndex()-1)==0) ) return true;
        }
        return false;
    }
    
    
    @Override
    public String toString(){
        return this.clauseName+"= "+this.litéreaux.toString();
    }
    
    
}
