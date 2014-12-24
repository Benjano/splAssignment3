package interfaces;

import java.util.List;
import java.util.Vector;

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
	 * 
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
	 * Damage the asset content by damage precenatage 0-100
	 * 
	 * @param damagePrecentage
	 */
	void damageAssetContent(double damagePrecentage);

	/**
	 * @return A list of all the damaged asset content
	 */
	List<AssetContent> getDamagedAssetContent();

}
