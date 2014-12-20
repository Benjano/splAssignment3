package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import interfaces.Asset;
import interfaces.Assets;

public class AssetsImpl implements Assets {

	List<Asset> fAssets;

	Comparator<Asset> fCompareAsset = new Comparator<Asset>() {

		@Override
		public int compare(Asset asset1, Asset asset2) {
			return asset1.getSize() - asset2.getSize();
		}
	};

	public AssetsImpl() {
		fAssets = Collections.synchronizedList(new ArrayList<Asset>());
	}

	@Override
	public Vector<Asset> getDamagedAssets() {
		Vector<Asset> result = new Vector<Asset>();

		for (Asset asset : fAssets) {
			if (asset.isDamaged()) {
				result.add(asset);
			}
		}

		return result;
	}

	@Override
	public Vector<Asset> findAssetByTypeAndSize(String type, int size) {
		Vector<Asset> result = new Vector<Asset>();

		for (Asset asset : fAssets) {
			if (asset.getType().equals(type) & asset.getSize() >= size) {
				result.add(asset);
			}
		}

		Collections.sort(result, fCompareAsset);

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
