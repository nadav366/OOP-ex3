import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that is a simple storage object. Contains the features common to Locker and long-term storage.
 */
public class Storage {

    // Objects that center all prints to the user.
    // Designed to make it easy to modify and edit messages.

    protected static String WARNING = "Warning: Action successful, but has caused items to be moved to storage";

    protected static String ERROR_ITEMS =  " Items of type ";

    protected static String ERROR = "Error: Your request cannot be completed at this time. ";

    protected static String ERROR_NO_ROOM1 = "Problem: no room for ";
    protected static String ERROR_NO_ROOM2 = " in the long-term storage";

    protected static String ERROR_NEG =  "Problem: cannot remove a negative number of items of type ";
    protected static String ERROR_CON =  "Problem: the locker does not contain ";

    protected static String  ERROR_CON1 = "Problem: the locker cannot contain items of type ";
    protected static String  ERROR_CON2 = " as it contains a contradicting item";


    /** Maximum storage on the storage unit   */
    protected int maxCapacity;

    /** The amount of space available in the storage unit  */
    protected int currentCapacity;

    /** HashMap contains the storage object data    */
    protected Map<String,Integer> contents = new HashMap();

    /**
     * constructor.
     * Initializes storage unit size when empty.
     * @param capacity Storage unit size
     */
    public Storage(int capacity){
        this.maxCapacity = capacity;
        this.currentCapacity = capacity;
    }

    /**
     * A function that adds a certain amount of object to a storage unit
     * @param item Object for income
     * @param n The number of units from the object
     * @return 0, if successfully inserted. -1, if not.
     */
    protected int addItem(Item item, int n){
        if (!inputCheck(item, n)) return -1;

        // Check that there is enough space to store the required.
        if (item.getVolume()*n > this.currentCapacity){
            System.out.println(ERROR+ERROR_NO_ROOM1+n+ERROR_ITEMS+item.getType());
            return -1;
        }


        int currentUnits = 0;
        if (contents.containsKey(item.getType()))
            currentUnits = contents.get(item.getType());
        contents.put(item.getType(),currentUnits+n);
        currentCapacity -= n*item.getVolume();
        return 0;
    }

    /**
     * Check the integrity of the input received
     * @param item Object for income
     * @param n The number of units from the object
     * @return true If the input is correct, otherwise false.
     */
    private boolean inputCheck(Item item, int n){
        if (n<0 || item==null) return false;

        // Check that an object is in the list of valid objects.
        Item[] legal = ItemFactory.createAllLegalItems();
        for (int i=0; i<legal.length; i++) {
            if (legal[i].toString().equals(item.toString())){
                return true;
            }
        }
        return false;
    }

    /**
     * method returns the number of Items of certain type the storage contains.
     * @param type Name of the object to be checked
     * @return number of Items of type type the storage contains.
     */
    public int getItemCount(String type){
        if (contents.containsKey(type))
            return contents.get(type);
        return 0;
    }

    /**
     * method returns a map of all the item types
     * contained in the storage, and their respective quantities
     * @return map of all the item types and their respective quantities
     */
    public Map<String, Integer> getInventory(){
        // Copies the existing map object so that it can not be changed outside the class.
        Map<String,Integer> newMap = new HashMap();
        for (String key: contents.keySet())
            if (contents.get(key) != 0 )
                newMap.put(key, contents.get(key));
        return newMap;
    }

    /**
     * This method returns the storage’s total capacity.
     * @return the storage’s total capacity.
     */
    public int getCapacity(){
        return maxCapacity;
    }

    /**
     * This method returns the storage’s available capacity
     * @return storage’s available capacity
     */
    public int getAvailableCapacity(){
        return currentCapacity;
    }
}
