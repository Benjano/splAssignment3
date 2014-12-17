package implement;

public class Location {

	private double x, y;

	/**
	 * @param x
	 * @param y
	 */
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double calculateDistance(Location other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location ");
		builder.append("(");
		builder.append(x);
		builder.append(",");
		builder.append(y);
		builder.append(")");
		return builder.toString();
	}
}
