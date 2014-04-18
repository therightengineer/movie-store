
package moviestore.commands;

import moviestore.Inventory;

public class SellMovie implements Command {

	private int uniqueId;

	public SellMovie(int uniqueId){
		this.uniqueId = uniqueId;
	}


	@Override
	public void execute(Inventory inventory) {
		inventory.sellMovie(uniqueId);
	}

}
