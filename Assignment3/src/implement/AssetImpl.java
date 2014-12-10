package implement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import consts.AssetStatus;
import interfaces.Asset;
import interfaces.AssetContent;

public class AssetImpl implements Asset {

	private String fName, fType;
	private Location fLocation;
	private int fSize;
	private double fCostPerNight;
	private Map<String, AssetContent> fAssetContent;
	private AssetStatus fStatus;

	/**
	 * @param fName
	 * @param fType
	 * @param fLocation
	 * @param fSize
	 * @param fCostPerNight
	 */
	public AssetImpl(String name, String type, Location location,
			AssetStatus status, double costPerNight, int size) {
		super();
		this.fName = name;
		this.fType = type;
		this.fLocation = location;
		this.fAssetContent = new ConcurrentHashMap<String, AssetContent>();
		this.fStatus = status;
		this.fCostPerNight = costPerNight;
		this.fSize = size;
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public AssetStatus getStatus() {
		return fStatus;
	}

	@Override
	public Location getLocation() {
		return fLocation;
	}

	@Override
	public String getType() {
		return fType;
	}

	@Override
	public double getCostPerNight() {
		return fCostPerNight;
	}

	@Override
	public int getSize() {
		return fSize;
	}

	@Override
	public void addAssetContent(AssetContent assetContent) {
		fAssetContent.put(assetContent.getName(), assetContent);
	}
}
