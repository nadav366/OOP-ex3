import static org.junit.Assert.*;
import oop.ex3.spaceship.*;
import org.junit.Before;
import org.junit.Test;


/**
 * This class represents a test for the LongTermStorage class.
 */
public class MoreTest2 {

    private LongTermStorage longTerm1;
    private LongTermStorage longTerm2;
    private LongTermStorage longTerm3;
    private Item item0;
    private Item item1;
    private Item invalidItem;

    /**
     * Creates a new test Storage objects and invalid item.
     */
    @Before
    public void createTestObjects() {
        this.longTerm1 = new LongTermStorage();
        this.longTerm2 = new LongTermStorage();
        this.longTerm3 = new LongTermStorage();
        this.item0 = ItemFactory.createSingleItem("baseball bat");
        this.item1 = ItemFactory.createSingleItem("helmet, size 1");
        this.invalidItem = new Item("baseball", 2);
    }

    @Test
    public void createStorageTest() {
        assertNotNull(this.longTerm1);
        assertNotNull(this.longTerm2);
        assertNotNull(this.longTerm1.getInventory());
        assertNotNull(this.longTerm2.getInventory());
        assertTrue(this.longTerm1.getInventory().isEmpty());
        assertTrue(this.longTerm2.getInventory().isEmpty());
    }

    /**
     * Basics test for checking the methods
     */
    @Test
    public void basicTests() {
        // 1st check - initialize Storage and check basic functions

        assertEquals(1000, this.longTerm1.getCapacity());
        assertEquals(1000, this.longTerm1.getAvailableCapacity());

        // 2st check - add item to Storage, then remove

        assertEquals(0, this.longTerm1.addItem(item0, 3));
        assertEquals(1000, this.longTerm1.getCapacity());
        assertEquals(994, this.longTerm1.getAvailableCapacity());
        assertEquals(3, this.longTerm1.getItemCount(item0.getType()));
        this.longTerm1.resetInventory(); // clear the Map
        assertTrue(this.longTerm1.getInventory().isEmpty());
    }


    /**
     * check the addItem method + other methods in the class
     */
    @Test
    public void addItemTests() {
        // 1st check - edge cases

        assertEquals(-1, this.longTerm2.addItem(item0, -5)); // invalid n
        assertEquals(-1, this.longTerm2.addItem(item0, 501)); //  n bigger then capacity

        // 2st check - add many items
        this.longTerm2.addItem(item0, 3);
        this.longTerm2.addItem(item1, 3);
        assertEquals(1000, this.longTerm2.getCapacity()); // check total Capacity
        assertEquals(985, this.longTerm2.getAvailableCapacity()); // check current Capacity
        assertEquals(3, this.longTerm2.getItemCount(item0.getType())); // check map
        assertEquals(3, this.longTerm2.getItemCount(item1.getType())); // check map
        this.longTerm2.resetInventory(); // remove all the Items
        assertTrue(this.longTerm2.getInventory().isEmpty());
        assertFalse(this.longTerm2.getInventory().containsKey(item0.getType())); // check item removed
        assertFalse(this.longTerm2.getInventory().containsKey(item1.getType())); // check item removed
    }

    /**
     * Tests the resetInventory method.
     */
    @Test
    public void resetInventoryTest(){
        this.longTerm3.addItem(item0, 10);
        assertFalse(this.longTerm3.getInventory().isEmpty());
        assertEquals(980, this.longTerm3.getAvailableCapacity());
        this.longTerm3.resetInventory();
        assertNotNull(this.longTerm3.getInventory());
        assertEquals( 0, this.longTerm3.getItemCount(item0.getType()));

    }

    ///////////////////////////////////

    Item legalItem1 = ItemFactory.createSingleItem("helmet, size 1");
    Item legalItem2 = ItemFactory.createSingleItem("spores engine");
    Item illegalItem1 = new Item("helmet, size 3", 4);
    LongTermStorage longStorage;

    @Before
    public void beforeTest() {
        this.longStorage = new LongTermStorage();
    }

    @Test
    public void testStorage(){
        assertTrue(this.longStorage.getInventory().isEmpty());
        assertNotNull(this.longStorage.getInventory());
        assertEquals(1000, this.longStorage.getCapacity());
    }

    @Test
    public void testAddItem(){
        this.longStorage.addItem(legalItem1, 5);
        this.longStorage.addItem(legalItem1, 5);
        assertEquals(10, this.longStorage.getItemCount(legalItem1.getType()));
    }

    @Test
    public void testAddIllegalItem(){
        this.longStorage.addItem(illegalItem1, 5);
        assertEquals(0, this.longStorage.getItemCount(legalItem1.getType()));
        assertEquals(0, this.longStorage.getItemCount(illegalItem1.getType()));
        assertTrue(this.longStorage.getInventory().isEmpty());
    }

    @Test
    public void testFullStorage(){
        this.longStorage.addItem(legalItem2, 100);
        assertEquals(0, this.longStorage.getAvailableCapacity());
        int result = this.longStorage.addItem(legalItem1,2);
        assertEquals(-1, result);
        assertEquals(0,this.longStorage.getItemCount(legalItem1.getType()));
        assertEquals(100,this.longStorage.getItemCount(legalItem2.getType()));
    }

    @Test
    public void testResetStorage(){
        this.longStorage.addItem(legalItem1, 50);
        this.longStorage.addItem(legalItem2, 50);
        assertEquals(350, this.longStorage.getAvailableCapacity());
        this.longStorage.resetInventory();
        assertEquals(0,this.longStorage.getItemCount(legalItem1.getType()));
        assertEquals(0,this.longStorage.getItemCount(legalItem2.getType()));
    }
}



