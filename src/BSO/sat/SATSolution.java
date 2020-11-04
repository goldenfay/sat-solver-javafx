
package BSO.sat;

import GraphSearchs.SAT.SATNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import satproblem.Clause;
import satproblem.Literal;

/**
 * Class qui représente l'objet Solution de problème SAT(point de recherche ).
 * @author PC
 */
public class SATSolution implements Comparable{
    private BitSet config;
    private SATInstance instance;

    
    
    public SATSolution(SATInstance instance) {
        this.instance = instance;
        this.config=new BitSet(instance.getNbrVariables());
    }
    
    
    
    
    
    /**
     * Méthode statique(static) qui génère une solution aléatoire.
     * @param instance L'instance de SAT à génerer pour.
     * @return La solution aléatoire générée.
     */
    public static SATSolution generateRandomSolution(SATInstance instance){
        
        SATSolution SATSol=new SATSolution(instance);
        Random random=new Random();
        for (int i = 0; i < SATSol.size(); i++)
            SATSol.setIndexValue(i, random.nextInt(2)==1);
        
        
        
        return SATSol;
    }
    
    
    
    
    /**
     * Méthode qui celcule la distance(diversity) entre l'instance actuele et celle donnée en paramètres.
     * @param solution La solution à comparer avec.
     * @return La distance(diversity) en terme de nombre de bits différents entre les 2 solutions.
     */
    public int distance(SATSolution solution)
    {
        BitSet dis = clone().getConfig();
        dis.xor(solution.getConfig());
        return dis.cardinality();
    }
    
    /**
     * Méthode qui fait une copie de l'instance actuelle
     * @return une copie de l'instance
     */
    public SATSolution clone()
    {
        BitSet copyBitset = new BitSet(config.length());
        copyBitset.clear();
        copyBitset.or(config);
        SATSolution solution = new SATSolution(instance);
        solution.config = copyBitset;
        return solution;
    }

    public ArrayList<Integer> asList(){
        ArrayList<Integer> liste=new ArrayList<Integer>();
        for(int i=0;i<this.size();i++) liste.add(this.getBit(i)?1:0);
        
        return liste;
    }
    /**
     * Méthode qui inverse le bit d'index index
     * @param index: le bit à inverser
     * @return  cette instance avec le bit d'index index inversé
     */
    public SATSolution invertBit(int index)
    {
        config.flip(index);
        return this;
    }

    public SATSolution setBit(int index)
    {
        config.set(index);
        return this;
    }

    public SATSolution clearBit(int index)
    {
        config.clear(index);
        return this;
    }

    public SATSolution setIndexValue(int index, boolean value)
    {
        if(value)
            config.set(index);
        else
            config.clear(index);
        return this;
    }

    public boolean getBit(int index)
    {
        return config.get(index);
    }

    public int size()
    {
        return instance.getNbrVariables();
    }
    
    
    public int getNBROfClausedSatified(){
        int nbr=0;
        
        for(int[] line:instance.getMATRICE_CNF()){
            
            /*Clause cli=new Clause("C",Arrays.asList(line));
            ArrayList<Literal> literaux=new ArrayList<Literal>();
            
            for(int i=0;i<config.size();i++) literaux.add(new Literal((config.get(i)),Math.abs(config.get(i)?1:0)-1));
            
            if(cli.estClauseSatisfiable(literaux)) nbr++;
            */
            for(int i=0;i<line.length;i++){
                if( (config.get( Math.abs(line[i])-1 )&& line[i]>0  ) ||
                      (!config.get( Math.abs(line[i])-1 )&& line[i]<0  )  ) 
                    nbr++;
            }
            
        }
        
        
        return nbr;
    }
    
    
    
                    // Getters & Setters
    
    public double getEvaluation(){
        
        
        return 0;
    }

    public BitSet getConfig() {
        return config;
    }

    public void setConfig(BitSet config) {
        this.config = config;
    }

    public SATInstance getInstance() {
        return instance;
    }

    public void setInstance(SATInstance instance) {
        this.instance = instance;
    }

    
    
    
    
    
    @Override
    public int compareTo(Object o) {
        try{
            SATSolution sol=(SATSolution)o;
            return ((Double)this.getEvaluation()).compareTo(sol.getEvaluation());
            
        }catch(ClassCastException e){
            System.err.println("Instance passé en paramètre n'est pas de 'SATSolution'");
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        String desc="";
        System.out.println(this.size() +"    "+this.config.size());
         for(int i=0;i<this.size();i++) desc+=config.get(i)?"x"+(i+1):"!x"+(i+1)+(i!=config.size()-1?", ":" \n");
        return desc;
    }
    
    
    
    
    
    
    
    
}
