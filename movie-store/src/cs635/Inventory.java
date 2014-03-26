/**
 * This class provides the following business functionalities
 * Add new movies.
 * Sell a movie in the inventory.
 * Add new copies of existing movies
 * Change the price of a movie
 * Find the price and/or quantity of a movie by either name or id. 
 */
package cs635;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {

	private Map<Integer,Movie> movieInventory = new ConcurrentHashMap<>();


	private static final int DECREMENT_QUANTITY_BY_ONE = -1;
	private static final double NOT_IN_STOCK_PRICE = -1d;
	private static final int NOT_IN_STOCK_QUANTITY = 0;

	public void addMovie(String name){
		Movie newMovie = MovieFactory.createMovie(name);
		addMovie(newMovie);
	}

	/**
	 * @param newMovie
	 */
	private void addMovie(Movie newMovie) {
		if(!newMovie.isNull()){
			movieInventory.put(newMovie.getUniqueID(), newMovie);
		}
	}

	public void addCopies(int uniqueId, int numOfNewCopies){
		updateMovieQuantity(uniqueId,numOfNewCopies);
	}

	private void updateMovieQuantity(int uniqueId, int numOfNewCopies) {
		Movie movie = movieInventory.get(uniqueId);
		if(movie != null){
			int numOfExistingCopies = movie.getQuantity();
			int updatedCount = numOfExistingCopies + numOfNewCopies;
			if(updatedCount >= 0){
				movie.setQuantity(updatedCount);
			}
		}
	}

	/**
	 * Decrements the movie quantity by one
	 * @param uuid
	 */
	public void sellMovie(int uniqueId){
		updateMovieQuantity(uniqueId,DECREMENT_QUANTITY_BY_ONE);
	}


	/**
	 * Find a movie by id and update the price
	 * @param uuid
	 * @param newPrice
	 */
	public void updateMoviePrice(int uniqueId, double newPrice){
		Movie movie = movieInventory.get(uniqueId);
		if(movie != null){
			movie.setPrice(newPrice);
		}
	}

	/**
	 * Find a movie by name and update the price
	 * @param movieName
	 * @param newPrice
	 */
	public void updateMoviePrice(String movieName, double newPrice){
		Movie movie = InventoryUtil.findMovieByName(movieName,movieInventory);
		if(!movie.isNull()){
			updateMoviePrice(movie.getUniqueID(), newPrice);
		}
	}

	/**
	 * Find movie price by name
	 * @param movieName
	 * @return
	 */
	public double findMoviePrice(String movieName){
		Movie movie = InventoryUtil.findMovieByName(movieName,movieInventory);
		return getMoviePrice(movie);
	}

	/**
	 * @param movie
	 * @return
	 */
	private double getMoviePrice(Movie movie) {
		return movie.isNull() ? NOT_IN_STOCK_PRICE : movie.getPrice();
	}

	/**
	 * Find movie price by id
	 * @param uuid
	 * @return
	 */
	public double findMoviePrice(int uniqueId){
		Movie movie = movieInventory.get(uniqueId);
		return movie != null ? getMoviePrice(movie) : NOT_IN_STOCK_PRICE;
	}

	/**
	 * Find movie quantity by name
	 * @param movieName
	 * @return
	 */
	public int findMovieQuantity(String movieName){
		Movie movie = InventoryUtil.findMovieByName(movieName,movieInventory);
		return getMovieQuantity(movie);
	}

	/**
	 * @param movie
	 * @return
	 */
	private int getMovieQuantity(Movie movie) {
		return movie.isNull() ? NOT_IN_STOCK_QUANTITY : movie.getQuantity();
	}

	/**
	 * Find movie quantity by id
	 * @param uuid
	 * @return
	 */
	public int findMovieQuantity(int uniqueId){
		Movie movie = movieInventory.get(uniqueId);
		return movie != null ? getMovieQuantity(movie) : NOT_IN_STOCK_QUANTITY;
	}

}