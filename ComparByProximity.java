import oop.ex3.searchengine.*;
import java.util.Comparator;

/**
 * An object he compares hotel-type objects, by Proximity to given point and then by number of POI.
 */
public class ComparByProximity implements Comparator<Hotel> {

    /** latitude of comparison points    */
    private double latitude;

    /** longitude of comparison points    */
    private double longitude;

    /**
     * constructor.
     * Initializes the comparison class according to the given point
     * @param latitude latitude of comparison points
     * @param longitude longitude of comparison points
     */
    public ComparByProximity(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * A function that compare two hotels
     * @param hotel1 Object of a hotel type
     * @param hotel2 Object of a hotel type
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal
     *  to, or greater than the second.
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        // Calculates the distance of each hotel to a point.
        double distOf1 = Math.sqrt(Math.pow(this.latitude-hotel1.getLatitude(), 2)
                + Math.pow(this.longitude-hotel1.getLongitude(), 2));
        double distOf2 = Math.sqrt(Math.pow(this.latitude-hotel2.getLatitude(), 2)
                + Math.pow(this.longitude-hotel2.getLongitude(), 2));

        // Compares the distances
        if (distOf1 >  distOf2) return 1;
        if (distOf1 <  distOf2) return -1;
        if (hotel1.getNumPOI() > hotel2.getNumPOI()) return -1;
        if (hotel1.getNumPOI() < hotel2.getNumPOI()) return 1;
        return 0;
    }
}