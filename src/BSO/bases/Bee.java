
package BSO.bases;

/**
 *
 * @author PC
 */
public abstract class Bee<E> {
    protected E zoneSearch;
    
    
    
    
    public abstract E initialize();
    public abstract E search(int maxIteration,int maxSize);

    
                //Getters & Setters
    public E getZoneSearch() {
        return zoneSearch;
    }

    public void setZoneSearch(E zoneSearch) {
        this.zoneSearch = zoneSearch;
    }
    
    
    
}
