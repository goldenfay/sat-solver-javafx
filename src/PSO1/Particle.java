/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO1;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mac
 */
public class Particle
{
    
    private ArrayList<Integer> position;
    private ArrayList<Integer> PBest;
    private int velocity ;
    
    public Particle()
    {
        this.position = new ArrayList<>();
        this.PBest = new ArrayList<>();
        this.velocity=0;
    }
      
      
    public void setVelocity(int velocity)
    {
        this.velocity = velocity;
    }
    public int getVelocity()
    {
        return this.velocity;
    }
   
  
    public ArrayList<Integer> getPosition()
    {
        return position;
    }
    
    public void setPosition(ArrayList<Integer> position )
    {
        this.position=position;
    }
    
    public void setPBest(ArrayList<Integer> PBest)
    {
        this.PBest = PBest;
    }
    
    public ArrayList<Integer> getPBest()
    {
        return this.PBest;
    }
    
    private int distance(ArrayList<Integer> p1 , ArrayList<Integer> p2)
    { 
        int cpt = 0;
        for(int i=0;i<p1.size();i++)
        {
            if(!(p1.get(i).equals(p2.get(i)))) cpt++;
        }
        return cpt;
    }
    
    public void updateVelocity(ArrayList<Integer> GBest,int w, int c1, int c2,int r1,int r2)
    {
        //UPDATING VELOCITY
        this.velocity = (w*this.velocity + c1*r1*(distance(this.getPBest(),this.getPosition())) + c2*r2*(distance(GBest,this.getPosition())))%this.getPosition().size() ;
    }
    
    //UPDATING THE PARTICLE POSITION AFTER UPDATING IT'S VELOCITY
    public void updatePosition()
    {
        Random rand = new Random();
        int var = 0;
        
        for(int i=0;i<this.getVelocity();i++)
        {
            var = rand.nextInt(this.getPosition().size());
            if(this.getPosition().get(var)==0)
            {
                this.getPosition().set(var,1);
            }
            else
            {
                this.getPosition().set(var,0);
            }
        }
    }

    @Override
    public String toString() {
        return "Particle{" + "position=" + position + ", PBest=" + PBest.toString().substring(0,50) + ", velocity=" + velocity + '}';
    }
    
    
    
    
    
}
