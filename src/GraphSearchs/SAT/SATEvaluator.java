
package GraphSearchs.SAT;

import PSO1.Particle;
import geneticAlgorithm.Individual;
import GraphSearchs.Bases.*;
import GraphSearchs.MemoriesStructures.Memory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import satproblem.Clause;
import satproblem.Literal;
import static satproblem.SATPROBLEM.MatriceCNF;

/**
 *
 * @author PC
 */
public abstract class SATEvaluator {
   
    public Estimator estimator;
    public static int nbrVariables;
    public static int CURRENT_DEPTH=0;
    public HashMap<Integer,Double> map; // Paires : index du neod , valeur de la fonction f

    
    
    public SATEvaluator(int nbrVariables) {
        this.nbrVariables = nbrVariables;
    }
    
    
    
    
    public boolean estBut(Node node){
        SATNode sNode=(SATNode)node;
        return(isValideSolution(sNode.getConfig()));
    }
    
    
                    /* Abstract Methodes */
    
    
    
    public abstract double evaluate(Node node);
    
    public abstract int indexOfNext();

    
    
    
    
    public static boolean isValideSolution(ArrayList<Integer> solution){
        
        for(int i=0;i<MatriceCNF.length;i++){
            
            ArrayList<Integer> templitereaux=new ArrayList<Integer>();
            for( int el:MatriceCNF[i]) templitereaux.add(el);
            Clause ci=new Clause("C"+(i+1), templitereaux);
            //for(int j=0;j<MatriceCNF.get(i).size();j++) ci.getLitereaux().add(new Literal(MatriceCNF.get(i).get(j)>0,Math.abs(MatriceCNF.get(i).get(j))));
            
            //System.out.println(ci);
            /*ArrayList<Literal> sol=new ArrayList<Literal>();
            for(int j=0;j<SATEvaluator.nbrVariables;j++) sol.add(new Literal(solution.get(j)>0,j));
            */
            if(!ci.estSatisfiable(solution)){
                return false;
            }
            //else System.out.println(ci.getClauseName()+" satisfiable");
        }
        
        
        return true;
    }
    
    
    
    public static ArrayList<Integer>[] LireFichier(String fileName) throws IOException{
         ArrayList<Integer>[] AllClauses;
        
        File file = new File(fileName); 
  
        BufferedReader br = new BufferedReader(new FileReader(file)); 

        String st=br.readLine();; 
        ArrayList<Integer> clause=new ArrayList<Integer>();
        
        while (st.charAt(0) != 'p') {
            st = br.readLine();
        }
        
        nbrVariables=Integer.parseInt(st.split("\\s+")[2]);
        
        AllClauses=(ArrayList<Integer>[])new ArrayList[Integer.parseInt(st.split("\\s+")[3])];
        int index=0;
        while ((st = br.readLine()) != null) {
            String lit="";
            
            for(int i=0;i<st.length();i++){
                if(st.charAt(i)==' '&& !st.isEmpty() || ( st.charAt(i)=='0' && i==st.length()-1)){
                    
                    try{ 
                        if(lit!="")clause.add(Integer.parseInt(lit));
                        lit="";
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                } else lit+=st.charAt(i)+"";
            }
            
            if(!clause.isEmpty()) AllClauses[index]=new ArrayList<Integer>((Collection<? extends Integer>) clause.clone());
            clause.clear();
            index++;
        }
        
        
          return AllClauses;

    }
    
    
    
                    /**
                     * Getters & Setters 
                     */
    
    public Estimator getEstimator() {
        return estimator;
    }

    public void setEstimator(Estimator estimator) {
        this.estimator = estimator;
    }

    public static int getCURRENT_DEPTH() {
        return CURRENT_DEPTH;
    }

    public static void setCURRENT_DEPTH(int CURRENT_DEPTH) {
        SATEvaluator.CURRENT_DEPTH = CURRENT_DEPTH;
    }

    public HashMap<Integer, Double> getMap() {
        return map;
    }
    

    public void setMap(HashMap<Integer, Double> map) {
        this.map = map;
    }

    public int getNbrVariables() {
        return nbrVariables;
    }

    public void setNbrVariables(int nbrVariables) {
        this.nbrVariables = nbrVariables;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
                    /************************************************************************************/
                    /*******************METHODES ADDED BY ISLAM*************************/
                    /************************************************************************************/
    
   /*
    public boolean particleIsGoal(Particle P)
    {
        for(int i=0;i<MatriceCNF.length;i++){
            Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
            if(!ci.estSatisfiable(P.getPosition())){
                return false;
            }
        }
        return true;
    }
    
    */
    public boolean individualIsGoal(Individual indi)
    {
        for(int i=0;i<MatriceCNF.length;i++){
            Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
            if(!ci.estSatisfiable(indi.getGeneticData())){
                return false;
            }
        }
        return true;
    }
    //FUNCTION USED TO COMPARE THE HERUSTICAL VALUE OF TWO NODES
    public int compare(Node node1,Node node2)
    {
        double value1 = this.evaluate(node1);
        double value2 = this.evaluate(node2);
        if(value1<value2)
        {
            return -1;
        }else
        {
            if(value1>value2){
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
    //ORDONNE L'ENSSEMBLE OPEN EN BASANT SUR L'HEURISTIQUE DES NOEUDS SELON L'ORDRE D'ÉCROISSANT
    public Memory orderList(Memory list) throws InstantiationException, IllegalAccessException
    {
            Memory newList;
            newList = (Memory)list.getClass().newInstance();
        
        int j=0;
        while(!list.isEmpty() && list.size()>1)
        {
            for(int i = 0;i<list.size()-1;i++)
            { 
                if(compare(list.get(i),list.get(i+1))==-1)
                {
                   j=i;
                }else
                {
                    j=i+1;
                }
            }
             newList.add(list.get(j));
                    list.remove(j);
        }
        newList.add(list.get(0));
        return newList;
       
    }
    
                    /* Abstract Methodes */
    
    
    
    

    
    public double evaluateIndividual(Individual indi)
    {
        double fitValue = 0;
        for(int i=0;i<MatriceCNF.length;i++)
        {
         Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
         if(ci.estSatisfiable(indi.getGeneticData()))
         {
            fitValue++;
         }
        }
        indi.setEvaluationResult(fitValue*100/MatriceCNF.length);
        return fitValue*100/MatriceCNF.length;
    }
    
    public int evaluateParticle(Particle P)
    {
        int fitValue = 0;
        for(int i=0;i<MatriceCNF.length;i++)
        {
         Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
         if((P.getPosition()).size()==0)
         {
             return 0;
         }else
         {
            if(ci.estSatisfiable(P.getPosition()))
            {
               fitValue++;
            } 
         }
            
        }
        return fitValue;
    }
    
    
  public boolean particleIsGoal(Particle P)
    {
        for(int i=0;i<MatriceCNF.length;i++){
            Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
            if(!ci.estSatisfiable(P.getPosition())){
                return false;
            }
        }
        return true;
    }
}
