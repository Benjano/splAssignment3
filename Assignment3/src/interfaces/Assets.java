package interfaces;

import java.util.List;

public interface Assets {

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
