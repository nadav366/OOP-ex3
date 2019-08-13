/**
 * A class that is Long Term Storage, which is a kind of storage.
 */
public class LongTermStorage extends Storage{

    /**
     * constructor.
     * Uses the father's constructor, with the value 1000 being the size of the long-term storage unit.
     */
    public LongTermStorage(){
        super(1000);
    }

    /**
     * A function that initializes the storage to a blank state.
     */
    public void resetInventory(){
        currentCapacity = maxCapacity;
        contents.clear();
    }
}
