/**
 * Uses Null Pattern
 */
package moviestore.model;

public class NullMovie extends Movie {

	@Override
	public boolean isNull(){
		return true;
	}
}
