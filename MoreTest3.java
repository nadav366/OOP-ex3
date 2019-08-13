
import static org.junit.Assert.*;

import oop.ex3.spaceship.*;
import org.junit.Before;
import org.junit.Test;

public class MoreTest3 {

    private Locker locker1;
    private Locker locker2;
    private Locker locker3;
    private Item item0;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item invalidItem;


    /**
     * creates Locker with 20,1,40 units, creates invalid locker, creates arry of legal items.
     */
    @Before
    public void createTestObjects(){
        this.locker1 = new Locker(20);
        this.locker2 = new Locker(1);
        this.locker3 = new Locker(40);
        this.item0 = ItemFactory.createSingleItem("baseball bat");
        this.item1 = ItemFactory.createSingleItem("helmet, size 1");
        this.item2 = ItemFactory.createSingleItem("helmet, size 3");
        this.item3 = ItemFactory.createSingleItem("spores engine");
        this.item4 = ItemFactory.createSingleItem("football");
        this.invalidItem = new Item("baseball", 2);
    }


    /**
     * Tests the creation of a new Locker object.
     */
    @Test
    public void createLockerTest(){
        assertNotNull(this.locker1);
        assertNotNull(this.locker1.getInventory());
        assertTrue(this.locker1.getInventory().isEmpty());
    }

    @Test
    /**
     * Basic tests for the All the methods after add the Locker an item.
     */
    public void basicTests(){

        // 1st check: initialize Locker

        assertEquals(20, this.locker1.getCapacity());
        assertEquals(20, this.locker1.getAvailableCapacity());

        // 2st check: add the Locker item - "baseball bat", 2 units, then remove it

        this.locker1.addItem(item0, 1);
        assertEquals(20, this.locker1.getCapacity());
        assertEquals(18, this.locker1.getAvailableCapacity());
        assertEquals(1, this.locker1.getItemCount(item0.getType()));

        // 3st check: remove from the Locker item - "baseball bat", 2 units

        this.locker1.removeItem(item0, 1);
        assertEquals(20, this.locker1.getCapacity());
        assertEquals(20, this.locker1.getAvailableCapacity());
        assertEquals(0, this.locker1.getItemCount(item0.getType()));
    }

    @Test
    // check the addItem method

    public void addItemTests(){

        // 0st check: edge cases

        assertEquals(-1, this.locker1.addItem(item0, -5)); // invalid n
        assertEquals(-1, this.locker1.addItem(item0, 30)); //  n bigger then capacity
        assertEquals(-1, this.locker2.addItem(item0, 1)); //  only full item can enter

        // 1st check: check all legal items

        assertEquals(0, locker1.addItem(item0, 1));
        this.locker1.removeItem(item0, 1);
        assertEquals(0, locker1.addItem(item1, 1));
        this.locker1.removeItem(item1, 1);
        assertEquals(0, locker1.addItem(item2, 1));
        this.locker1.removeItem(item2, 1);
        assertEquals(0, locker1.addItem(item3, 1));
        this.locker1.removeItem(item3, 1);
        assertEquals(0, locker1.addItem(item4, 1));
        this.locker1.removeItem(item4, 1);

        //2st check : add items of "helmet, size 1", check other functions

        this.locker1.addItem(item1, 2);
        assertEquals(14, this.locker1.getAvailableCapacity());
        assertEquals(2, this.locker1.getItemCount(item1.getType()));
        //storage is 1000


        //3st check: add items of "helmet, size 1" - 3 should move to storage, 1 stay

        this.locker1.addItem(item1, 2);
        assertEquals(17, this.locker1.getAvailableCapacity());
        assertEquals(1, this.locker1.getItemCount(item1.getType()));
        //storage is 991 - 3 "helmet, size 1"


        //4st check: add items of "helmet, size 3" - both of them should insert the Locker

        assertEquals(0, this.locker1.addItem(item2, 2));
        assertEquals(7, this.locker1.getAvailableCapacity());
        assertEquals(1, this.locker1.getItemCount(item1.getType()));
        assertEquals(2, this.locker1.getItemCount(item2.getType()));
        //storage is 991 - 3 "helmet, size 1"


        //5st check: add more items of "helmet, size 3" - no enough capacity in the Locker to add them

        assertEquals(-1, this.locker1.addItem(item2, 2));
        assertEquals(7, this.locker1.getAvailableCapacity());
        assertEquals(1, this.locker1.getItemCount(item1.getType()));
        assertEquals(2, this.locker1.getItemCount(item2.getType()));
        //storage is 991 - 3 "helmet, size 1"


        //6st check: remove helmet size 1, add 2 more helmets size 3 - all helmets 'size 3
        // will move to storage

        assertEquals(0, this.locker1.removeItem(item1, 1));
        assertEquals(1, this.locker1.addItem(item2, 2));
        assertEquals(20, this.locker1.getAvailableCapacity());
        assertEquals(0, this.locker1.getItemCount(item2.getType()));
        //storage is 971 - 3 "helmet, size 1", 2 "helmet, size 3"

        // 7st check - add 0 Items - item should insert the map with value 0

        assertEquals(0, this.locker1.addItem(item3, 0));
        //assertFalse(this.locker1.getInventory().containsKey(item3.getType()));
        assertEquals(0, this.locker1.removeItem(item3, 0));

    }


    /**
     * Test removeItem Method - add 2 items and try to remove them.
     */
    @Test
    public void removeItemTests(){
        this.locker3.addItem(item0, 2);
        this.locker3.addItem(item1, 4);
        assertEquals(0, this.locker3.removeItem(item0, 2));
        assertEquals(0, this.locker3.getItemCount(item0.getType()));
        assertEquals(0, this.locker3.removeItem(item0, 0));
        assertEquals(0, this.locker3.removeItem(item1, 4));
        assertEquals(0, this.locker3.removeItem(item1, 0));
    }

    /**
     * test the section C method
     */
    @Test
    public void sectioncTests(){

        assertEquals(0, this.locker3.addItem(item0, 2));
        assertEquals(-2, this.locker3.addItem(item4, 1));
        assertEquals(2, this.locker3.getItemCount(item0.getType()));
        assertEquals(0, this.locker3.getItemCount(item4.getType()));
        assertEquals(0, this.locker3.removeItem(item0, 2));
    }

    /**
     * test that max capacity no change when add new item
     */
    @Test
    public void lockerCapacityTest() {
        this.locker3.addItem(item0, 3);
        assertEquals(40, locker3.getCapacity());
        this.locker3.removeItem(item0, 3);
    }

    @Test
    public void availableCapacityTest(){
        this.locker3.addItem(item3, 1);
        assertEquals(30, this.locker3.getAvailableCapacity());

    }
}

