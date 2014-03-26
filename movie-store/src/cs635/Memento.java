/**
 * 
 */
package cs635;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String,Movie> movieInventory = new ConcurrentHashMap<>();

	public void setMemento(Map<String,Movie> movieInventory){
		this.movieInventory = movieInventory;
	}

	public Map<String,Movie> getMemento(){
		return movieInventory;
	}
	
}
