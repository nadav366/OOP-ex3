import oop.ex3.searchengine.*;
import java.util.Comparator;

/**
 * An object that compares hotel-type objects, by star rating and then by name.
 */
public class ComparByRating implements Comparator<Hotel> {

    /**
     * A function that compare two hotels
     * @param hotel1 Object of a hotel type
     * @param hotel2 Object of a hotel type
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating() < hotel2.getStarRating()) return 1;
        if (hotel1.getStarRating() > hotel2.getStarRating()) return -1;
        return (hotel1.getPropertyName().compareTo(hotel2.getPropertyName()));
    }
}
