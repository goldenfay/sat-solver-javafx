
package ACO.Bases;

/**
 *
 * @author PC
 */
public class Pheromon {
    
    private double initValue;
    private double[][] pheromonTable;
    
      // Paramètres :
    private double alpha;
    private double beta;
    private double rho;
    private double to;

    public Pheromon(double initValue, double[][] pheromonTable, double alpha, double beta, double rho, double to) {
        this.initValue = initValue;
        this.pheromonTable = pheromonTable;
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.to = to;
    }
    
    
    
    /**
     * Méthode qui nitialise la table de pheromones à une valeur initiale donnée.
     * @param initValue 
     */
    
    public void initializePheromonTable(double initValue){
        
        
        for(int i=0;i<pheromonTable.length;i++)
            for(int j=0;j<pheromonTable[i].length;j++)
                pheromonTable[i][j]=initValue;
    }
    
    
    
    
    
    
                    //Getter & Setters

    public double getInitValue() {
        return initValue;
    }

    public void setInitValue(double initValue) {
        this.initValue = initValue;
    }

    public double[][] getPheromonTable() {
        return pheromonTable;
    }

    public void setPheromonTable(double[][] pheromonTable) {
        this.pheromonTable = pheromonTable;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getRho() {
        return rho;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }
    
    
    
}
