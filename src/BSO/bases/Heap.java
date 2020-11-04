
package BSO.bases;

/**
 * Class qui implémente la structure de Tas
 * @author PC
 */
public class Heap<E extends Comparable> {
    
    private Object[] heap;
    private int size=0;
    
    
    public Heap(int size){
        this.size=0;
        heap=new Object[size];
    }

    

    
    
    
    
    
    public void add(E node)
    {
        if(size == heap.length) {
            extendHeap();
        }
        
        heap[size] = node;
        size++;
        for (int i = size-1; i != 0 && get(i).compareTo(get(parent(i)))<0; i=parent(i))
            swap(i,parent(i));

    }
    
    
    public E get(int index)
    {
        return (E) heap[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E getRoot() {
        if(size == 0)
            return null;
        if (size == 1)
            return get(--size);
        E node = get(0);
        heap[0] = heap[--size];
        entasser(0);
        return node;
    }

    public E removeLast()
    {
        if(size == 0)
            return null;
        return get(--size);
    }

    public void clear()
    {
        size = 0;
    }

    private void entasser(int i)
    {
        int l = leftChild(i),r = rightChild(i);
        int smallest = i;
        if(l < size && get(l).compareTo(get(i)) < 0) // heap[l] < heap[i]
            smallest = l;
        if(r < size && get(r).compareTo(get(i)) < 0) // heap[r] < heap[smallest]
            r = smallest;
        if(smallest != i)
        {
            swap(i,smallest);
            entasser(smallest);
        }
    }



    private int parent(int i)
    {
        return (i-1)/2;
    }

    private int leftChild(int i)
    {
        return 2*i+1;
    }

    private int rightChild(int i)
    {
        return 2*i+2;
    }

    private void swap(int i,int j)
    {
        E node = get(i);
        heap[i] = heap[j];
        heap[j] = node;
    }


    private void extendHeap()
    {
        Object[] newHeap = new Object[heap.length*2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    public Object[] getHeap() {
        return heap;
    }

    public int getSize() {
        return size;
    }
    
    
}
