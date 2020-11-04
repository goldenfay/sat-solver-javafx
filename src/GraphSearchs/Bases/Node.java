
package GraphSearchs.Bases;

import java.util.ArrayList;

/**
 *
 * @author PC
 */
public abstract class Node {
    
    protected int depth =0;
    protected int index;
    protected Node parent;
    protected int GVal=0,HVal=0; // Valeurs de cout,heuristique

    public Node(Node parent,int depth,int index) {
        this.parent = parent;
        this.depth=depth;
        this.index=index;
    }
    
    public abstract ArrayList<Node> getSuccessors();
    
    
    /**
     * Méthode qui retourne le chemein du noeud courant jusqu'à la racine du graphe.
     * @return Le chemin depuis la racine jusqu'au noeud courant .
     */
    public ArrayList<Node> getPathToRoot(){
        ArrayList<Node> nodes = new ArrayList<>();
        Node node = this;
        for (;node != null;node = node.getParent())
            nodes.add(0,node);
        return nodes;
    }
    
    
    
    
            //Getters & Setters

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
    
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.depth = index;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getGVal() {
        return GVal;
    }

    public void setGVal(int GVal) {
        this.GVal = GVal;
    }

    public int getHVal() {
        return HVal;
    }

    public void setHVal(int HVal) {
        this.HVal = HVal;
    }
    
    
    
    
    
    
    
}
