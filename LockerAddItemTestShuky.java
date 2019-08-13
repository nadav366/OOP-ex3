import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;
import static org.junit.Assert.*;

public class LockerAddItemTestShuky {

    private Locker testLocker;
    private Locker testLocker2;
    //items0 - type "baseball bat" volume 2.
    //items1 - type "helmet, size 1" volume 3.
    //items2 - type "helmet, size 3" volume 5.
    //items3 - type "spores engine" volume 10.
    //items4 - type "football" volume 4.
    private Item item0 = ItemFactory.createSingleItem("baseball bat");
    private Item item1 = ItemFactory.createSingleItem("helmet, size 1");
    private Item item2 = ItemFactory.createSingleItem("helmet, size 3");
    private Item item3 = ItemFactory.createSingleItem("spores engine");
    private Item item4 = ItemFactory.createSingleItem("football");

    private Item item = new Item("type_illegal",1);

    @Before
    public void createTestObject() {

        testLocker = new Locker(20);
        testLocker.getLongTerm().resetInventory();
    }
    @Test
    public void testCreateInstance(){
        //checks the constructor and the getters
        assertNotNull(testLocker);
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 20, testLocker.getAvailableCapacity());
        assertNotNull(testLocker.getInventory());
    }
    @Test
    public void testAddItem1() {
        //adding 1 item (simple and legal adding)
        assertEquals(0, testLocker.addItem(item0, 1));
        assertEquals(20, testLocker.getCapacity());
        assertEquals(18, testLocker.getAvailableCapacity());
        assertEquals(1, testLocker.getItemCount("baseball bat"));
    }
    @Test
     public void testAddItem2(){
        //adding 1 baseball bat and then again 1 baseball bat
        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 18, testLocker.getAvailableCapacity());
        assertEquals(1,testLocker.getItemCount("baseball bat"));

        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 16, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem3() {
        //trying to add 3 items of spores engine (30 units total) to locker with 20 capacity units.
        assertEquals(-1,testLocker.addItem(item3,3));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 20, testLocker.getAvailableCapacity());
        assertTrue(testLocker.getInventory().isEmpty());
    }
    @Test
    public void testAddItem4(){
        //adding 2 items of baseball bat (legal adding)
        assertEquals(0,testLocker.addItem(item0,2));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 16, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem5() {
        //trying to add 4 items of helmet size 1(3 volume each -12 total) to locker with 20 capacity units
        // should add 1 to the locker and 3 to the long-term storage (1 is less then 20% but 2 is already 30%)
        assertEquals(1,testLocker.addItem(item1,4));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 17, testLocker.getAvailableCapacity());
        assertEquals(1,testLocker.getItemCount("helmet, size 1"));
    }
    @Test
    public void testAddItem6() {
        //lets try to add 4 different types to the testLocker in legal way.
        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(0,testLocker.addItem(item1,1));
        assertEquals(0,testLocker.addItem(item2,1));
        assertEquals(0,testLocker.addItem(item3,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 0, testLocker.getAvailableCapacity());
        assertEquals(1,testLocker.getItemCount("baseball bat"));
        assertEquals(1,testLocker.getItemCount("helmet, size 1"));
        assertEquals(1,testLocker.getItemCount("helmet, size 3"));
        assertEquals(1,testLocker.getItemCount("spores engine"));
    }
    @Test
    public void testAddItem7() {
        //lets try do add 3 different types to the lestLocker (multiple times for one of the items)
        assertEquals(0,testLocker.addItem(item0,2));
        assertEquals(0,testLocker.addItem(item1,1));
        assertEquals(0,testLocker.addItem(item2,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 8, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
        assertEquals(1,testLocker.getItemCount("helmet, size 1"));
        assertEquals(1,testLocker.getItemCount("helmet, size 3"));
    }
    @Test
    public void testAddItem8() {
        //now we will add 5 baseball bats, then 1 baseball bat - its 60% of the capacity - 2 need to say and the
        //other 4 need to move to the long-term storage
        assertEquals(0,testLocker.addItem(item0,5));
        assertEquals(1,testLocker.addItem(item0,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 16, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem9() {
        //now we will add 1 baseball bat, then 5 baseball bat - its 60% of the capacity - 2 need to say and the
        //other 4 need to move to the long-term storage
        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(1,testLocker.addItem(item0,5));
        assertEquals(20,testLocker.getCapacity());
        assertEquals( 16, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem10(){
        //lets add a negative number of baseball bats
        assertEquals(-1,testLocker.addItem(item0,-9));
        assertTrue(testLocker.getInventory().isEmpty());
        assertEquals( 20, testLocker.getAvailableCapacity());
        assertEquals(0,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem11(){
        //check that 2 locker communicate with the same LTS
        testLocker2 = new Locker(20);
        assertEquals(1,testLocker.addItem(item3,2));
        assertEquals(1,testLocker2.addItem(item3,2));
        // should move all items to the LTS because it takes more then 50%.
        // and 20% or  less is 0 items
        assertEquals(4,testLocker.getLongTerm().getItemCount("spores engine"));
    }
    /** ******* tests to part C - football and baseball bat ******* */

    @Test
    public void testAddItem12(){
        //lets check a simple adding of football and then baseball bat
        assertEquals(0,testLocker.addItem(item4,1));
        assertEquals(-2,testLocker.addItem(item0,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(16, testLocker.getAvailableCapacity());
        assertEquals(1,testLocker.getItemCount("football"));
        assertEquals(0,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem13(){
        //lets check a simple adding of baseball bat and then football
        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(-2,testLocker.addItem(item4,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(18, testLocker.getAvailableCapacity());
        assertEquals(1,testLocker.getItemCount("baseball bat"));
        assertEquals(0,testLocker.getItemCount("football"));
    }
    @Test
    public void testAddItem14(){
        //now we will try to add football to locker that contains baseball bat but there is no
        // place for football anyway. the method addItem should return -2 because it need FIRST
        // to check the condition of the contradicting items
        assertEquals(0,testLocker.addItem(item0,2));
        assertEquals(0,testLocker.addItem(item3,1));
        assertEquals(0,testLocker.addItem(item2,1));
        assertEquals(-2,testLocker.addItem(item4,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(1, testLocker.getAvailableCapacity());
        assertEquals(2,testLocker.getItemCount("baseball bat"));
        assertEquals(1,testLocker.getItemCount("helmet, size 3"));
        assertEquals(1,testLocker.getItemCount("spores engine"));
        assertEquals(0,testLocker.getItemCount("football"));
    }
    /*****************************************************************************************************************/
    @Test
    public void testAddItem15(){
        //lets try to add 1 spores engine and then again 1 spores engine, should transfer both of them to the LTS
        assertEquals(0,testLocker.addItem(item3,1));
        assertEquals(1,testLocker.addItem(item3,1));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(0,testLocker.getItemCount("spores engine"));
        assertEquals(20, testLocker.getAvailableCapacity());
    }
    @Test
    public void testAddItem16() {
        //lets add baseball bat and then lets try to add a negative number of a football items. as described in the
        //ex description, the method should first check the condition of the football-baseball relation - which means
        //that the method should return -2 instead of -1 (because of the negative number)
        assertEquals(0,testLocker.addItem(item0,1));
        assertEquals(-2,testLocker.addItem(item4,-5));
        assertEquals(1,testLocker.getItemCount("baseball bat"));
        assertEquals(0,testLocker.getItemCount("football"));
        assertEquals(18, testLocker.getAvailableCapacity());
    }
    @Test
    public void testAddItem17() {
        //now we will add 1000 capacity units to the long-term storage (to fill all available capacity)
        // and trying to add items that will have to pass to the long-term storage, there is no place there
        // so it will have to return -1 and do nothing.
        testLocker.getLongTerm().addItem(item3,100);
        assertEquals(-1,testLocker.addItem(item3,2));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(20, testLocker.getAvailableCapacity());
        assertTrue(testLocker.getInventory().isEmpty());
    }
    @Test
    public void testAddItem18() {
        //now we will add 1000 capacity units to the long-term storage (to fill all available capacity)
        // and trying to add items in addition to items that already in the locker from the same type
        // that will have to pass to the long-term storage, there is no place there
        // so it will have to return -1 and do nothing.
        testLocker.getLongTerm().addItem(item3,100);
        assertEquals(0,testLocker.addItem(item0,4));
        assertEquals(-1,testLocker.addItem(item0,2));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(12, testLocker.getAvailableCapacity());
        assertEquals(4,testLocker.getItemCount("baseball bat"));
    }
    @Test
    public void testAddItem19() {
        //similar to testAddItem18
        testLocker.getLongTerm().addItem(item3,100);
        assertEquals(0,testLocker.addItem(item0,4));
        assertEquals(0,testLocker.addItem(item1,2));
        assertEquals(-1,testLocker.addItem(item0,2));
        assertEquals(20,testLocker.getCapacity());
        assertEquals(6, testLocker.getAvailableCapacity());
        assertEquals(4,testLocker.getItemCount("baseball bat"));
        assertEquals(2,testLocker.getItemCount("helmet, size 1"));
    }
    @Test
    public void testAddItem20() {
        //now we will check adding 0 items of baseball bat and then 1 football. should return 0 because its
        // legal because there is no baseball bats in the locker
        assertEquals(0,testLocker.addItem(item0,0));
        assertEquals(0,testLocker.addItem(item4,1));
    }
    @Test
    public void testAddItem21(){
        //now we wil check adding 0 items when locker is full. should return 0.
        testLocker.addItem(item3,1);
        testLocker.addItem(item0,5);
        assertEquals(0,testLocker.addItem(item0,0));
    }
}
