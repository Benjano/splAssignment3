package implement;

import java.util.logging.Logger;

import interfaces.ClerkDetails;

public class ClerkDetailsImpl implements ClerkDetails {
	
	private String fName;
	private Location fLocation;
	private Logger fLogger;
	
	public ClerkDetailsImpl(String name, Location location){
		this.fName = name;
		this.fLocation = location;
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}
	
	public String getName() {
		return fName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Clerk Name: ");
		builder.append(fName);
		builder.append(". Clerk Location: ");
		builder.append(fLocation);
		return builder.toString();
	}

	@Override
	public Location getLocation() {
		return fLocation;
	}
}
