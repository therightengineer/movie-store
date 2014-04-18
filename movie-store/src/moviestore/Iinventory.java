/**
 * Inventory Interface.
 * Defines the following functionalities to be implemented by concrete 
 * implementation classes
 * 
 * Add new movies.
 * Sell a movie in the inventory.
 * Add new copies of existing movies
 * Change the price of a movie
 * Find the price and/or quantity of a movie by either name or id. 
 */
package moviestore;

import moviestore.model.Movie;

/**
 * Interface class for inventory which defines the basic functions which
 * should be supported by the movie store
 */
public interface Iinventory<T extends Movie> {
	public void addMovie(String name);
	public void addMovie(String name, double price);
	public void addMovie(String name, int quantity);
	public void addMovie(String name, double price, int quantity);
	public void addCopies(int uniqueId, int numOfNewCopies);
	public void sellMovie(int uniqueId);
	public void changeMoviePrice(int uniqueId, double newPrice);
	public void changeMoviePrice(String movieName, double newPrice);
	public double findMoviePrice(String movieName);
	public double findMoviePrice(int uniqueId);
	public int findMovieQuantity(String movieName);
	public int findMovieQuantity(int uniqueId);
}
