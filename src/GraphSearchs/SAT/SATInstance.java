
package GraphSearchs.SAT;

import GraphSearchs.Bases.Node;
import GraphSearchs.MemoriesStructures.*;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class SATInstance<E> {
    
    
    public final int MAX_DEPTH;
    private static Memory Open;
    private static Memory Closed;
    private static  int nbrClauses , nbrVariables;
    private double [] frequences; // tableaux des fréquences d'apparitions de chaque Clause
    private SATEvaluator evaluator;
    private long startTime,endTime;

    public SATInstance(E stockage,int nbrClauses ,int nbrVariables,int MAX_DEPTH) {
       
            this.MAX_DEPTH = MAX_DEPTH;
            this.nbrClauses=nbrClauses;
            this.nbrVariables=nbrVariables;
            
            frequences=new double[nbrClauses];
            
            
             try {
            Open=(Memory) stockage.getClass().newInstance();
            
            Closed=new Memory() {
                @Override
                public Node getNext() {
                    return null;
                }
            };
        } catch (InstantiationException ex) {
            Logger.getLogger(SATInstance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SATInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
    
    
    
    
                        /*    Getters & Setters  */

    public static Memory getOpen() {
        return Open;
    }

    public void setOpen(Memory Open) {
        this.Open = Open;
    }

    public static Memory getClosed() {
        return Closed;
    }

    public void setClosed(Memory Closed) {
        this.Closed = Closed;
    }

    public double[] getFrequences() {
        return frequences;
    }

    public void setFrequences(double[] frequences) {
        this.frequences = frequences;
    }

    public SATEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(SATEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public static int getNbrClauses() {
        return nbrClauses;
    }

    public static void setNbrClauses(int nbrClauses) {
        SATInstance.nbrClauses = nbrClauses;
    }

    public static int getNbrVariables() {
        return nbrVariables;
    }

    public static void setNbrVariables(int nbrVariables) {
        SATInstance.nbrVariables = nbrVariables;
    }
    
    
    
    
    
    
    
    
}
