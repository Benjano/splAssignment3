package interfaces;

import implement.Location;

public interface ClerkDetails {

	/**
	 * @return The name of the clerk
	 */
	public String getName();
	
	/**
	 * @return The location of the clerk
	 */
	public Location getLocation();
}
