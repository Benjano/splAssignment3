package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import consts.AssetStatus;
import interfaces.Asset;
import interfaces.AssetContent;

public class AssetImpl implements Asset {

	private final double ASSET_DAMAGED_HEALTH = 65;

	private String fName, fType;
	private Location fLocation;
	private int fSize;
	private double fCostPerNight;
	private List<AssetContent> fAssetContent;
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
		this.fAssetContent = Collections
				.synchronizedList(new ArrayList<AssetContent>());
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
	public boolean isDamaged() {
		for (AssetContent assetContent : fAssetContent) {
			if (assetContent.getHealth() < ASSET_DAMAGED_HEALTH)
				return true;
		}

		return false;
	}

	@Override
	public void addAssetContent(AssetContent assetContent) {
		fAssetContent.add(assetContent);
	}

	@Override
	public AssetContent[] getAllAssetContent() {
		AssetContent[] assetContents = new AssetContent[fAssetContent.size()];
		int k = 0;
		for (AssetContent assetContent : fAssetContent) {
			assetContents[k] = assetContent;
		}
		return assetContents;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset: ").append(fName).append("\nType: ")
				.append(fType).append("\n").append(fLocation)
				.append("\nSize: ").append(fSize).append("\nCost per night: ")
				.append(fCostPerNight);

		for (AssetContent assetContent : fAssetContent) {
			builder.append("\n").append(assetContent);
		}

		builder.append(" \nStatus: ").append(fStatus);

		return builder.toString();
	}

}
