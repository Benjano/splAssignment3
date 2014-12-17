package interfaces;

import java.util.Vector;

public interface Assets {

	/**
	 * @return ArrayList of damaged assets
	 */
	Vector<Asset> getDamagedAssets();

	/**
	 * Find asset by type and size
	 * 
	 * @param type
	 * @param size
	 * @return Assets that match the search
	 */
	Vector<Asset> findAssetByTypeAndSize(String type, int size);

	/**
	 * Adds a new Asset to the collection
	 * 
	 * @param asset
	 */
	void addAsset(Asset asset);

}
