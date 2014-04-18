package moviestore.commands;

import moviestore.Inventory;
import moviestore.util.InventoryUtil;

public class ChangeMoviePrice implements Command {
	private int uniqueId;
	private double newPrice;
	private String movieName;

	public ChangeMoviePrice(int uuid, double newPrice){ 
		this.uniqueId = uuid;
		this.newPrice = newPrice;
	}
	public ChangeMoviePrice(String movieName, double newPrice){
		this.movieName = movieName;
		this.newPrice = newPrice;
	}
	@Override
	public void execute(Inventory inventory) {
		if(InventoryUtil.isMovieNameValid(movieName)){
			inventory.changeMoviePrice(movieName, newPrice);
		}else{
			inventory.changeMoviePrice(uniqueId, newPrice);
		}
	}
}





