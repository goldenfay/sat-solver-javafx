
package GraphSearchs.SAT;

import GraphSearchs.Bases.Node;
import java.util.ArrayList;
import java.util.ArrayList;
import satproblem.Clause;
import satproblem.Literal;
import satproblem.SATPROBLEM;
import GraphSearchs.Bases.Estimator;


/**
 *
 * @author PC
 */

public class SATNode extends Node{
    
    private ArrayList<Integer> config;

    public SATNode(Node parent, int depth, int index) {
        super(parent, depth, index);
    }

   
    
    
    @Override
    public ArrayList<Node> getSuccessors() {
        ArrayList<Node> successors=new ArrayList<Node>();
        ArrayList<Integer> child=(ArrayList<Integer>) this.getConfig().clone();
      
        for(int i=0;i<SATEvaluator.nbrVariables;i++)
        {
            child.set(i, child.get(i)-1<0?1:0);
            SATNode childNode=new SATNode(this,SATEvaluator.CURRENT_DEPTH+1, index+1);
            childNode.setConfig(child);
            childNode.setParent(this);


        
        if(!SATInstance.getOpen().contains(childNode) && !SATInstance.getClosed().contains(childNode)) successors.add(childNode);
        
        child=(ArrayList<Integer>) this.getConfig().clone();
       
        }
        return successors;

    }
    
    public ArrayList<Node> getSuccessorsBis(Estimator estimator)
    {
        ArrayList<Node> successors=new ArrayList<Node>();
        ArrayList<Integer> child=(ArrayList<Integer>) this.getConfig().clone();
      
        for(int k=0;k<SATEvaluator.nbrVariables;k++)
        {
            child.set(k, child.get(k)-1<0?1:0);
            SATNode childNode=new SATNode(this,SATEvaluator.CURRENT_DEPTH+1, index+1);
            childNode.setConfig(child);
        
        if(!SATInstance.getOpen().contains(childNode) && !SATInstance.getClosed().contains(childNode)) successors.add(childNode);
        else
        {
            if((estimator.estimate(childNode)+childNode.getDepth())!=0)
            {
              if(SATInstance.getOpen().contains(childNode))
                {
                    for(int i=0;i<SATInstance.getOpen().size();i++)
                    {
                        double childNodeValue = (estimator.estimate(childNode)+childNode.getDepth());
                        double openNodeValue = (SATInstance.getOpen().get(i).getGVal()+SATInstance.getOpen().get(i).getHVal());
                        if(SATInstance.getOpen().get(i).equals(childNode) && childNodeValue<openNodeValue )
                        {
                          SATInstance.getOpen().remove(i);
                          successors.add(childNode);
                          break;
                        }
                    }
                }
              else
                {
                    for(int i=0;i<SATInstance.getClosed().size();i++)
                    {
                        double childNodeValue = (estimator.estimate(childNode)+childNode.getDepth());
                        double openNodeValue = (SATInstance.getClosed().get(i).getGVal()+SATInstance.getClosed().get(i).getHVal());
                        if(SATInstance.getClosed().get(i).equals(childNode) && childNodeValue<openNodeValue )
                        {
                          successors.add(childNode);
                          break;
                        }
                    }
                }
            }            
        }
        child=(ArrayList<Integer>) this.getConfig().clone();
        }
        return successors;
    }
    
    public int getNbrOfSatisfiedClauses(){
        int nbr=0;
        
        for(ArrayList<Integer> line:SATPROBLEM.MatriceCNF){
            
            Clause cli=new Clause("C",line);
            ArrayList<Literal> literaux=new ArrayList<Literal>();
            
            for(int el:config) literaux.add(new Literal(el>=0?true:false,Math.abs(el)-1));
            
            if(cli.estClauseSatisfiable(literaux)) nbr++;
            
        }
        return nbr;
    }

    
    
    
                                /*  Getters & Setters    */
    public ArrayList<Integer> getConfig() {
        return config;
    }

    public void setConfig(ArrayList<Integer> config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object obj) {
        if(!( obj instanceof SATNode)) return false;
        SATNode node2=(SATNode)obj;
        
        for(int i=0;i<node2.getConfig().size();i++) if(this.config.get(i)!=node2.getConfig().get(i)) return false;
        
        return true;
        
    }
    
    
    
    
    
    
    
    
}
