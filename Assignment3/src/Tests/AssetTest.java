package Tests;

import consts.AssetStatus;
import implement.AssetImpl;
import implement.Location;

public class AssetTest extends AssetImpl {

	public AssetTest(String name, String type, Location location,
			AssetStatus status, double costPerNight, int size) {
		super(name, type, location, status, costPerNight, size);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return The number of asset content
	 */
	public int getNumberOfAssetContent() {
		return this.fAssetContent.size();
	}

}
