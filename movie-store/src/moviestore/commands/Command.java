
package moviestore.commands;

import java.io.Serializable;

import moviestore.Inventory;

public interface Command extends Serializable{
	public abstract void execute(Inventory inventory);
}
