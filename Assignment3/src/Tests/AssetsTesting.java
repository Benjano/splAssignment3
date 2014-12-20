package Tests;

import static org.junit.Assert.assertEquals;
import implement.AssetContentImpl;
import implement.AssetImpl;
import implement.AssetsImpl;
import implement.Location;
import interfaces.Asset;
import interfaces.AssetContent;
import interfaces.Assets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.AssetStatus;

public class AssetsTesting {
	private Assets assets;
	private Asset asset1, asset2, asset3, asset4;

	@Before
	public void setUp() throws Exception {
		assets = new AssetsImpl();

		asset1 = new AssetImpl("Aviv's House", "Appartment", new Location(10,
				15), AssetStatus.Available, 100, 2);
		asset2 = new AssetImpl("Nir's House", "Appartment",
				new Location(10, 15), AssetStatus.Available, 100, 1);
		asset3 = new AssetImpl("Yarden's House", "Loft", new Location(10, 15),
				AssetStatus.Available, 100, 3);
		asset4 = new AssetImpl("Gal's House", "Loft", new Location(10, 15),
				AssetStatus.Available, 100, 6);

		AssetContent content1 = new AssetContentImpl("Table", 1.5f);
		AssetContent content2 = new AssetContentImpl("Chairs", 1.5f);
		AssetContent content3 = new AssetContentImpl("Bed", 1.5f);

		asset1.addAssetContent(content1);
		asset1.addAssetContent(content2);
		asset1.addAssetContent(content3);

		assets.addAsset(asset1);
		assets.addAsset(asset2);
		assets.addAsset(asset3);
		assets.addAsset(asset4);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDamagedAssets() {

		assertEquals("Should be no damaged assets", 0, assets
				.getDamagedAssets().size());

		AssetContent content1 = new AssetContentImpl("Table", 1.5f);
		AssetContent content2 = new AssetContentImpl("Chairs", 1.5f);

		asset2.addAssetContent(content1);
		asset2.addAssetContent(content2);

		assertEquals("Should be 1 damaged assets", 1, assets.getDamagedAssets()
				.size());
	}

	@Test
	public void testFindByTypeAndSize() {

		assertEquals("Should find 0", 0,
				assets.findAssetByTypeAndSize("Hut", 2).size());
		assertEquals("Should find 1", 1,
				assets.findAssetByTypeAndSize("Appartment", 2).size());
		assertEquals("Should find 2 ", 2,
				assets.findAssetByTypeAndSize("Loft", 3).size());
	}
}
