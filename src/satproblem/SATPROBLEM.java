
package satproblem;

import BSO.bases.BSOEvaluator;
import BSO.bases.Bee;
import BSO.bases.DanceList;
import BSO.bases.HeapDanceList;
import BSO.sat.SATBee;
import BSO.sat.SATSolution;
import BSO.tabu.TabuList;
import GUI.Controllers.PSO_GUIController;
import GraphSearchs.Bases.Estimator;
import GraphSearchs.Bases.Node;
import GraphSearchs.MemoriesStructures.AstartMemory;
import GraphSearchs.MemoriesStructures.BreadthTransversMemory;
import GraphSearchs.MemoriesStructures.DepthTranversMemory;
import GraphSearchs.MemoriesStructures.Memory;
import javafx.application.Platform;
import GraphSearchs.SAT.SATEvaluator;
import GraphSearchs.SAT.SATInstance;
import GraphSearchs.SAT.SATNode;
import geneticAlgorithm.GAInstance;
import geneticAlgorithm.Individual;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;
import PSO1.PSOInstance;
import PSO1.Particle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.scene.control.Label;

/**
 *
 * @author PC
 */
public class SATPROBLEM {

    
    public static ArrayList<Integer>[] MatriceCNF;
    
    public static ArrayList<ArrayList<Integer>> Closed=new ArrayList<ArrayList<Integer>>();
    
