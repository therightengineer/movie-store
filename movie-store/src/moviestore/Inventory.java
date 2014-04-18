/**
 * This class provides the following business functionalities
 * Add new movies.
 * Sell a movie in the inventory.
 * Add new copies of existing movies
 * Change the price of a movie
 * Find the price and/or quantity of a movie by either name or id. 
 */
package moviestore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import moviestore.model.Movie;
import moviestore.util.InventoryUtil;
import moviestore.util.MovieFactory;

public class Inventory implements Iinventory<Movie>{

	private Map<Integer,Movie> movieInventory = new ConcurrentHashMap<>();
	MovieFactory movieFactory = new MovieFactory();
	private static final int DECREMENT_QUANTITY_BY_ONE = -1;
	private static final double NOT_IN_STOCK_PRICE = -1d;
	private static final int NOT_IN_STOCK_QUANTITY = 0;

	/**
	 * Add a new movie to Inventory
	 */
	@Override
	public void addMovie(String name){
		Movie newMovie = movieFactory.createMovie(name);
		InventoryUtil.addMovie(newMovie,this);
	}

	@Override
	public void addMovie(String name, double price) {
		Movie newMovie = movieFactory.createMovie(name, price);
		InventoryUtil.addMovie(newMovie,this);

	}

	@Override
	public void addMovie(String name, int quantity) {
		Movie newMovie = movieFactory.createMovie(name, quantity);
		InventoryUtil.addMovie(newMovie,this);
	}

	@Override
	public void addMovie(String name, double price, int quantity) {
		Movie newMovie = movieFactory.createMovie(name, price, quantity);
		InventoryUtil.addMovie(newMovie,this);
	}

	/**
	 * Add Copies of existing movie
	 */
	@Override
	public void addCopies(int uniqueId, int numOfNewCopies){
		InventoryUtil.updateMovieQuantity(uniqueId,numOfNewCopies,this);
	}

	/**
	 * Sell copy of a movie
	 * Decrements the movie quantity by one
	 */
	@Override
	public void sellMovie(int uniqueId){
		InventoryUtil.updateMovieQuantity(uniqueId,DECREMENT_QUANTITY_BY_ONE, this);
	}

	/**
	 * Update movie price given uniqu ID
	 * Find a movie by id and update the price
	 */
	@Override
	public void changeMoviePrice(int uniqueId, double newPrice){
		Movie movie = movieInventory.get(uniqueId);
		if(movie != null){
			movie.setPrice(newPrice);
		}
	}

	/**
	 * Update movie price given movie name
	 * Find a movie by name and update the price
	 */
	@Override
	public void changeMoviePrice(String movieName, double newPrice){
		Movie movie = InventoryUtil.findMovieByName(movieName,this);
		if(!movie.isNull()){
			changeMoviePrice(movie.getUniqueID(), newPrice);
		}
	}

	/**
	 * Find movie price by name
	 */
	@Override
	public double findMoviePrice(String movieName){
		Movie movie = InventoryUtil.findMovieByName(movieName,this);
		return InventoryUtil.getMoviePrice(movie);
	}

	/**
	 * Find movie price by id
	 */
	@Override
	public double findMoviePrice(int uniqueId){
		Movie movie = movieInventory.get(uniqueId);
		return movie != null ? InventoryUtil.getMoviePrice(movie) : NOT_IN_STOCK_PRICE;
	}

	/**
	 * Find movie quantity by name
	 */
	@Override
	public int findMovieQuantity(String movieName){
		Movie movie = InventoryUtil.findMovieByName(movieName,this);
		return InventoryUtil.getMovieQuantity(movie);
	}

	/**
	 * Find movie quantity by id
	 */
	@Override
	public int findMovieQuantity(int uniqueId){
		Movie movie = movieInventory.get(uniqueId);
		return movie != null ? InventoryUtil.getMovieQuantity(movie) : NOT_IN_STOCK_QUANTITY;
	}


	/**
	 * @return the movieInventory
	 */
	public Map<Integer, Movie> getMovieInventory() {
		return movieInventory;
	}

	/**
	 * @param movieInventory the movieInventory to set
	 */
	public void setMovieInventory(Map<Integer, Movie> movieInventory) {
		this.movieInventory = movieInventory;
	}

	@Override
	public int hashCode(){
		return this.getMovieInventory().size();
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Inventory)){
			return false;
		}
		Map<Integer,Movie> thisMovieInventory = this.getMovieInventory();
		Map<Integer,Movie> objMovieInventory = ((Inventory)obj).getMovieInventory();
		return compareMaps(thisMovieInventory,objMovieInventory);
	}

	/** 
	 * Compares two maps and returns true if they are equal
	 */
	private boolean compareMaps(Map<Integer,Movie> map1, Map<Integer,Movie>map2){
		if(map1.size() != map2.size()){
			return false;
		}
		for(Integer id : map1.keySet()){
			if(!(map1.get(id).equals(map2.get(id)))){
				return false;
			}
		}
		return true;
	}
}



