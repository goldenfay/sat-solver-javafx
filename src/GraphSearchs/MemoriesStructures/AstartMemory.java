/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphSearchs.MemoriesStructures;
import GraphSearchs.Bases.Node;

/**
 *
 * @author mac
 */
public class AstartMemory extends Memory {
    @Override
    public Node getNext() {
        Node node=this.getFirst();
        this.removeFirst();
        return node;
    }   
}