    public static String inputFile = "ressources\\Benchmarks\\uf50-0996.cnf";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
        try {
           // LireFichier(inputFile,MatriceCNF);
            
            /*boolean solutionFound=false;
            while(!solutionFound){
                
                ArrayList<Literal> sol=generateSolution();
                
                if(!Closed.containsAll(sol)){
                    System.out.print("Current solution : "+sol.toString());
                    Closed.add(new ArrayList<>());
                    for(Literal el:sol)Closed.get(Closed.size()-1).add(el.getIndex());
                    
                    if(isValideSolution(sol)) {
                        System.out.println("Solution found ! ");
                        solutionFound=true;
                    }
                    else {
                        System.out.println("    Bad Solution! ");
                    }
                }
            }
            for(int i=0;i<MatriceCNF.size();i++){
                for(int z:MatriceCNF.get(i)) System.out.print(z+" ");
                System.out.println(MatriceCNF.size());
            
            
            }*/
            
            //BreathTransverseSearche(10);
            
            //DepthTransverseSearch();
           // BSO();
        } catch (Exception ex) {
            Logger.getLogger(SATPROBLEM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public static void LireFichier(String fileName, ArrayList<ArrayList<Integer>> AllClauses) throws IOException{
        File file = new File(fileName); 
  
        BufferedReader br = new BufferedReader(new FileReader(file)); 

        String st=br.readLine();; 
        ArrayList<Integer> clause=new ArrayList<Integer>();
        
        while (st.charAt(0) != 'p') {
            st = br.readLine();
        }
        
        while ((st = br.readLine()) != null) {
            String lit="";
            
            for(int i=0;i<st.length();i++){
                if(st.charAt(i)==' ' || ( st.charAt(i)=='0' && i==st.length()-1)){
                    
                    try{ 
                        if(lit!="")clause.add(Integer.parseInt(lit));
                        lit="";
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                } else lit+=st.charAt(i)+"";
            }
            AllClauses.add((ArrayList<Integer>) clause.clone());
            clause.clear();
            
        }
          

    }
    
    /**
     * Méthode qui teste si une Solution donnée(vecteur de litéreaux) est valide
     * @param solution
     * @return  True ou False
     */
    
    public static boolean isValideSolution(ArrayList<Literal> solution){
        
        for(int i=0;i<MatriceCNF.length;i++){
            Clause ci=new Clause("C"+(i+1), (ArrayList<Integer>) MatriceCNF[i].clone());
            if(!ci.estClauseSatisfiable(solution)){
                return false;
            }
        }
        
        
        return true;
    }
    
    
    
    /**
     * Méthode qui génère aléatoirement une solution pour l'instance SAT
     * @return : un ensemble(vecteur) de litéreaux
     */
    public static ArrayList<Literal> generateSolution(){
        ArrayList<Literal> randomSol=new ArrayList<Literal>();
        Random rand;
        for(int i=0;i<20;i++){
            rand=new Random();
            randomSol.add(new Literal(rand.nextBoolean(),i));
            
        }
        return  randomSol;
    }
    
    /**
     * Méthode qui génère aléatoirement une solution pour l'instance SAT
     * @return un ensemble de bit codant les litéreaux
     */
    public static ArrayList<Integer> generateRandomSolution(){
        ArrayList<Integer> randomSol=new ArrayList<Integer>();
        Random rand;
        for(int i=0;i<SATEvaluator.nbrVariables;i++){
            rand=new Random();
            randomSol.add(rand.nextInt(2));
            
        }
        return  randomSol;
    }
    
    
    public static void BreathTransverseSearche(HashMap<String,Text> GUIComponentsMap,int maxDepth) throws IOException{
        System.out.println("**********Starting BreathTransverseSearche *******");
        /**
         * Initialiser les configurations nécessaires : l'Instance,Evaluateur,Estimateur...
         */
        MatriceCNF=SATEvaluator.LireFichier(inputFile);

        SATEvaluator evaluator=new SATEvaluator(SATEvaluator.nbrVariables) {
            @Override
            public double evaluate(Node node) {
                estimator.estimate(node);
                return node.getGVal()+node.getHVal();
            }

            @Override
            public int indexOfNext() {
                return 0;
            }
        };
        //for(int i=0;i<MatriceCNF.size();i++) for(int j=0;j<MatriceCNF.get(i).size();j++) System.out.println(MatriceCNF.get(i).get(j));
        SATInstance<BreadthTransversMemory> instance=new SATInstance<>(new BreadthTransversMemory(),MatriceCNF.length,SATEvaluator.nbrVariables,maxDepth);

        Estimator estimator=new Estimator() {
            @Override
            public double estimate(Node node) {

                return 0;
            }
        };
        System.out.println("**********Starting BreathTransverseSearche*******");

        evaluator.setEstimator(estimator);

        instance.setEvaluator(evaluator);

        ArrayList<Integer> randomConfig=generateRandomSolution();
        SATNode root=new SATNode(null,0,0);
        root.setConfig(randomConfig);
        SATNode node=root;

        ArrayList<Node> path=null;
        instance.getOpen().addNode(node);
        int nbrNodes=0;
        int n1 = nbrNodes;
        Platform.runLater(() -> {
            GUIComponentsMap.get("nbrVisitedNodes").setText(n1+"");
        });
        Platform.runLater(() -> {
            GUIComponentsMap.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");
        });
        while(SATEvaluator.CURRENT_DEPTH<instance.MAX_DEPTH)
        {
              if(instance.getOpen().isEmpty())
              {
                  int n2 = nbrNodes;
                      Platform.runLater(() -> {
                          GUIComponentsMap.get("currentSolution").setText("No solution found for this instance");
                          GUIComponentsMap.get("depth").setText(n2+"");
                          GUIComponentsMap.get("solution").setText("No solution found for this instance");

                      });
                     break;
              }
              nbrNodes++;
              int n3 = nbrNodes;
              Platform.runLater(() -> {
            GUIComponentsMap.get("nbrVisitedNodes").setText(n3+"");
                });


             // Défiler le noeud de la liste Open
            node=(SATNode) instance.getOpen().getNext();
            instance.getOpen().remove(node);



            SATEvaluator.CURRENT_DEPTH=node.getDepth();
            System.out.println("Current Solution :");
            SATNode nd1 = node;
             Platform.runLater(() -> {
            GUIComponentsMap.get("currentSolution").setText(nd1.getConfig()+"");
                });
            for(int i=0;i<node.getConfig().size();i++) System.out.print(node.getConfig().get(i)+" ,"+(i==node.getConfig().size()-1?"\n":""));
            System.out.println("Depth :"+SATEvaluator.CURRENT_DEPTH);
            GUIComponentsMap.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");


            instance.getClosed().addNode(node);

            ArrayList<Node> children = node.getSuccessors();

            if(children==null || children.isEmpty()) {
                SATEvaluator.CURRENT_DEPTH--;
                GUIComponentsMap.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");
                System.out.println("Empty");
                continue;
            }

            for(Node n:children){
                 // Si un des noeud est un état but alors terminer et faire le chainage arrière du chemin parcouru
                if(evaluator.estBut(n)){
                     path = n.getPathToRoot();
                     System.out.println("Solution found ! \n Space Search to Solution  : ");
                     GUIComponentsMap.get("solution").setText(((SATNode)n ).getConfig()+"");
                     GUIComponentsMap.get("solutionFitness").setText(instance.getEvaluator().evaluate(node)*100/SATPROBLEM.MatriceCNF.length+"");
                     GUIComponentsMap.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");
                     GUIComponentsMap.get("finalDepth").setText(SATEvaluator.CURRENT_DEPTH+"");
                     GUIComponentsMap.get("nbrVisitedNodes").setText(nbrNodes+"");
                     System.out.println( ((SATNode)n ).getConfig());
                     break;
                }
                instance.getOpen().addNode(n);
            }
            //SATEvaluator.CURRENT_DEPTH++;


        }

        System.out.println("NBR NOdes : "+nbrNodes);
        int n4 = SATEvaluator.CURRENT_DEPTH;
        int n5 = nbrNodes;
         Platform.runLater(() -> {

                          GUIComponentsMap.get("solution").setText("No solution found for this instance at this depth");
                          GUIComponentsMap.get("depth").setText(n4+"");
                          GUIComponentsMap.get("currentSolution").setText("No solution found for this instance at this depth");
                          GUIComponentsMap.get("nbrVisitedNodes").setText(n5+"");

                      });
    }



    /**
     * DFS Search : Recherche aveugle en profondeur d'abord.
     * @param GUIComponentsMap
     * @param maxDepth
     */
    public static void DepthTransverseSearch(HashMap<String,Text> GUIComponentsMap,int maxDepth) throws IOException
    {
        System.out.println("Max depth is "+ maxDepth);
        System.out.println("**********Starting DepthTransverseSearch*******");
        MatriceCNF=SATEvaluator.LireFichier(inputFile);
        SATEvaluator evaluator=new SATEvaluator(SATEvaluator.nbrVariables)
        {
            @Override
            public double evaluate(Node node)
            {
                estimator.estimate(node);
                return node.getGVal()+node.getHVal();
            }
            @Override
            public int indexOfNext()
            {
                return 0;
            }
        };
        SATInstance<DepthTranversMemory> instance=new SATInstance<DepthTranversMemory>(new DepthTranversMemory(),MatriceCNF.length,SATEvaluator.nbrVariables,maxDepth);
        Estimator estimator=new Estimator() {
            @Override
            public double estimate(Node node)
            {
                return 0;
            }
        };
        evaluator.setEstimator(estimator);
        instance.setEvaluator(evaluator);
//#########################################################################################################################################################
        //CREATION OF THE FIRST NODE
        ArrayList<Integer> randomConfig=generateRandomSolution();
        SATNode root=new SATNode(null,0,0);
        root.setConfig(randomConfig);
        SATNode node=root;

        ArrayList<Node> path=null;
        instance.getOpen().addNode(node);
        boolean GOAL_REACHED=false;
        int nbrNodes=0;

        while(SATEvaluator.CURRENT_DEPTH<instance.MAX_DEPTH && !GOAL_REACHED)
        {
            if(instance.getOpen().isEmpty())
            {
               System.out.println("************STOPING THE EXECUTION*************");
               System.out.println("****No solution found for this instance****");
               Platform.runLater(() ->
               {
                    GUIComponentsMap.get("solution").setText("No solution found for this instance");
               });
               return;
            }
            nbrNodes++;
            int n1  = nbrNodes;
            Platform.runLater(() -> {
                GUIComponentsMap.get("nbrVisitedNodes").setText(n1+"");
            });

             // Défiler le noeud de la liste Open
            node=(SATNode) instance.getOpen().getNext();
            instance.getOpen().remove(node);
            SATEvaluator.CURRENT_DEPTH=node.getDepth();
            System.out.println("Current Solution :");
            for(int i=0;i<node.getConfig().size();i++) System.out.print(node.getConfig().get(i)+" ,"+(i==node.getConfig().size()-1?"\n":""));
            SATNode nd1 = node;
            Platform.runLater(() ->
            {
                  GUIComponentsMap.get("currentSolution").setText(nd1.getConfig()+"");
            });
            System.out.println("Depth :"+SATEvaluator.CURRENT_DEPTH);
            int d1  = SATEvaluator.CURRENT_DEPTH;
            Platform.runLater(() ->
            {
                GUIComponentsMap.get("depth").setText(d1+"");
            });
            // Inserer le noeud dans la liste Closed
            instance.getClosed().addNode(node);

            // Traitement des fils et chainages


            if(SATEvaluator.CURRENT_DEPTH>=instance.MAX_DEPTH)
            {
                 System.out.println("****Max depth reached");
                SATEvaluator.CURRENT_DEPTH--;
            }
            else
            {
                ArrayList<Node> children = node.getSuccessors();
                for(Node n:children)
                {
                    // Si un des noeud est un état but alors terminer et faire le chainage arrière du chemin parcoru
                    if(evaluator.estBut(n))
                    {
                        path = n.getPathToRoot();
                        System.out.println("Solution found ! \n Space Search to Solution  : ");
                        System.out.println( ((SATNode)n ).getConfig());
                        GUIComponentsMap.get("solution").setText(((SATNode)n ).getConfig()+"");
                        GUIComponentsMap.get("solutionFitness").setText((instance.getEvaluator().evaluate(node))*100/(SATPROBLEM.MatriceCNF.length)+"");
                        GUIComponentsMap.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");
                        GUIComponentsMap.get("finalDepth").setText(SATEvaluator.CURRENT_DEPTH+"");
                        GUIComponentsMap.get("nbrVisitedNodes").setText(nbrNodes+"");
                        return;
                    }
                    instance.getOpen().addNode(n);
                }
            }
        }
            if(SATEvaluator.CURRENT_DEPTH>=instance.MAX_DEPTH) System.out.println("****Max depth reached, no solution found.****");
    }



     /**
    * PROCÉDURE HEURISTQUE DE RECHERCHE AVEC GRAPHE
    * @param listeTextes
    * @param maxDepth
    * @return
    * @throws java.io.IOException
    * @throws java.lang.InstantiationException
    * @throws java.lang.IllegalAccessException*/

   /*PROCÉDURE HEURISTQUE DE RECHERCHE AVEC GRAPHE*/
   public static double Astartearch(HashMap<String,Text> listeTextes, int maxDepth) throws IOException, InstantiationException, IllegalAccessException
   {

       //IT IS USED TO ESTIMATE THE HERUSTICAL VALUE OF THE NODE
        Estimator estimator=(Node node) -> {
            int nbUnSatClause = 0;
            for(int i=0;i<MatriceCNF.length;i++)
            {
                Clause ci=new Clause("C"+(i+1), MatriceCNF[i]);
                if(!ci.estSatisfiable(((SATNode)node).getConfig()))
                {
                    nbUnSatClause++;
                }
            }
            node.setHVal(nbUnSatClause);
            int GVal = node.getDepth();
            node.setGVal(GVal);
            return (double)nbUnSatClause;
        };


       MatriceCNF=SATEvaluator.LireFichier(inputFile);
       SATEvaluator evaluator=new SATEvaluator(SATEvaluator.nbrVariables) {

           @Override
           public double evaluate(Node node) {
               estimator.estimate(node);
               return node.getGVal()+node.getHVal();
           }

           @Override
           public int indexOfNext(){
               return 0;
           }
       };

       evaluator.setEstimator(estimator);


       //for(int i=0;i<MatriceCNF.size();i++) for(int j=0;j<MatriceCNF.get(i).size();j++) System.out.println(MatriceCNF.get(i).get(j));
       SATInstance<AstartMemory> instance=new SATInstance<>(new AstartMemory(),MatriceCNF.length,SATEvaluator.nbrVariables,maxDepth);

       instance.setEvaluator(evaluator);

       //CREATION AND INSERTION OF ROOT IN OPEN MEMORY
       ArrayList<Integer> randomConfig=generateRandomSolution();
       SATNode root=new SATNode(null,0,0);
       root.setConfig(randomConfig);
       SATInstance.getOpen().addNode(root);

       //STARTING OF SEARCH
       SATNode node=root;
       ArrayList<Node> path=null;
       int nbrNodes=0;
       Boolean found = false;
       while(!found)
       {

          if(instance.getOpen().isEmpty())
              return -1;

          nbrNodes++;
          int nbrn = nbrNodes;
          Platform.runLater(() -> {
              listeTextes.get("nbrVisitedNodes").setText(nbrn+"");
          });

         //ORDER THE OPEN LIST
         instance.setOpen(instance.getEvaluator().orderList((Memory)instance.getOpen()));
         // GET FIRST NODE OF OPEN
         node=(SATNode)instance.getOpen().getNext();
         SATEvaluator.CURRENT_DEPTH=node.getDepth();
         System.out.println("Curent Solution :");
         System.out.println( ((SATNode)node ).getConfig());
         System.out.println("Depth :"+SATEvaluator.CURRENT_DEPTH);

         //GUI UPDATE
          SATNode nd =node ;
             Platform.runLater(() -> {
              listeTextes.get("currentSolution").setText(nd.getConfig()+"");
              listeTextes.get("depth").setText(SATEvaluator.CURRENT_DEPTH+"");
                  });

         if(evaluator.estBut(node))
           {
               path = node.getPathToRoot();
               System.out.println("Solution found ! \n Space Search to Solution  : ");
               System.out.println( ((SATNode)node ).getConfig());
               System.out.println("Heuristic value : " +instance.getEvaluator().evaluate(node));
               System.out.println("Depth :"+node.getDepth());

               listeTextes.get("solution").setText(node.getConfig()+"");
               listeTextes.get("solutionFitness").setText(instance.getEvaluator().evaluate(node)*100/SATPROBLEM.MatriceCNF.length+"");
               listeTextes.get("depth").setText(node.getDepth()+"");
               found = true;
               return instance.getEvaluator().evaluate(node)*100/SATPROBLEM.MatriceCNF.length;
           }

         instance.getClosed().addNode(node);
         ArrayList<Node> children = node.getSuccessorsBis(estimator);

         if(children==null || children.isEmpty())
           {
             //SATEvaluator.CURRENT_DEPTH--;
             continue;
           }

         for(Node n:children)
           {
             SATInstance.getOpen().addNode(n);
              // Si un des noeud est un état but alors terminer et faire le chainage arrière du chemin parcouru
             if(evaluator.estBut(n))
               {
                  path = n.getPathToRoot();
                  System.out.println("Solution found ! \n Space Search to Solution  : ");
                  System.out.println( ((SATNode)n ).getConfig());
                  System.out.println("Heuristic value : " +instance.getEvaluator().evaluate(n));
                  System.out.println("Depth :"+n.getDepth());
                   listeTextes.get("solution").setText(node.getConfig()+"");
                   listeTextes.get("solutionFitness").setText(instance.getEvaluator().evaluate(node)*100/SATPROBLEM.MatriceCNF.length+"");
                   listeTextes.get("depth").setText(node.getDepth()+"");
                  return instance.getEvaluator().evaluate(n)*100/SATPROBLEM.MatriceCNF.length;
               }
           }
       }
     return 0;
   }
   

    
    public static void BSO(){
        try {
            BSO.sat.SATInstance instance=BSO.sat.SATInstance.getInstanceFromFile(inputFile);
            
            SATSolution startPoint=SATSolution.generateRandomSolution(instance);
            
            HeapDanceList heapDances=new HeapDanceList(10000) {
                @Override
                public double evaluate(Object solution) {
                    SATSolution sol=(SATSolution) solution;
                    double eval = sol.getEvaluation();
                    if(eval<this.getMinEvaluation()) this.setMinEvaluation(eval);
                    
                    return this.getMinEvaluation();
                }
            };
            TabuList<SATSolution> tabuList=new TabuList<SATSolution>(10000000);
            BSOEvaluator evaluator=new BSOEvaluator(instance,tabuList,heapDances, 200000, 10000000);
            
           SATBee initialBee=new SATBee(instance);
           
           evaluator.lookForSolution(initialBee);
            
            
        } catch (IOException ex) {
            Logger.getLogger(SATPROBLEM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    public static double geneticAlgorithm(HashMap<String,Text> listeTextes , int maxItr, int initNbrIndividuals , int tC , int tM) throws IOException
    {       
        int RC, RM;
        int counter=0;
        boolean found = false;
        Random rand ;
        MatriceCNF=SATEvaluator.LireFichier(inputFile);
        GAInstance.nbIndividuals = initNbrIndividuals;
        
        System.out.println("\n \n \n **************** STARTING ALGORITHM ****************** \n \n \n ");
        SATEvaluator evaluator=new SATEvaluator(SATEvaluator.nbrVariables)
        {
            @Override
            public double evaluate(Node node) {
              return 0;
            }
            @Override
            public int indexOfNext() {  
                return 0;
            }
        };
/*##################################################################################################################################*/
     
        //CREATION AND INITIALIZATION OF THE GA INSTANCE
        GAInstance instance = new GAInstance();
        instance.initialize(initNbrIndividuals);
       
        //EVALUATION OF THE INITIAL POPULATION
        double result = 0;
        for(int i =0;i<initNbrIndividuals;i++)
        {   
           result = evaluator.evaluateIndividual(instance.getIndividualsList().get(i));
           instance.getIndividualsList().get(i).setEvaluationResult(result);
           System.out.println(instance.getIndividualsList().get(i).getGeneticData()+"\t Fitness :"+result);
        }
        
        //NO NEED TO CREATE A NEW GENERATION IF THE INITIAL ONE CONTAINS A GOAL INDIVIDUAL
        if(instance.containsSolution(evaluator))
        {
            System.out.println("Une solution au problème apparait dans la population actuelle");
            Individual indiv =  instance.getBestIndividual(evaluator);
            System.out.println("la solution la plus optimale est "+ indiv.getGeneticData() +"Fitness : "+evaluator.evaluateIndividual(indiv));
            
            Platform.runLater(() -> {
                       listeTextes.get("bestSolFound").setText(""+indiv.getGeneticData());
                       listeTextes.get("optimalSolutionEvaluation").setText(""+evaluator.evaluateIndividual(indiv));
                        
                    });
            
            //listeTextes.get("bestSolFound").setText(""+indiv.getGeneticData());
            //listeTextes.get("optimalSolutionEvaluation").setText(""+evaluator.evaluateIndividual(indiv));
            //listeTextes.put("bestSolFound",""+indiv.getGeneticData());
            //listeTextes.put("optimalSolutionEvaluation",""+evaluator.evaluateIndividual(indiv));
            found=true;
        }
/*##################################################################################################################################*/
        else
        {   
            System.out.println("le nombre max d'itérations ==> " +maxItr);
            for(int cpt =0; cpt<maxItr && !found; cpt++)
                
            {
                System.out.println("Iteration "+cpt+" : Global fitness="+instance.getPopulationFitness()/100);
                System.out.println("\t Previous Global fitness : "+GAInstance.oldPopulationFitness/100);
                GAInstance.oldPopulationFitness=instance.getPopulationFitness();
                int temp = cpt+1;
                Platform.runLater(() ->{
                    listeTextes.get("currentIteration").setText(""+temp);                   
                });
               
                
                //GENERATION OF RANDOM NUMBER RC
                rand = new Random();
                RC =ThreadLocalRandom.current().nextInt(101); 
                
                if(RC<=tC && RC!=0)
                {   
                    System.out.println(RC+" < "+tC);
                    
                    Platform.runLater(() ->{
                    listeTextes.get("currentStep").setText("Cross Over");
                    listeTextes.get("nbrOfIndividuals").setText(""+instance.getIndividualsList().size());
                     });
                    
                    System.out.println("Le nbr initial d'individus est --> " + instance.getIndividualsList().size());

                   //CROSSOVER BETWEEN INDIVIUALS TO CREATE NEW ONES WITH BETTER GENETICS
                    instance.crossOver(evaluator, RC, tC, tM);
                    
                    Platform.runLater(() ->{
                        listeTextes.get("nbrOfIndividuals").setText(""+instance.getIndividualsList().size());
                    });
                    
                }
                else
                {   System.out.println(RC+" > "+tC+" ===> RC > tC ");
                    System.out.println("Pas de croisement lors de l'itération n° "+ (cpt+1));
                    
                    Platform.runLater(() ->{
                        listeTextes.get("nbrOfIndividuals").setText(""+instance.getIndividualsList().size());
                    });
                    
                }
/*##################################################################################################################################*/
                //GENERATION OF RANDOM NUMBER RM
                RM = rand.nextInt(101);
                
                //MUTATION OF INDIVIDUALS
                if(RM<=tM && RM!=0)
                {
                  
                  instance.mutate(evaluator,RM); 
                  GAInstance.oldPopulationFitness=instance.getPopulationFitness();
                  Platform.runLater(() ->{
                        listeTextes.get("currentStep").setText("Mutation");
                    });
                    
                  //listeTextes.get("currentStep").setText("Mutation");
                  System.out.println("Mutation lors de l'itération n° "+ (cpt+1));
                }
                else
                {
                    System.out.println(RM+" > "+tM+" ===> RM > tM ");
                    System.out.println("Pas de mutation lors de l'itération n° "+ (cpt+1));
                }
/*##################################################################################################################################*/
                /*WE SUPPOSE THAT THE WEAKEST INDIVIDUALS ARE THE ONES TO BE DELETED
                SO THAT THE POPULATION GETS BACK IT'S NORMAL SIZE*/ 
                Platform.runLater(() ->{
                    listeTextes.get("currentStep").setText("Filtering");
                });
                
               int numberToDelete = instance.getIndividualsList().size()-initNbrIndividuals;
               instance.deleteWeakestIndividuals(numberToDelete);
               GAInstance.oldPopulationFitness=instance.getPopulationFitness();
               System.out.println("Le nombre d'individus aprés remplacement est "+ instance.getIndividualsList().size());
               Platform.runLater(() ->{
                   listeTextes.get("nbrOfIndividuals").setText(""+instance.getIndividualsList().size());
                });

 /*##################################################################################################################################*/
               //WE VERIFY IF THE CURRENT GENERATION CONTAINS AN OPTIMAL SOLUTION
                if(instance.containsSolution(evaluator))
                {   
                    System.out.println("La solution optimale apparait dans la population actuelle");
                    found=true;
                    Individual indiv =  instance.getBestIndividual(evaluator);
                    System.out.println("la solution la plus optimale est "+ indiv.getGeneticData() +"Fitness : "+evaluator.evaluateIndividual(indiv));
                    Platform.runLater(()->{
                        listeTextes.get("bestSolFound").setText(""+indiv.getGeneticData().toString());
                        listeTextes.get("optimalSolutionEvaluation").setText(""+evaluator.evaluateIndividual(indiv));
                    });
                    break;
                }
            }
            if(!found)
            {
               Individual indiv =  instance.getBestIndividual(evaluator);
               System.out.println("la solution la plus optimale est "+ indiv.getGeneticData() +"Fitness : "+evaluator.evaluateIndividual(indiv));
               Platform.runLater(()->{
                        listeTextes.get("bestSolFound").setText(""+indiv.getGeneticData().toString());
                        listeTextes.get("optimalSolutionEvaluation").setText(""+evaluator.evaluateIndividual(indiv));
                    });
            } 
            System.err.println("\n \n \n FIN de LALGO \n \n \n");
            //return instance.getBestIndividual(evaluator).getEvaluationResult();
        }

        Individual bestIndividual = instance.getBestIndividual(evaluator);
        //listeTextes.get("bestSolFound").setText(bestIndividual.toString());
        return evaluator.evaluateIndividual(bestIndividual);   
    }
    
    
    
    
    
    
    
    
      public static double PSOptimisation(HashMap<String,Text> listeTextes,int nbItr, int nbParticles) throws IOException
    {
        MatriceCNF=SATEvaluator.LireFichier(inputFile);

        SATEvaluator evaluator=new SATEvaluator(SATEvaluator.nbrVariables)
        {
            @Override
            public double evaluate(Node node) {
              return 0;
            }
            @Override
            public int indexOfNext() {  
                return 0;
            }
        };

      
        PSOInstance instance = new PSOInstance(); 
        
        //Initial group of particles
        instance.initialize(evaluator,nbParticles);
        ArrayList<Particle> list = instance.getParticlesList();
        for(int i =0;i <nbItr; i++)    
        {   
            
            
            final int iter=i;
            Platform.runLater(new Runnable() {
                
                @Override
                public void run() {
                    listeTextes.get("currentIteration").setText(iter+"");
                    PSO_GUIController.executionController.getGBest().setText(instance.getBestParticle(evaluator).toString());
                }
            });
              
            
            System.out.println("L'itération num "+i);
            //VERIFY IF THE SOLUTION HAS BEEN FOUND
            if(instance.containsGoalParticle(evaluator)!=null)
            {
                System.out.println("La solution au problème a été trouvé");
                System.out.println("La solution est "+ instance.getBestParticle(evaluator).getPosition());
                listeTextes.get("bestSolFound").setText(""+instance.getBestParticle(evaluator).getPosition());
                listeTextes.get("optimalSolutionEvaluation").setText(""+evaluator.evaluateParticle(instance.getBestParticle(evaluator)));
                return evaluator.evaluateParticle(instance.getBestParticle(evaluator)) ;
            }
            
          for(Particle P : list)
            {
                P.updateVelocity(instance.getGBest(),instance.w,instance.c1,instance.c2,instance.r1,instance.r2);
                P.updatePosition();
                
                //UPDATING BEST POSTION OF EACH PARTICLE
                int particleFitValue = evaluator.evaluateParticle(P);
                
                Particle D = new Particle();
                D.setPosition(P.getPBest());              
                int pBestFitValue = evaluator.evaluateParticle(D);
                
                
                if(pBestFitValue<particleFitValue)
                 {
                    P.setPBest(P.getPosition());
                    
                    D.setPosition(instance.getGBest());                
                    int GBestFitValue = evaluator.evaluateParticle(D);

                    if( GBestFitValue < particleFitValue)
                    {
                      instance.setGBest(P.getPosition());
                    }    
                 }                    
            }
        }
        Particle bestParticle = instance.getBestParticle(evaluator);
        System.out.println("La solution la plus optimale est "+bestParticle.getPosition());
        System.out.println("it's evaluation is ==> "+ evaluator.evaluateParticle(bestParticle));
        listeTextes.get("bestSolFound").setText(""+instance.getBestParticle(evaluator).getPosition());
        double fitness = evaluator.evaluateParticle(instance.getBestParticle(evaluator))*100/MatriceCNF.length;
        listeTextes.get("optimalSolutionEvaluation").setText(""+fitness);
        return fitness;
    }
        
}
