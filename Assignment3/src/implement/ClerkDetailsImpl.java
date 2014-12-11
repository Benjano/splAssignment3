package implement;

import interfaces.ClerkDetails;

public class ClerkDetailsImpl implements ClerkDetails {
	
	private String fName;
	private Location fLocation;
	
	public ClerkDetailsImpl(String name, Location location){
		this.fName = name;
		this.fLocation = location;
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
}
