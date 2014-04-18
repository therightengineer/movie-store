/**
 * This class defines a Movie
 */
package moviestore.model;

import java.io.Serializable;

public class Movie implements Serializable{
	private String name;
	private double price;
	private int uniqueID;
	private int quantity;


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNull(){
		return false;
	}

	// to compare to movie objects for testing purpose
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(obj == this){
			return true;
		}
		if (!(obj instanceof Movie )){
			return false;
		}
		Movie movie = (Movie)obj;
		return movie.getUniqueID() == this.getUniqueID() &&
				movie.getName().equals(this.getName()) &&
				movie.getPrice() == this.getPrice() &&
				movie.getQuantity() == this.getQuantity();
	}

	@Override
	public int hashCode(){
		return this.getUniqueID();
	}
}