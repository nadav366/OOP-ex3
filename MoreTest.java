import oop.ex3.searchengine.Hotel;
import org.junit.*;
import static org.junit.Assert.*;

public class MoreTest {

    private BoopingSite siteTest1;
    private BoopingSite siteTest2;
    private Hotel[] hotels;

    /**
     * @param x1 - first x coordinate
     * @param y1 - first y coordinate
     * @param x2 - second x coordinate
     * @param y2 - second y coordinate
     * @return the distance between (x1,y1) to (x2,y2)
     * note : i assume this method works well because its Java Math library.
     */
    public double getDistance(double x1, double y1, double x2, double y2) {
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.hypot(a, b);
    }
    @Before
    public void createTestObject() {
        siteTest1 = new BoopingSite("hotels_tst1.txt");
        siteTest2 = new BoopingSite("hotels_tst2.txt");
    }
    @Test
    public void testRating1() {
        hotels = siteTest1.getHotelsInCityByRating("manali");
        int res1, res2;
        for (int i = 0; i < hotels.length - 1; i++) {
            res1 = hotels[i].getStarRating();
            res2 = hotels[i + 1].getStarRating();
            assertTrue(res1 >= res2);
            assertEquals("manali", hotels[i].getCity());
            if (res1 == res2)
                assertTrue(hotels[i].getPropertyName().compareTo(hotels[i + 1].getPropertyName()) <= 0);
        }
        assertEquals("manali", hotels[hotels.length - 1].getCity());
    }
    @Test
    public void testRating2() {
        hotels = siteTest2.getHotelsInCityByRating("manali");
        assertEquals(0, hotels.length);
    }
    @Test
    public void testRating3() {
        hotels = siteTest1.getHotelsInCityByRating("city that doesn't exists");
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity1() {
        // checking that works on (0,0)
        this.testHelperProximityInCity("manali", 0, 0);
    }
    @Test
    public void testProximityInCity2() {
        //checking that works on (+,+)
        this.testHelperProximityInCity("manali", 20.2, 18.768);
    }
    @Test
    public void testProximityInCity3() {
        //checking that works on (+,-)
        this.testHelperProximityInCity("manali", 35.22, -11.231);
    }
    @Test
    public void testProximityInCity4() {
        //checking that works on (-,-)
        this.testHelperProximityInCity("manali", -97.28, -112);
    }
    @Test
    public void testProximityInCity5() {
        //checking that works on (-,+)
        this.testHelperProximityInCity("manali", -54.41, 251.25);
    }
    @Test
    public void testProximityInCity6() {
        hotels = siteTest2.getHotelsInCityByProximity("manali", 152.41, 65.21);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity7() {
        hotels = siteTest1.getHotelsInCityByProximity("city that doesn't exists", 312.31, -125.42);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity8() {
        // now we will check if it returns empty array if its illegal coordinates
        this.testHelperProximityInCity("manali",-154.41, 151.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity9() {
        // now we will check if it returns empty array if its illegal coordinates
        this.testHelperProximityInCity("manali",154.41, 151.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity10() {
        // now we will check if it returns empty array if its illegal coordinates
        this.testHelperProximityInCity("manali",54.41, 251.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximityInCity11() {
        // now we will check if it returns empty array if its illegal coordinates
        this.testHelperProximityInCity("manali",54.41, -251.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximity1() {
        // checking that works on (0,0)
        this.testHelperProximity(0, 0);
    }
    @Test
    public void testProximity2() {
        //checking that works on (+,+)
        this.testHelperProximity(20.2, 18.768);
    }
    @Test
    public void testProximity3() {
        //checking that works on (+,-)
        this.testHelperProximity(35.22, -11.231);
    }
    @Test
    public void testProximity4() {
        //checking that works on (-,-)
        this.testHelperProximity(-87.28, -112);
    }
    @Test
    public void testProximity5() {
        //checking that works on (-,+)
        this.testHelperProximity(-54.41, 151.25);

    }
    @Test
    public void testProximity6() {
        hotels = siteTest2.getHotelsByProximity(52.41, 65.21);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximity7() {
        // now we will check if it returns empty array if its illegal coordinates
        hotels = siteTest1.getHotelsByProximity(-154.41, 151.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximity8() {
        // now we will check if it returns empty array if its illegal coordinates
        hotels = siteTest1.getHotelsByProximity(154.41, 151.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximity9() {
        // now we will check if it returns empty array if its illegal coordinates
        hotels = siteTest1.getHotelsByProximity(54.41, 251.25);
        assertEquals(0, hotels.length);
    }
    @Test
    public void testProximity10() {
        // now we will check if it returns empty array if its illegal coordinates
        hotels = siteTest1.getHotelsByProximity(54.41, -251.25);
        assertEquals(0, hotels.length);
    }
    /**
     * helper method - checks that the array sorted by the given latitude and longitude
     */
    private void testHelperProximity(double latitude, double longitude) {
        double dist1, dist2;
        hotels = siteTest1.getHotelsByProximity(latitude, longitude);
        for (int i = 0; i < hotels.length - 1; i++) {
            dist1 = this.getDistance(latitude, longitude, hotels[i].getLatitude(), hotels[i].getLongitude());
            dist2 = this.getDistance(latitude, longitude, hotels[i + 1].getLatitude(), hotels[i + 1].getLongitude());
            assertTrue(dist1 <= dist2);
            if (dist1 == dist2)
                assertTrue(hotels[i].getNumPOI() >= hotels[i + 1].getNumPOI());
        }
    }
    /**
     * helper method - checks that the array sorted by the given latitude and longitude,
     * and every hotel placed in the given city
     */
    private void testHelperProximityInCity(String city, double latitude, double longitude) {
        double dist1, dist2;
        hotels = siteTest1.getHotelsInCityByProximity(city, latitude, longitude);
        for (int i = 0; i < hotels.length - 1; i++) {
            dist1 = this.getDistance(latitude, longitude, hotels[i].getLatitude(), hotels[i].getLongitude());
            dist2 = this.getDistance(latitude, longitude, hotels[i + 1].getLatitude(), hotels[i + 1].getLongitude());
            assertTrue(dist1 <= dist2);
            if (dist1 == dist2)
                assertTrue(hotels[i].getNumPOI() >= hotels[i + 1].getNumPOI());
        }
    }

    /////////////////////////////////////////////


    private BoopingSite boopingSite1;
    private BoopingSite boopingSite2;

    /**
     * load the objects
     */
    @Before
    public void createTestObjects() {
        this.boopingSite1 = new BoopingSite("hotels_tst1.txt");
        this.boopingSite2 = new BoopingSite("hotels_tst2.txt");
    }

    /**
     * check the initialize of the objects
     */
    @Test
    public void testBoopingSite() {
        assertNotNull(this.boopingSite1);
        assertNotNull(this.boopingSite2);
    }

    /**
     * This method checks getHotelsInCityByRating method
     */
    @Test
    public void testGetHotelsInCityByRating(){
        // 0st check: Empty DataSet
        Hotel[] hotelsInCity0 = this.boopingSite2.getHotelsInCityByRating("manali");
        assertEquals(0, hotelsInCity0.length);

        // 1st check - city don't appear in the DataSet
        Hotel[] hotelsInCity1 = this.boopingSite2.getHotelsInCityByRating("Abu Gosh");
        assertEquals(0, hotelsInCity1.length);

        // 2st check: Method checker - Normal run
        Hotel[] hotelsInCity = this.boopingSite1.getHotelsInCityByRating("manali");
        for (int index = 0; index < (hotelsInCity.length -1); index++){
            int rate1 = hotelsInCity[index].getStarRating();
            int rate2 = hotelsInCity[index + 1].getStarRating();
            assertTrue(rate1 >= rate2);
            if (rate1 == rate2){
                assertTrue(hotelsInCity[index].getPropertyName().
                        compareTo(hotelsInCity[index+1].getPropertyName()) <= 0);
            }
        }
    }

    /**
     * This method checks getHotelsByProximity method
     */
    @Test
    public void testGetHotelsByProximity() {
        // 0st check: Empty DataSet
        Hotel[] hotelsInCityEmpty = this.boopingSite2.getHotelsInCityByRating("manali");
        assertEquals(0, hotelsInCityEmpty.length);

        // Basic Test: checks limits:

        // 1st check: latitude above 90
        Hotel[] hotelsInCity0 = this.boopingSite1.getHotelsByProximity(91, 77.17066482);
        assertEquals(0, hotelsInCity0.length);
        // 2st check: latitude below 90
        Hotel[] hotelsInCity1 = this.boopingSite1.getHotelsByProximity(-91, 77.17066482);
        assertEquals(0, hotelsInCity1.length);
        // 3st check: longitude above 180
        Hotel[] hotelsInCity2 = this.boopingSite1.getHotelsByProximity(-32.13729659, 181);
        assertEquals(0, hotelsInCity2.length);
        // 4st check: longitude below 180
        Hotel[] hotelsInCity3 = this.boopingSite1.getHotelsByProximity(-32.13729659, -181);
        assertEquals(0, hotelsInCity3.length);


        // Advanced testing:
        Hotel[] hotelsInCity = this.boopingSite1.getHotelsByProximity
                (32.13729659, 77.17066482);
        for (int index = 0; index < (hotelsInCity.length - 1); index++) {

            double distance1 = this.checkDistance(hotelsInCity[index],
                    32.13729659,77.17066482);
            double distance2 = this.checkDistance(hotelsInCity[index + 1],
                    32.13729659,77.17066482);
            assertTrue(distance1 <= distance2);
            if (distance1 == distance2) {
                assertTrue(hotelsInCity[index].getNumPOI() >= hotelsInCity[index+1].getNumPOI());
            }
        }
    }

    /**
     * This method checks getHotelsInCityByProximity method
     */
    @Test
    public void testGetHotelsInCityByProximity(){
        // 0st check: Empty DataSet
        Hotel[] hotelsInCityEmpty = this.boopingSite2.getHotelsInCityByRating("manali");
        assertEquals(0, hotelsInCityEmpty.length);

        // Basic Test: checks limits:

        // 0st check - city don't appear in the DataSet
        Hotel[] CityNotInData = this.boopingSite2.getHotelsInCityByRating("Abu Gosh");
        assertEquals(0, CityNotInData.length);

        // 1st check: latitude above 90
        Hotel[] hotelsInCity0 = this.boopingSite1.getHotelsByProximity(91, 77.17066482);
        assertEquals(0, hotelsInCity0.length);
        // 2st check: latitude below 90
        Hotel[] hotelsInCity1 = this.boopingSite1.getHotelsByProximity(-91, 77.17066482);
        assertEquals(0, hotelsInCity1.length);
        // 3st check: longitude above 180
        Hotel[] hotelsInCity2 = this.boopingSite1.getHotelsByProximity(-32.13729659, 181);
        assertEquals(0, hotelsInCity2.length);
        // 4st check: longitude below 180
        Hotel[] hotelsInCity3 = this.boopingSite1.getHotelsByProximity(-32.13729659, -181);
        assertEquals(0, hotelsInCity3.length);


        // Advanced testing:
        Hotel[] hotelsInCity = this.boopingSite1.getHotelsInCityByProximity
                ("manali",32.13729659, 77.17066482 );
        for (int index = 0; index < (hotelsInCity.length - 1); index++) {

            double distance1 = this.checkDistance(hotelsInCity[index],
                    32.13729659,77.17066482);
            double distance2 = this.checkDistance(hotelsInCity[index + 1],
                    32.13729659,77.17066482);
            assertTrue(distance1 <= distance2);
            if (distance1 == distance2) {
                assertTrue(hotelsInCity[index].getNumPOI() >= hotelsInCity[index+1].getNumPOI());
            }
        }
    }

    private double checkDistance(Hotel hotel, double latitude,double longtitude){
        double ac = Math.abs(hotel.getLatitude() - latitude);
        double cb = Math.abs(hotel.getLongitude() - longtitude);

        return Math.hypot(ac, cb);
    }


    /////////////////////////////////////

    private BoopingSite boopingSite = new BoopingSite("hotels_dataset.txt");
    private Hotel[] emptyArray = new Hotel[0];

    private boolean testerForRating(Hotel[] hotels){
        for (int i=0; i<hotels.length; i++){
            if (i == hotels.length-1) return true;
            if (hotels[i].getStarRating()> hotels[i+1].getStarRating()) continue;
            if (hotels[i].getStarRating()< hotels[i+1].getStarRating()) return false;
            else {int relative = hotels[i].getPropertyName().compareTo(hotels[i+1].getPropertyName());
                if (relative<0) continue;
                if (relative>0) return false;
            }
        }return true;
    }

    private double getDistance(Hotel hotel, final double latitude, final double longitude){
        double dx = hotel.getLatitude() - latitude;
        double dy = hotel.getLongitude() - longitude;
        return  Math.sqrt(dx * dx + dy * dy);
    }

    private boolean testerForProx(Hotel[] hotels, final double latitude, final double longitude) {
        for (int i = 0; i < hotels.length; i++) {
            if (i == hotels.length-1) return true;
            double distance1 = this.getDistance( hotels[i], latitude, longitude);
            double distance2 = this.getDistance( hotels[i+1], latitude, longitude);
            if (distance1 < distance2) continue;
            if (distance1 > distance2) return false;
            else {
                if (hotels[i].getNumPOI() > hotels[i+1].getNumPOI()) continue;
                if (hotels[i].getNumPOI() < hotels[i+1].getNumPOI()) return false;
            }
        }return true;
    }

    private boolean testerForCity(Hotel[] hotels, String city){
        for (int i = 0; i < hotels.length; i++) {
            if (!hotels[i].getCity().equals(city)) return false;
        }return true;
    }


    @Test
    public void getHotelsInCityByRatingTest1(){
        assertTrue(this.testerForRating(boopingSite1.getHotelsInCityByRating("manali")));
        assertNotEquals(0,boopingSite1.getHotelsInCityByRating("manali").length);
        assertArrayEquals(emptyArray,boopingSite1.getHotelsInCityByRating("Tel Aviv"));
    }

    @Test
    public void getHotelsInCityByRatingTest2(){
        assertTrue(this.testerForRating(boopingSite.getHotelsInCityByRating("manali")));
        assertNotEquals(0,boopingSite.getHotelsInCityByRating("manali").length);
        assertTrue(this.testerForRating(boopingSite.getHotelsInCityByRating("goa")));
        assertTrue(this.testerForRating(boopingSite.getHotelsInCityByRating("delhi")));
        assertArrayEquals(emptyArray,boopingSite.getHotelsInCityByRating("tel aviv"));
    }

    @Test
    public void getHotelsInCityByProximityTest1(){
        assertTrue(this.testerForProx(boopingSite.getHotelsInCityByProximity
                ("manali",0.0,0.0),0.0,0.0));
        assertTrue(this.testerForProx(boopingSite.getHotelsInCityByProximity
                ("manali",180.0,180.0), 180.0,180.0));
        assertTrue(this.testerForProx(boopingSite.getHotelsInCityByProximity
                ("manali",-180.0,-180.0), -180.0,-180.0));
        //Hotel[] hotels = boopingSite.getHotelsInCityByProximity
        //("manali",-180.0,-180.0);
        //Hotel hotel1 = hotels[1];Hotel hotel2 = hotels[2];
        //hotels[1] =hotel2; hotels[2] = hotel1;
        //assertFalse(this.testerForProx(hotels, -180.0,-180.0));

    }
    @Test
    public void getHotelsInCityByProximityTest2() {
        assertArrayEquals(emptyArray,boopingSite.getHotelsInCityByProximity
                ("Tel Aviv",0,0));
        assertArrayEquals(emptyArray,boopingSite.getHotelsInCityByProximity
                ("manal",0,0));
        assertArrayEquals(emptyArray,boopingSite.getHotelsInCityByProximity
                ("manalie",0,0));

    }
    @Test
    public void getHotelsInCityByProximityTest3(){
        assertTrue(this.testerForProx(boopingSite1.getHotelsInCityByProximity
                ("manali",111.0,331.30),111.0,331.30));
        assertArrayEquals(emptyArray,boopingSite1.getHotelsInCityByProximity
                ("manali",111.0,331.30));
        assertEquals(0,boopingSite1.getHotelsInCityByProximity
                ("manali",111.0,331.30).length);
    }

    @Test
    public void getHotelsByProximityTest1(){
        assertTrue(this.testerForProx(boopingSite.getHotelsByProximity
                (0.0,0.0),0.0,0.0));
        assertTrue(this.testerForProx(boopingSite.getHotelsByProximity
                (180.0,180.0),180.0,180.0));
        assertTrue(this.testerForProx(boopingSite.getHotelsByProximity
                (-180.0,-180.0),-180.0,-180.0));
        //Hotel[] hotels = boopingSite.getHotelsByProximity
        // (-180.0,-180.0);
        //Hotel hotel1 = hotels[1];Hotel hotel2 = hotels[2];
        //hotels[1] =hotel2; hotels[2] = hotel1;
        //assertFalse(this.testerForProx(hotels, -180.0,-180.0));
    }
    @Test
    public void getHotelsByProximityTest2() {
        assertArrayEquals(emptyArray,boopingSite2.getHotelsByProximity
                (0,0));
        assertTrue(this.testerForProx(boopingSite1.getHotelsByProximity
                (0.0,0.0),0.0,0.0));
        assertTrue(this.testerForProx(boopingSite1.getHotelsByProximity
                (180.0,180.0),180.0,180.0));
    }

    @Test
    public void getHotelsByProximityTest3(){
        assertArrayEquals(emptyArray,boopingSite1.getHotelsByProximity
                (111.0,331.30));
        assertTrue(this.testerForProx(boopingSite1.getHotelsByProximity
                (111.0,331.30),111.0,331.30));
        assertEquals(0,boopingSite1.getHotelsByProximity
                (111.0,331.30).length);
    }

    private String[] nameArray(Hotel[] hotels){
        String[] names = new String[hotels.length];
        for (int i=0 ; i<names.length; i++){
            names[i] = hotels[i].getPropertyName();
        }return names;
    }
    @Test
    public void compareArrays(){
        String[] hotelNames1 = this.nameArray(boopingSite.getHotelsInCityByRating("manali"));
        String[] hotelNames2 = this.nameArray(boopingSite1.getHotelsInCityByRating("manali"));
        assertArrayEquals(hotelNames1,hotelNames2);
    }

    @Test
    public void testCity1(){
        assertTrue(this.testerForCity(boopingSite.getHotelsInCityByRating("manali"),"manali"));
        assertTrue(this.testerForCity(boopingSite.getHotelsInCityByRating("goa"),"goa"));
        assertTrue(this.testerForCity(boopingSite.getHotelsInCityByProximity
                ("manali",0,0),"manali"));
        assertTrue(this.testerForCity(boopingSite.getHotelsInCityByProximity
                ("goa",0,0),"goa"));
    }

    @Test
    public void testCity2(){
        assertEquals(70,boopingSite.getHotelsInCityByRating("manali").length);
        assertEquals(63,boopingSite.getHotelsInCityByRating("srinagar").length);
        assertEquals(70,boopingSite.getHotelsInCityByProximity("manali",0,0).length);
        assertEquals(63,boopingSite.getHotelsInCityByProximity("srinagar",0,0).length);
    }


}
