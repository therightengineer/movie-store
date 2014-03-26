package cs635;

public class AddMovieCommand implements Command {
	
	private Inventory inventory;
	
	AddMovieCommand(Inventory inventory){
		this.inventory = inventory;
	}

	@Override
	public void execute() {
		
	}

}
