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
	 * Change the status of the asset
	 * @param status
	 */
	void setStatus(AssetStatus status);

	/**
	 * @return The cost per night
	 */
	double getCostPerNight();

	/**
	 * @return The number of people the asset can fit in
	 */
	int getSize();
	
	/**
	 * @return True if the asset is damaged
	 */
	boolean isDamaged();

	/**
	 * @param assetContent
	 */
	void addAssetContent(AssetContent assetContent);
	
	/**
	 * @param name
	 * @return Array of assetContent
	 */
	AssetContent[] getAllAssetContent();

}
