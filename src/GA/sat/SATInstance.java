
package GA.sat;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class SATInstance {
    
    private ArrayList<Integer>[][] literalSatisfictionTable;
    private static int[][] MATRICE_CNF;
    private int nbrVariables;
    private int nbrClauses;

    
    
    
    public SATInstance(int nbrVariables, int nbrClauses) {
        
        this.nbrVariables = nbrVariables;
        this.nbrClauses = nbrClauses;
        this.literalSatisfictionTable=new ArrayList[nbrVariables][2];
          //initialiser la table des satisfections des litéreaux
          for(int i=0;i<this.literalSatisfictionTable.length;i++){
              literalSatisfictionTable[i][0]=new ArrayList<Integer>();
              literalSatisfictionTable[i][0]=new ArrayList<Integer>();
          }
    }

    
    
    
    /**
     * Méthode statique (static) qui charge les clauses depuis un fichier CNF donnée par son chemin en paramètres.
     * @param fileName Chemin vers le fichier CNF à charger.
     * @return une instance de SAT configurée depuis les données chargés depuis le fichier.
     * @throws IOException 
     */
    public static SATInstance getInstanceFromFile(String fileName) throws IOException {
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
          // Sauter la lecture jusqu'a atteindre la ligne où il ya le p , pour déterminer le nombre de variables et clauses
        while (line.charAt(0) != 'p') {
            line = reader.readLine();
        }

         // Extraire le nombre de clauses et variables
        String first[] = line.split("\\s+");
        SATInstance satInstance = new SATInstance(Integer.parseInt(first[3]),Integer.parseInt(first[2]));
         // Instancier la matrice à partir des informations extraites
        MATRICE_CNF=new int[Integer.parseInt(first[3])][Integer.parseInt(first[2])];
        
         // Remplissage de la matrice par lecture du reste de fichier
        int i = 0;
        while ((line = reader.readLine()) != null && i < satInstance.getNbrClauses()) {
            if(line.length()>0 && line.charAt(0) == ' ')
                line = line.substring(1);

            String[] lineString = line.split("\\s+");
            for (int j = 0; j < lineString.length - 1; j++) {
                int lit = Integer.parseInt(lineString[j]);
                MATRICE_CNF[i][j]=lit;
                
            }
            i++;
        }
        reader.close();
        return satInstance;
    }
    
    
    
    
    
    
    
    
    
    
    
                //Getters & Setters

    public ArrayList<Integer>[][] getLiteralSatisfictionTable() {
        return literalSatisfictionTable;
    }

    public void setLiteralSatisfictionTable(ArrayList<Integer>[][] literalSatisfictionTable) {
        this.literalSatisfictionTable = literalSatisfictionTable;
    }

    public static int[][] getMATRICE_CNF() {
        return MATRICE_CNF;
    }

    public static void setMATRICE_CNF(int[][] MATRICE_CNF) {
        SATInstance.MATRICE_CNF = MATRICE_CNF;
    }

   

    public int getNbrVariables() {
        return nbrVariables;
    }

    public void setNbrVariables(int nbrVariables) {
        this.nbrVariables = nbrVariables;
    }

    public int getNbrClauses() {
        return nbrClauses;
    }

    public void setNbrClauses(int nbrClauses) {
        this.nbrClauses = nbrClauses;
    }
    
    
    
    
    
    
    
    
    
}
