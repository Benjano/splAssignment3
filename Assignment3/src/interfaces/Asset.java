package interfaces;

import implement.Location;
import consts.AssetStatus;

public interface Asset {

	/**
	 * @return The name of the asset
	 */
	String getName();

	/**
	 * @return The location of the asset
	 */
	Location getLocation();

	/**
	 * @return The status of the asset
	 */
	AssetStatus getStatus();
	
	/**
	 * @return The type of the asset
	 */
	String getType();

	/**
	 * @return The cost per night
	 */
	double getCostPerNight();

	/**
	 * @return The number of people the asset can fit in
	 */
	int getSize();

	/**
	 * @param The
	 *            assetContent to be added to assetContent collection
	 */
	void addAssetContent(AssetContent assetContent);

}
