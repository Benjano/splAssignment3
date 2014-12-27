package interfaces;

public interface DamageReport {

	/**
	 * @return The asset attached to the Damage report
	 */
	public Asset getAsset();

	/**
	 * @return The damage percentage
	 */
	public double getDamagePercentage();
}
