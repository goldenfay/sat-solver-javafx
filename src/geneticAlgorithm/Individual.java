
package geneticAlgorithm;

import java.util.ArrayList;

/**
 *
 * @author mac
 */
public class Individual {
    
        private ArrayList<Integer> geneticData;
        private double evaluationResult ;
        
          public Individual()
        {
            this.geneticData = new ArrayList<Integer>();
        }
        public Individual(ArrayList<Integer> geneticData)
        {
            this.geneticData = geneticData;
        }
        
        
        public void setEvaluationResult(double evaluationResult)
        {
            this.evaluationResult = evaluationResult;
        }
         public double getEvaluationResult()
        {
            return this.evaluationResult;
        }
     
        public ArrayList<Integer> getGeneticData()
        {
            return this.geneticData;
        }
        public void  setGeneticData(ArrayList<Integer> geneticData)
        {
            this.geneticData=geneticData;
        }
        
        @Override
        public boolean equals(Object o)
        {
            //if(this==o) return true;
            if(!(o instanceof Individual))
            {
                return false;
            }
            Individual indi = (Individual)o;
             for(int i=0;i<this.geneticData.size();i++)
            {
                if(!(this.geneticData.get(i).equals(indi.geneticData.get(i))))
                {
                    return false;
                }
            }
           return true;         
        }
                
        
}
