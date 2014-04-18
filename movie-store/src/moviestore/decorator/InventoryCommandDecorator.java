package moviestore.decorator;

import moviestore.Inventory;
import moviestore.commands.AddCopiesForMovie;
import moviestore.commands.AddMovie;
import moviestore.commands.ChangeMoviePrice;
import moviestore.commands.SellMovie;

/**
 * Decorator for Inventory
 */
public class InventoryCommandDecorator extends InventoryDecorator {

	public InventoryCommandDecorator(Inventory inventory){
		startingInventory = inventory;
	}


	@Override
	public void addMovie(String name) {
		startingInventory.addMovie(name);
		createAndSaveCommand(new AddMovie(name));
	}

	@Override
	public void addMovie(String name, double price) {
		startingInventory.addMovie(name, price);
		createAndSaveCommand(new AddMovie(name, price));
	}

	@Override
	public void addMovie(String name, int quantity) {
		startingInventory.addMovie(name, quantity);
		createAndSaveCommand(new AddMovie(name,quantity));
	}

	@Override
	public void addMovie(String name, double price, int quantity) {
		startingInventory.addMovie(name, price, quantity);
		createAndSaveCommand(new AddMovie(name, price, quantity));
	}

	@Override
	public void addCopies(int uniqueId, int numOfNewCopies) {
		startingInventory.addCopies(uniqueId,numOfNewCopies);
		createAndSaveCommand(new AddCopiesForMovie(uniqueId,numOfNewCopies));
	}


	@Override
	public void sellMovie(int uniqueId) {
		startingInventory.sellMovie(uniqueId);
		createAndSaveCommand(new SellMovie(uniqueId));
	}


	@Override
	public void changeMoviePrice(int uniqueId, double newPrice) {
		startingInventory.changeMoviePrice(uniqueId,newPrice);
		createAndSaveCommand(new ChangeMoviePrice(uniqueId,newPrice));
	}


	@Override
	public void changeMoviePrice(String movieName, double newPrice) {
		startingInventory.changeMoviePrice(movieName,newPrice);
		createAndSaveCommand(new ChangeMoviePrice(movieName,newPrice));
	}


	@Override
	public double findMoviePrice(String movieName) {
		return startingInventory.findMoviePrice(movieName);
	}


	@Override
	public double findMoviePrice(int uniqueId) {
		return startingInventory.findMoviePrice(uniqueId);
	}


	@Override
	public int findMovieQuantity(String movieName) {
		return startingInventory.findMovieQuantity(movieName);
	}


	@Override
	public int findMovieQuantity(int uniqueId) {
		return startingInventory.findMovieQuantity(uniqueId);
	}



}
