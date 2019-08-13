import java.util.ArrayList;
import oop.ex3.searchengine.*;

/**
 * A class that is a site comparing hotels according to different parameters
 */
public class BoopingSite {

    /** An array containing all hotels in the database    */
    private Hotel[] allHotels;

    /**
     * constructor.
     * Initializing the list of hotels to accommodate all hotels
     * @param name The name of the file that contains the database
     */
    public BoopingSite(String name){
        allHotels = HotelDataset.getHotels(name);
    }

    /**
     * A function that returns the list of all the hotels in a particular city, by rating
     * @param city Name the city for the hotels.
     * @return list of all the hotels in a particular city, by rating
     */
    public Hotel[] getHotelsInCityByRating(String city){
        ArrayList<Hotel> goodHotels = new ArrayList<>();
        for (Hotel hotel: allHotels)
            if (hotel.getCity().equals(city))
                goodHotels.add(hotel);

        goodHotels.sort(new ComparByRating()); // Sort the list
        Hotel[] result = new Hotel[goodHotels.size()]; // Copy ArrayList to Array

        return goodHotels.toArray(result);
    }

    /**
     * A function that returns the list of all the hotels, By their proximity to a given point
     * @param latitude latitude of comparison points
     * @param longitude longitude of comparison points
     * @return list of all the hotels, By their proximity to a given point
     */
        public Hotel[] getHotelsByProximity(double latitude, double longitude){
        // Validation of the given coordinates
        if (Math.abs(latitude) > 90 || Math.abs(longitude) > 180)
            return new Hotel[0];

        ArrayList<Hotel> goodHotels = new ArrayList<>();
        for (Hotel hotel: allHotels)
                goodHotels.add(hotel);

        goodHotels.sort(new ComparByProximity(latitude, longitude)); // Sort the list
        Hotel[] result = new Hotel[goodHotels.size()]; // Copy ArrayList to Array

        return goodHotels.toArray(result);
    }

    /**
     * A function that returns the list of all the hotels in a particular city,
     * by their proximity to a given point
     * @param city Name the city for the hotels.
     * @param latitude latitude of comparison points
     * @param longitude longitude of comparison points
     * @return the list of all the hotels in a particular city, by their proximity to a given point
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        // Import all hotels, according to their distance from the point
        Hotel[] hotelsByProximity = getHotelsByProximity(latitude, longitude);

        // Select only hotels from the given city
        ArrayList<Hotel> goodHotels = new ArrayList<>();
        for (Hotel hotel: hotelsByProximity)
            if (hotel.getCity().equals(city))
                goodHotels.add(hotel);

        Hotel[] result = new Hotel[goodHotels.size()];
        return goodHotels.toArray(result);
    }
}
