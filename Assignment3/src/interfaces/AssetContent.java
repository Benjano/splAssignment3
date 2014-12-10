package interfaces;

public interface AssetContent {

	/**
	 * @return The time needs to repair the asset item
	 */
	double calculateRepairTime();

	/**
	 * Set the health of the item
	 * 
	 * @param int health;
	 */
	void setHealth(int health);

	/**
	 * @return The name of the asset
	 */
	String getName();
}
