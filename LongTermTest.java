import oop.ex3.spaceship.*;
import org.junit .*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * LongTermTest object test
 */
public class LongTermTest {
    /** Boot up some long-term storage units    */
    LongTermStorage bigLoker1 = new LongTermStorage();
    LongTermStorage bigLoker2 = new LongTermStorage();
    LongTermStorage bigLoker3 = new LongTermStorage();

    /** Initialization of different objects, according to the list of valid objects.    */
    Item item2 = ItemFactory.createSingleItem("baseball bat");
    Item item3 = ItemFactory.createSingleItem("helmet, size 1");
    Item item4 = ItemFactory.createSingleItem("football");
    Item item5 = ItemFactory.createSingleItem("helmet, size 3");
    Item item10 = ItemFactory.createSingleItem("spores engine");

    /**
     * A function that initializes all lockers to a blank position before each function.
     */
    @Before
    public void reset(){
        bigLoker1.resetInventory();
        bigLoker2.resetInventory();
        bigLoker3.resetInventory();
    }

    /**
     * A function that examines the method addItem
     */
    @Test
    public void addItemTest(){
        // Experience an income of more than the size of the locker
        assertEquals(-1, bigLoker1.addItem(item10, 130));
        assertEquals(-1, bigLoker2.addItem(item5, 210));

        // Experience simple input of two object types to the same LTS
        assertEquals(0, bigLoker1.addItem(item5, 100));
        assertEquals(0, bigLoker1.addItem(item10, 50));

        // Experience an income of 0 objects
        assertEquals(0, bigLoker3.addItem(item10, 0));

        //A test that did not leave room for storage
        assertEquals(-1, bigLoker1.addItem(item10, 1));

        //Attempt to insert a negative number of objects
        assertEquals(-1, bigLoker3.addItem(item10, -2));
    }

    /**
     * Storage boot test
     */
    @Test
    public void resetInventoryTest(){
        bigLoker1.addItem(item5, 200);
        bigLoker1.resetInventory();
        assertEquals(0,bigLoker1.getInventory().size());
        assertEquals(1000, bigLoker1.getAvailableCapacity());
        assertEquals(0, bigLoker1.addItem(item5, 200));
    }

    /**
     * A function that examines the method getItemCount
     */
    @Test
    public void getItemCountTest(){
        // Testing a single object
        assertEquals(0,bigLoker1.addItem(item3, 1));
        assertEquals(1, bigLoker1.getItemCount("helmet, size 1"));
        // Checking a product that does not exist
        assertEquals(0, bigLoker1.getItemCount("baseball bat"));

        // Test two objects
        bigLoker2.addItem(item5,5);
        bigLoker2.addItem(item10, 46);
        assertEquals(5, bigLoker2.getItemCount("helmet, size 3"));
        assertEquals(46, bigLoker2.getItemCount("spores engine"));

        // Test some additions of the same object
        bigLoker3.addItem(item10,32);
        bigLoker3.addItem(item10,2);
        bigLoker3.addItem(item10,25);
        assertEquals("Checking Add the same item",59, bigLoker3.getItemCount("spores engine"));
    }

    /**
     * A function that examines the getInventory function
     */
    @Test
    public void  mapTest(){
        // Testing that I do get the map I expect
        Map<String,Integer> temp1 = new HashMap();
        temp1.put("spores engine",67);
        temp1.put("football",2);
        bigLoker1.addItem(item10,67);
        bigLoker1.addItem(item4,2);
        assertEquals(temp1, bigLoker1.getInventory());

        Map<String,Integer> temp2 = new HashMap();
        temp2.put("baseball bat",2);
        bigLoker2.addItem(item2,2);
        assertEquals(temp2, bigLoker2.getInventory());

        // Empty map test
        Map<String,Integer> temp3 = new HashMap();
        assertEquals( temp3, bigLoker3.getInventory());
    }

    /**
     *  A function that examines the getCapacity function
     */
    @Test
    public void  getCapacityTest(){
        assertEquals("empty check",1000, bigLoker1.getCapacity());

        bigLoker1.addItem(item5,20);
        assertEquals("full check",1000, bigLoker1.getCapacity());
    }

    /**
     * A function that examines the getAvailableCapacity function
     */
    @Test
    public void  getAvailableCapacityTest(){
        assertEquals(1000, bigLoker1.getCapacity()); // Empty Locker test

        assertEquals(0,bigLoker1.addItem(item5,20));
        assertEquals(900, bigLoker1.getAvailableCapacity());

        bigLoker2.addItem(item10,20);
        assertEquals(800, bigLoker2.getAvailableCapacity());
        bigLoker2.addItem(item10,5);
        assertEquals(750, bigLoker2.getAvailableCapacity());

        bigLoker3.addItem(item4,150);
        assertEquals(400, bigLoker3.getAvailableCapacity());
    }
}
