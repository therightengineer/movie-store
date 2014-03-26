/**
 * This class defines a Movie
 */
package cs635;
public class Movie {
    private String name;
    private double price;
    private int uniqueID;
    private int quantity;

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the uniqueID
     */
    public int getUniqueID() {
        return uniqueID;
    }
    /**
     * @param uniqueID the uniqueID to set
     */
    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }
    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
	public boolean isNull(){
		return false;
	}
}