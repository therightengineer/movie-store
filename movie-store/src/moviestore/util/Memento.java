
package moviestore.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import moviestore.model.Movie;

public class Memento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer,Movie> movieInventory = new ConcurrentHashMap<>();

	public void setMemento(Map<Integer,Movie> movieInventory){
		this.movieInventory = movieInventory;
	}

	public Map<Integer,Movie> getMemento(){
		return movieInventory;
	}

}
