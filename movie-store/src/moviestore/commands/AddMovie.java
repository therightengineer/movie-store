package

moviestore.commands;

import moviestore.Inventory;

public class AddMovie implements Command {
	private String movieName;
	private double moviePrice;
	private int movieQuantity;

	private static final int ZERO_QUANTITY = 0;
	private static final double NOT_YET_PRICED = 0.0d;


	public AddMovie(String movieName){
		this(movieName,NOT_YET_PRICED,ZERO_QUANTITY);
	}

	public AddMovie(String movieName, double moviePrice){
		this(movieName,moviePrice,ZERO_QUANTITY);
	}
	public AddMovie(String movieName, int movieQuantity){
		this(movieName,NOT_YET_PRICED);
	}
	public AddMovie(String movieName, double moviePrice, int movieQuantity){
		this.movieName = movieName;
		this.moviePrice = moviePrice;
		this.movieQuantity = movieQuantity;
	}



	@Override
	public void execute(Inventory inventory) {
		if(moviePrice != NOT_YET_PRICED && movieQuantity != ZERO_QUANTITY){
			inventory.addMovie(movieName,moviePrice,movieQuantity);
		}else if(movieQuantity != ZERO_QUANTITY){
			inventory.addMovie(movieName,moviePrice);
		}else if(moviePrice != NOT_YET_PRICED ){
			inventory.addMovie(movieName,movieQuantity);
		}else{
			inventory.addMovie(movieName);
		}
	}
}