package Tests;

import implement.AssetImpl;
import implement.Location;
import interfaces.AssetContent;

public class AssetTest extends AssetImpl {

	public AssetTest(String name, String type, Location location,
			double costPerNight, int size) {
		super(name, type, location, costPerNight, size);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return The number of asset content
	 */
	public int getNumberOfAssetContent() {
		return this.fAssetContent.size();
	}

	/**
	 * @return The asset content at i
	 */
	public AssetContent getAssetContent(int i) {
		if (i >= 0 & i < fAssetContent.size()) {
			return fAssetContent.get(i);
		}
		return null;
	}

}
