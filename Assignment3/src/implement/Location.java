package implement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Location {

	private double fX, fY;
	private Logger fLogger;

	/**
	 * @param x
	 * @param y
	 */
	public Location(double x, double y) {
		this.fX = x;
		this.fY = y;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	public double calculateDistance(Location other) {
		double result = Math.sqrt(Math.pow(fX - other.fX, 2)
				+ Math.pow(fY - other.fY, 2));
		fLogger.log(
				Level.INFO,
				new StringBuilder().append("Calculating the distance between ")
						.append(this).append(" to ").append(other)
						.append(" which is ").append(result).toString());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location ");
		builder.append("(");
		builder.append(fX);
		builder.append(",");
		builder.append(fY);
		builder.append(")");
		return builder.toString();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Location)
			return ((Location) other).fX == fX & ((Location) other).fY == fY;
		return false;
	}
}
