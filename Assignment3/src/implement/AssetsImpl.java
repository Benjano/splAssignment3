package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import interfaces.Asset;
import interfaces.Assets;

public class AssetsImpl implements Assets {

	protected List<Asset> fAssets;
	private Logger fLogger;

	// compare asset by size
	private Comparator<Asset> fCompareAsset = new Comparator<Asset>() {
		@Override
		public int compare(Asset asset1, Asset asset2) {
			return asset1.getSize() - asset2.getSize();
		}
	};

	public AssetsImpl() {
		this.fAssets = Collections.synchronizedList(new ArrayList<Asset>());
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}

	@Override
	public List<Asset> findAssetByTypeAndSize(String type, int size) {
		List<Asset> result = new Vector<Asset>();
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
