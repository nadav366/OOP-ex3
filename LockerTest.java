import oop.ex3.spaceship.*;
import org.junit .*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * Locker object test
 */
public class LockerTest {

    /** lockers of different sizes, to test different cases.    */
    Locker locker100 =  new Locker(100);
    Locker locker0 =  new Locker(0);
    Locker locker50 =  new Locker(50);
    Locker locker1000 =  new Locker(1000);
    Locker locker200 =  new Locker(200);

    /** Initialization of different objects, according to the list of valid objects.    */
    Item item2 = ItemFactory.createSingleItem("baseball bat");
    Item item3 = ItemFactory.createSingleItem("helmet, size 1");
    Item item4 = ItemFactory.createSingleItem("football");
    Item item5 = ItemFactory.createSingleItem("helmet, size 3");
    Item item10 = ItemFactory.createSingleItem("spores engine");

    /**
     * A function that initializes all lockers (including the long range)
     * to a blank position before each function.
     */
    @Before
    public void reset(){
        locker100 =  new Locker(100);
        locker0 =  new Locker(0);
        locker50 =  new Locker(50);
        locker1000 =  new Locker(1000);
        locker200 =  new Locker(200);
        locker0.getLongTerm().resetInventory();
    }

    /**
     * Test addItem function
     */
    @Test
    public void addItemTest(){
        // Attempt to insert object into cell size 0
        assertEquals(-1,locker0.addItem(item2,1));

        //Attempt to insert a negative number of objects
        assertEquals(-1,locker100.addItem(item2,-5));
        assertEquals(-1, locker200.addItem(item10, -2));

        // Experience an income of more than the size of the locker
        assertEquals(-1,locker100.addItem(item10,11));
        assertEquals(-1,locker1000.addItem(item2,501));

        // Experience an income of 0 objects
        assertEquals(0,locker50.addItem(item10,0));

        // Experience simple input of two object types to the same locker
        assertEquals(0, locker50.addItem(item5, 3));
        assertEquals(0, locker50.addItem(item10, 1));
        assertEquals(25, locker50.getAvailableCapacity());
        assertEquals(3, locker50.getItemCount("helmet, size 3"));
        assertEquals(1, locker50.getItemCount("spores engine"));

        // More experience including moving to long-term storage,
        // trying to get into longer-term storage and adding to it.
        assertEquals(1, locker1000.addItem(item10,80));
        assertEquals(800,locker1000.getAvailableCapacity());
        assertEquals(-1, locker1000.addItem(item10,100));
        assertEquals(-1, locker1000.addItem(item10,80));
        assertEquals(0, locker1000.addItem(item10,20));
        assertEquals(1, locker1000.addItem(item10,20));
        assertEquals(20,locker1000.getItemCount(item10.getType()));
        assertEquals(0, Locker.getLongTerm().getAvailableCapacity());

        // Another complex test that includes income for long-term storage and some types of objects
        Locker.getLongTerm().resetInventory();
        assertEquals(0,Locker.getLongTerm().addItem(item5,200));
        assertEquals(0,locker200.addItem(item10,5));
        assertEquals(-1,locker200.addItem(item10,10));
        assertEquals(5,locker200.getItemCount("spores engine"));
        assertEquals(150,locker200.getAvailableCapacity());
    }

    /**
     * Test for the remove function
     */
    @Test
    public void removeItemTest(){

        assertEquals(0, locker200.addItem(item2,3));
        assertEquals(-1, locker200.removeItem(item2,-2)); // Remove a negative number
        assertEquals(-1, locker200.removeItem(item2,5)); // Remove more than the amount that exists
        assertEquals(0, locker200.removeItem(item2,0)); // Remove 0 objects from an existing object

        // Remove 0 objects from an object that does not exist
        assertEquals(0, locker200.removeItem(item5,0));
        // Remove units from an object that does not exist in storage
        assertEquals(-1,locker200.removeItem(item5,2));

        assertEquals(0, locker200.removeItem(item2,2)); // Legal removal
        assertEquals(1,locker200.getItemCount(item2.getType()));
    }

    /**
     * A function that examines the getItemCount
     */
    @Test
    public void getItemCountTest(){
        // Single object test
        locker100.addItem(item3, 1);
        assertEquals(1, locker100.getItemCount("helmet, size 1"));

        // Test a single object that does not exist
        assertEquals(0, locker100.getItemCount("baseball bat"));

        // Test two objects
        locker1000.addItem(item5,5);
        locker1000.addItem(item10, 46);
        assertEquals(5, locker1000.getItemCount("helmet, size 3"));
        assertEquals(46, locker1000.getItemCount("spores engine"));

        // Test some additions of the same object
        locker200.addItem(item2,3);
        locker200.addItem(item2,2);
        locker200.addItem(item2,4);
        assertEquals(9, locker200.getItemCount("baseball bat"));
    }

    /**
     * A function that examines the getInventory function
     */
    @Test
    public void  mapTest(){
        // Testing that I do get the map I expect
        Map<String,Integer> temp1 = new HashMap();
        temp1.put("spores engine",6);
        temp1.put("football",2);
        locker1000.addItem(item10,6);
        locker1000.addItem(item4,2);
        assertEquals(temp1, locker1000.getInventory());

        Map<String,Integer> temp2 = new HashMap();
        temp2.put("baseball bat",2);
        locker200.addItem(item2,2);
        assertEquals(temp2, locker200.getInventory());

        // Empty map test
        Map<String,Integer> temp3 = new HashMap();
        assertEquals(temp3, locker100.getInventory());
    }

    /**
     *  A function that examines the getCapacity function
     */
    @Test
    public void  getCapacityTest(){
        assertEquals(100, locker100.getCapacity());
        locker100.addItem(item5,2);
        assertEquals(100, locker100.getCapacity());
    }

    /**
     * A function that examines the getAvailableCapacity function
     */
    @Test
    public void  getAvailableCapacityTest(){
        assertEquals(1000, locker1000.getCapacity()); // Empty Locker test

        assertEquals(0,locker1000.addItem(item5,20));
        assertEquals(900, locker1000.getAvailableCapacity());

        locker200.addItem(item10,2);
        assertEquals(180, locker200.getAvailableCapacity());
        locker200.addItem(item10,4);
        assertEquals(140, locker200.getAvailableCapacity());

        locker1000.addItem(item4,150);
        assertEquals(700, locker1000.getAvailableCapacity());
    }

    /**
     * A test that examines cases of collision between objects
     */
    @Test
    public void contradictingTest(){

        locker200.addItem(item2, 4);
        assertEquals(-2,locker200.addItem(item4,1));

        locker200.removeItem(item2,4);
        assertEquals(0,locker200.addItem(item4,1));

        assertEquals(-2,locker200.addItem(item2,1));
        assertEquals(-2,locker200.addItem(item2,0)); // Input of 0 objects

        // Conflict after insertion of 0
        locker100.addItem(item2,0);
        assertEquals(0,locker200.addItem(item4,1));
    }
}
