
package moviestore.decorator;

import moviestore.Iinventory;
import moviestore.Inventory;
import moviestore.commands.Command;
import moviestore.model.Movie;
import moviestore.util.InventoryUtil;


/**
 * Base Class for all the inventory decorators
 */
public abstract class InventoryDecorator implements Iinventory{
	Iinventory startingInventory;

	public void createAndSaveCommand(Command command){
		InventoryUtil.recordCommand(command,(Inventory)startingInventory);
	}
}
