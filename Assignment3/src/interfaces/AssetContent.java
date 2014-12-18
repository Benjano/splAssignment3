package interfaces;

public interface AssetContent {

	/**
	 * @return The time needs to repair the asset item
	 */
	double calculateRepairTime();

	/**
	 * Damage the content
	 * 
	 * @param damagePrecentage
	 */
	void damageAssetContent(double damagePrecentage);

	/**
	 * Fix the asset content to health = 100;
	 */
	void fixAssetContent();

	/**
	 * @return The name of the asset
	 */
	String getName();

	/**
	 * @return The health of the AssetContent
	 */
	double getHealth();
}
