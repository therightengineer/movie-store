/**
 * This class implements factory pattern to create Movie instances.
 */
package moviestore.util;

import java.util.concurrent.atomic.AtomicInteger;

import moviestore.model.Movie;
import moviestore.model.NullMovie;

public class MovieFactory {

	private static AtomicInteger uuid = new AtomicInteger(0);

	private static final int ZERO_QUANTITY = 0;
	private static final double NOT_YET_PRICED = 0.0d;

	public MovieFactory(){
	}

	public  Movie createMovie(String name){
		return createMovie(name,NOT_YET_PRICED,ZERO_QUANTITY);
	}

	public  Movie createMovie(String name, double price){
		return createMovie(name,price,ZERO_QUANTITY);
	}

	public  Movie createMovie(String name, int quantity){
		return createMovie(name,NOT_YET_PRICED,quantity);
	}

	public  Movie createMovie(String name, double price, int quantity){
		Movie movie = new Movie();
		if(InventoryUtil.isMovieNameValid(name)){
			movie.setName(name);
			movie.setPrice(price);
			movie.setUniqueID(uuid.incrementAndGet());
			movie.setQuantity(quantity);
			return movie;
		}
		return new NullMovie();
	}

}
