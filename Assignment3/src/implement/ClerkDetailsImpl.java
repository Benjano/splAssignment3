package implement;

import interfaces.ClerkDetails;

public class ClerkDetailsImpl implements ClerkDetails {

	private String fName;
	private Location fLocation;

	public ClerkDetailsImpl(String name, Location location) {
		this.fName = name;
		this.fLocation = location;
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public Location getLocation() {
		return fLocation;
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
