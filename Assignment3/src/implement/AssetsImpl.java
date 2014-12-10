package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import interfaces.Asset;
import interfaces.Assets;

public class AssetsImpl implements Assets {

	List<Asset> fAssets;

	public AssetsImpl() {
		fAssets = Collections.synchronizedList(new ArrayList<Asset>());
	}

	@Override
	public List<Asset> getDamagedAssets() {
		List<Asset> result = Collections
				.synchronizedList(new ArrayList<Asset>());

		for (Asset asset : fAssets) {
			if (asset.isDamaged()) {
				result.add(asset);
			}
		}

		return result;
	}

	@Override
	public List<Asset> findAssetByTypeAndSize(String type, int size) {
		List<Asset> result = Collections
				.synchronizedList(new ArrayList<Asset>());

		for (Asset asset : fAssets) {
			if (asset.getType() == type & asset.getSize() == size) {
				result.add(asset);
			}
		}

		return result;
	}

	@Override
	public void addAsset(Asset asset) {
		fAssets.add(asset);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Assets includes: ");
		
		for (Asset asset : fAssets) {
			builder.append("\n").append(asset).append("\n");
		}
		
		return builder.toString();
	}
	
	

}
