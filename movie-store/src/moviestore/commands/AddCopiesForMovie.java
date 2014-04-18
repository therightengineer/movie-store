
package moviestore.commands;

import moviestore.Inventory;

public class AddCopiesForMovie implements Command {
	private int uniqueId;
	private int numOfNewCopies;

	public AddCopiesForMovie(int uniqueId, int numOfNewCopies){
		this.uniqueId = uniqueId;
		this.numOfNewCopies = numOfNewCopies;
	}

	@Override
	public void execute(Inventory inventory) {
		inventory.addCopies(uniqueId,numOfNewCopies);
	}

}
