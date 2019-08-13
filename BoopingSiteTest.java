import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import oop.ex3.searchengine.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Test class to BoopingSite
 */
public class BoopingSiteTest {
    // Website objects for the test
    private BoopingSite siteBig;
    private BoopingSite siteEmpty;

    private Random random = new Random();

    // A map that holds all the cities and the number of hotels in them
    private Map<String, Integer> counter;

    /**
     * A function that defines objects that are needed for the test
     */
    @Before
    public void definition(){
        siteBig = new BoopingSite("hotels_dataset.txt");
        siteEmpty = new BoopingSite("hotels_tst2.txt");
        // Check that the boot succeeded
        assertNotNull(siteBig);
        assertNotNull(siteEmpty);
        // A map that holds all the cities and the number of hotels in them
         counter = getNumHotels();
    }

    /**
     * A function that examines the method getHotelsInCityByRating
     */
    @Test
    public void HotelsInCityByRatingTest(){
        // Check that a city does not exist, you receive an empty list
        assertEquals(0, siteBig.getHotelsInCityByRating("****").length);

        // Check that for a blank buffer, an empty list is returned
        assertEquals(0, siteEmpty.getHotelsInCityByRating("manali").length);

        // Checks the resulting list for each city
        for (String city: counter.keySet()){
            TestOneCityByRating(city);
        }
    }

    /**
     * A function that checks that the returned list is correct for one city, by Rating
     * @param city Name of the city
     */
    private void TestOneCityByRating(String city) {
        Hotel[] sortedArr = siteBig.getHotelsInCityByRating(city);
        // Checking that there is a list of hotels needed
        assertEquals((int)counter.get(city), sortedArr.length);
        //Checks that the list is ordered in the correct order
        for (int i=0;i<sortedArr.length-1;i++){
            assertEquals(city, sortedArr[i].getCity());//Checks that all hotels on the list belong to the city

            assertTrue(sortedArr[i].getStarRating() >= sortedArr[i+1].getStarRating());
            if (sortedArr[i].getStarRating() == sortedArr[i+1].getStarRating())
                 assertTrue(sortedArr[i].getPropertyName().compareTo(sortedArr[i + 1].getPropertyName()) <= 0);
        }
        // Checks that the last hotel belongs to the city
        if (sortedArr.length > 0) assertEquals(city, sortedArr[sortedArr.length-1].getCity());
    }


    /**
     * A function that examines the method getHotelsByProximity
     */
    @Test
    public void HotelsByProximityTest(){
        // Check that for invalid coordinates, an empty list is returned
        assertEquals(0,siteBig.getHotelsByProximity(-91.8,5.5).length);
        assertEquals(0,siteBig.getHotelsByProximity(43.2,187.6665).length);
        assertEquals(0,siteBig.getHotelsByProximity(91.8,5.5).length);
        assertEquals(0,siteBig.getHotelsByProximity(43.2,-187.6665).length);

        // Check that for a blank buffer, an empty list is returned
        assertEquals(0,siteEmpty.getHotelsByProximity(43.2,54.78).length);

        // Check that the coordinates 0,0 return the correct result
        oneCoordinatesTest(0, 0, siteBig.getHotelsByProximity(0, 0));

        // Random checker 10 coordinates.
        for (int i = 0; i<10; i++) {
            double lat = (double) random.nextInt(180) - 90;
            double longi = (double) random.nextInt(360) - 180;
            oneCoordinatesTest(lat, longi, siteBig.getHotelsByProximity(lat, longi));
        }
    }

    /**
     * A function that examines the method getHotelsInCityByProximity
     */
    @Test
        public void HotelsInCityByProximityTest(){
        // Check that a city does not exist, you receive an empty list
        assertTrue(siteBig.getHotelsInCityByProximity("****",0,0).length==0);

        // Check that for invalid coordinates, an empty list is returned
        assertEquals(0,siteBig.getHotelsInCityByProximity("Manali",-91.8,5.5).length);
        assertEquals(0,siteBig.getHotelsInCityByProximity("Manali",43.2,187.6665).length);
        assertEquals(0,siteBig.getHotelsInCityByProximity("Manali",91.8,5.5).length);
        assertEquals(0,siteBig.getHotelsInCityByProximity("Manali",43.2,-187.6665).length);

        // Check that for a blank buffer, an empty list is returned
        assertEquals(0,siteEmpty.getHotelsInCityByProximity("Manali",43.2,54.78).length);

        // Check that the coordinates 0,0 return the correct result
        oneCoordinatesTest(0, 0, siteBig.getHotelsInCityByProximity("Manali", 0, 0));

        // Check that for all cities in the database, correct lists are returned...
        for (String city: counter.keySet())
            oneCityByProximityTest(city);
    }

    /**
     * A function that checks that the returned list is correct for one city, by Proximity
     * @param city Name of the city
     */
    private void oneCityByProximityTest(String city) {
        // The test grinds 3 coordinates to test them
        for (int i = 0; i<3; i++) {
            double lat = (double) random.nextInt(180) - 90;
            double longi = (double) random.nextInt(360) - 180;
            Hotel[] inCity = siteBig.getHotelsInCityByProximity(city, lat, longi);
            // Check that all the hotels on the list belong to the city (Only in the first lottery)
            if (i==0) for (int j=0;j<inCity.length-1;j++) assertEquals(city, inCity[j].getCity());
            // Check that there is exactly the number of hotels you need
            assertEquals((int)counter.get(city), inCity.length);
            // Check that the list is correct
            oneCoordinatesTest(lat, longi, inCity);
        }

    }

    /**
     * The auxiliary function calculates the number of hotels in each city
     * @return A map containing all the cities and the number of hotels in the database.
     */
    private Map<String, Integer> getNumHotels() {
        Map<String, Integer> counter = new HashMap();
        for (Hotel hotel : HotelDataset.getHotels("hotels_dataset.txt")) {
            if (counter.containsKey(hotel.getCity()))
                counter.put(hotel.getCity(), counter.get(hotel.getCity()) + 1);
            else counter.put(hotel.getCity(), 1);
        }
        return counter;
    }

    /**
     * A function that accepts coordinates, and a list of hotels
     * and checks whether the hotels are arranged by proximity to the point.
     * @param lat Length Corridor
     * @param longi Width Corundity
     * @param sortedArr Hotels list for review
     */
    private void oneCoordinatesTest(double lat, double longi, Hotel[] sortedArr){
        double curr = 0;
        // Calculates the distance of the first hotel
        if (sortedArr.length>0)
            curr = getDistance(lat,sortedArr[0].getLatitude(),longi,sortedArr[0].getLongitude());
        for (int i=0;i<sortedArr.length-1;i++){
            // Calculates the distance of the next hotel on the list
            double next = getDistance(lat,sortedArr[i+1].getLatitude(),longi,sortedArr[i+1].getLongitude());
            // Checks that the order is correct
            assertTrue(curr <= next);
            // Checks that if there is equality, the list is organized according to the amount of POI
            if (curr == next) assertTrue(sortedArr[i].getNumPOI() >= sortedArr[i+1].getNumPOI());
            curr = next;
        }
    }

    /**
     * Auxiliary function. Calculates distance between two points.
     * @param x1 Coridante x of the first point
     * @param x2 Coridante x of the second point
     * @param y1 Coridante y of the first point
     * @param y2 Coridante y of the second point
     * @return distance between the points.
     */
    private double getDistance(double x1, double x2, double y1, double y2){
        return  Math.sqrt(
                    Math.pow(x1-x2, 2) +
                    Math.pow(y1-y2, 2));
    }
}
