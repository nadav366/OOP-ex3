import oop.ex3.spaceship.Item;

/**
 * A class that is Locker, which is a kind of storage.
 */
public class Locker extends Storage{

    /** One large storage unit for all lockers.    */
    private static LongTermStorage longLocker = new LongTermStorage();

    /**
     * constructor.
     * Uses the father's constructor, storage.
     * @param capacity The size of the locker
     */
    public Locker(int capacity){
        super(capacity);
    }

    /**
     * A function that adds a certain amount of object to a Locker unit.
     * Uses the father's addItem, but checks for additional parameters.
     * @param item Object for income
     * @param n The number of units from the object
     * @return 0, if successfully to Locker. 1, If some has been transferred to long-term storage
     * -2, If there was a collision between objects. -1, There was no other reason.
     */
    @Override
    public int addItem(Item item, int n){
        // Test for collision between objects
        if (contradicting(item)){
            System.out.println(ERROR+ERROR_CON1+item.getType()+ERROR_CON2);
            return -2;
        }
        // Insert to storage by function of the father.
        if (super.addItem(item,n) == -1)
            return -1;

        // Check whether to transfer to long-term storage
        if ((contents.get(item.getType())*item.getVolume())/(double)maxCapacity <= 0.5)
            return 0;

        // Transfer to long-term storage
        int transferAmount =contents.get(item.getType())-
                (int)(0.2*maxCapacity/item.getVolume());
        if (longLocker.addItem(item, transferAmount) == 0) {
            System.out.println(WARNING);
            removeItem(item, transferAmount);
            return 1;
        }

        // Could not insert into long-term storage, can not add the object
        System.out.println(ERROR+ERROR_NO_ROOM1+transferAmount+ERROR_ITEMS+item.getType()+ERROR_NO_ROOM2);
        removeItem(item, n);
        return -1;
    }

    /**
     * A function that checks whether there is a conflict between objects that can not be together.
     * @param item Object for income
     * @return true if there is a false collision if not.
     */
    private boolean contradicting(Item item) {
        for (String key: contents.keySet()){
            if (contents.get(key) > 0 && key.equals("football") && item.getType().equals("baseball bat"))
                return true;
            if (contents.get(key) > 0 && key.equals("baseball bat") && item.getType().equals("football"))
                return true;
        }
        return false;
    }

    /**
     * This method removes n Items of object from the locker.
     * @param item Object for Remove
     * @param n The number of units from the object
     * @return 0, if successfully Remove. -1, if not.
     */
    public int removeItem(Item item, int n){
        // Check for removal of negative value
        if (n<0){
            System.out.println(ERROR+ERROR_NEG + item.getType());
            return -1;
        }

        if (n==0) return 0; // Remove 0 units, is legally

        // Check that there are enough units of the item
        if ( !contents.containsKey(item.getType()) || contents.get(item.getType())<n){
            System.out.println(ERROR+ERROR_CON + item.getType() + ERROR_ITEMS + item.getType());
            return -1;
        }

        contents.put(item.getType(),contents.get(item.getType())-n);
        currentCapacity += n*item.getVolume();
        if (contents.get(item.getType())==0) contents.remove(item.getType());
        return 0;
    }

    /**
     * A function that returns the class's long-term storage object.
     * @return LongTermStorage of the class
     */
    public static LongTermStorage getLongTerm(){ return longLocker; }
}