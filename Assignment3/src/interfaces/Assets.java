package interfaces;

import java.util.List;

public interface Assets {

	/**
	 * @return ArrayList of damaged assets
	 */
	List<Asset> getDamagedAssets();

	/**
	 * Find asset by type and size
	 * 
	 * @param type
	 * @param size
	 * @return Assets that match the search
	 */
	List<Asset> findAssetByTypeAndSize(String type, int size);

	/**
	 * Adds a new Asset to the collection
	 * 
	 * @param asset
	 */
	void addAsset(Asset asset);

}
