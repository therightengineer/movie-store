/**
 * This class has utility methods for inventory
 */
package cs635;

import java.util.Map;

public class InventoryUtil {

    public static Movie findMovieByName(String movieName, Map<Integer,Movie> movieInventory){
        if(isMovieNameValid(movieName)){
            for(Movie movie : movieInventory.values()){
                if(movieName.equalsIgnoreCase(movie.getName())){
                    return movie;
                }
            }
        }
        return new NullMovie();
    }
    
    public static boolean isMovieNameValid(String name) {
        return null != name && name.length() != 0;
    }
}
