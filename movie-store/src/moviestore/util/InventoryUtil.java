/**
 * This class has utility methods for inventory
 */
package moviestore.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import moviestore.Inventory;
import moviestore.commands.Command;
import moviestore.model.Movie;
import moviestore.model.NullMovie;

public class InventoryUtil {

	private InventoryUtil(){
	}

	private static final String COMMAND_FILE_NAME = "/Users/src/moviestore/command.txt";
	private static final String MEMENTO_FILE_NAME = "/Users/src/moviestore/memento.txt";
	private static final String MEMENTO_FILE_NAME_SAFE = "/Users/src/moviestore/mementoTemp.txt";
	private static final double NOT_IN_STOCK_PRICE = 0;
	private static final int NOT_IN_STOCK_QUANTITY = 0;

	private static List<Command> commandCache = new ArrayList<>();


	public static void addMovie(Movie newMovie, Inventory inventory) {
		Map<Integer,Movie> movieInventory = inventory.getMovieInventory();
		if(!newMovie.isNull()){
			movieInventory.put(newMovie.getUniqueID(), newMovie);
		}
	}

	/**
	 * This method locates the movie in Inventory given movie name
	 */
	public static Movie findMovieByName(String movieName, Inventory inventory){
		Map<Integer,Movie> movieInventory = inventory.getMovieInventory();
		if(isMovieNameValid(movieName)){
			for(Movie movie : movieInventory.values()){
				if(movieName.equalsIgnoreCase(movie.getName())){
					return movie;
				}
			}
		}
		return new NullMovie();
	}

	public static int findIDForMovie(String movieName, Inventory inventory){
		Movie movie = findMovieByName(movieName, inventory);
		return null != movie ? movie.getUniqueID() : 0;
	}

	/**
	 * Updates the count of # of copies for a movie
	 */
	public static void updateMovieQuantity(int uniqueId, int numOfNewCopies, Inventory inventory) {
		Map<Integer,Movie> movieInventory = inventory.getMovieInventory();
		Movie movie = movieInventory.get(uniqueId);
		if(movie != null){
			int numOfExistingCopies = movie.getQuantity();
			int updatedCount = numOfExistingCopies + numOfNewCopies;
			if(updatedCount >= 0){
				movie.setQuantity(updatedCount);
			}
		}
	}


	public static double getMoviePrice(Movie movie) {
		return movie.isNull() ? NOT_IN_STOCK_PRICE : movie.getPrice();
	}


	public static int getMovieQuantity(Movie movie) {
		return movie.isNull() ? NOT_IN_STOCK_QUANTITY : movie.getQuantity();
	}

	/**
	 * Determines if the movie name considered valid
	 */
	public static boolean isMovieNameValid(String name) {
		return null != name && name.length() != 0;
	}

	/**
	 * This method records the command passed to it onto a list created on a file and save to memento if command size is greater than 10
	 */
	public static void recordCommand(Command command, Inventory inventory){
		if(commandCache.size() > 3){
			InventoryUtil.recordMemento(inventory);
			File f = new File(COMMAND_FILE_NAME);
			if(f.exists()){
				f.delete();
			}
			commandCache.clear();
		}else{
			commandCache.add(command);
			writeToFile(commandCache,COMMAND_FILE_NAME);
		}
	}

	/**
	 * This method writes the current state of Inventory on to a new  temp file and once is successfull then copy it to the original file
	 */
	public static void recordMemento(Inventory inventory) {
		Map<Integer, Movie> movieInventory = inventory.getMovieInventory();
		Memento memento = new Memento();
		memento.setMemento(movieInventory);
		File f = new File(MEMENTO_FILE_NAME); 
		if(f.exists()){
			writeToFile(memento,MEMENTO_FILE_NAME_SAFE); 
			copyFileUsingFileChannels(MEMENTO_FILE_NAME_SAFE, MEMENTO_FILE_NAME);
			commandCache.clear();
		}else{
			writeToFile(memento,MEMENTO_FILE_NAME);
			commandCache.clear();
		}
	}

	/**
	 * This method recreated the last stable state of Inventory by retrieving
	 * list of all past commands and applying them to the passed inventory object.
	 */
	public static void restoreState(Inventory inventory){
		retriveAndSetMementoStateToInventory(inventory);
		retriveAndFireCommandsOnInventory(inventory);
	}


	private static void retriveAndFireCommandsOnInventory(Inventory inventory) {
		List<Command> retrivedCommands = readCommandHistory();
		if(retrivedCommands != null){
			for(Command command: retrivedCommands){
				command.execute(inventory);
			}
		}
	}


	private static void retriveAndSetMementoStateToInventory(Inventory inventory) {
		Map<Integer, Movie> retrievedMemento = readMemento();
		if(null != retrievedMemento){
			inventory.setMovieInventory(retrievedMemento);  
		}else{
			inventory.setMovieInventory(new ConcurrentHashMap<Integer,Movie>());
		}
	}

	/**
	 * This method returns last saved Inventory memento if any.
	 */
	private static Map<Integer, Movie> readMemento() {

		File f = new File(MEMENTO_FILE_NAME);

		if(f.exists()){
			Object object = readFromFile(MEMENTO_FILE_NAME_SAFE);
			Memento retrivedMemento = (Memento)object;
			return retrivedMemento.getMemento();
		}else{
			System.out.println(MEMENTO_FILE_NAME +" File not found!");
			return null;
		}

	}

	/** NEED TO WORK ON THIS LATER
	 * This method reads the history of all the commands fired by the user
	 * from file and returns it as a List of Commands.
	 */
	private static List<Command> readCommandHistory() {
		File f = new File(COMMAND_FILE_NAME);
		if(f.exists()){
			Object object = readFromFile(COMMAND_FILE_NAME);
			if(object instanceof List){
				return (List<Command>)object;
			}
		}else{
			System.out.println(COMMAND_FILE_NAME +" File not found!");
			return null;
		}
		return null;
	}

	/**
	 * Returns the object read from the specified file. 
	 */
	private static Object readFromFile(String fileName) {
		FileInputStream fileIn = null;
		ObjectInputStream objectIn = null;
		try{
			fileIn = new FileInputStream(fileName);
			objectIn = new ObjectInputStream(fileIn);
			return objectIn.readObject();
		} catch (FileNotFoundException | ClassNotFoundException e ) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			closeInputConnections(fileIn,objectIn);
		}
		return null;
	}


	private static void writeToFile(Object object, String fileName) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objectOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(object);
			fileOut.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			closeOutputConnections(fileOut,objectOut);
		}
	}

	/**
	 * Closes all the openConnections opened for writing to file
	 */
	private static void closeOutputConnections(FileOutputStream fileOut,ObjectOutputStream objectOut) {
		try {
			fileOut.close();
			objectOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes all input connections opened for reading from file
	 */
	private static void closeInputConnections(FileInputStream fileIn,
			ObjectInputStream objectIn) {
		try {
			fileIn.close();
			objectIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//new memento is saved to new temp file and copy to main file. 
	private static void copyFileUsingFileChannels(String mementoFileNameSafe, String mementoFileName){
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(mementoFileNameSafe).getChannel();
			outputChannel = new FileOutputStream(mementoFileName).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} catch(IOException e) {
			e.printStackTrace();
		}finally{
			try {
				inputChannel.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				outputChannel.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	// for testing pursope
	public static void displayInventory(Inventory inventory){
		Map<Integer,Movie> movieList = inventory.getMovieInventory();
		for(int id : movieList.keySet()){
			System.out.println("ID: " + id + " Movie Name " + (movieList.get(id)).getName());
		}
	}
}
