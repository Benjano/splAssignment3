package Tests;

import static org.junit.Assert.assertEquals;
import implement.AssetContentImpl;
import implement.AssetImpl;
import implement.Location;
import interfaces.Asset;
import interfaces.AssetContent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.AssetStatus;

public class AssetTesting {
	private Asset asset;

	@Before
	public void setUp() throws Exception {
		asset = new AssetImpl("Aviv House", "appartment", new Location(10, 15),
				AssetStatus.Available, 100, 2);
	}

	@After
	public void tearDown() throws Exception {
		setUp();
	}

	@Test
	public void testCalculateRepairTime() {
		AssetContentImpl content1 = new AssetContentImpl("Table", 100, 1.5f);
		AssetContentImpl content2 = new AssetContentImpl("Chairs", 90, 1.5f);
		AssetContentImpl content3 = new AssetContentImpl("Bed", 100, 1.5f);

		asset.addAssetContent(content1);
		asset.addAssetContent(content2);
		asset.addAssetContent(content3);
		System.out.println(asset);

		assertEquals("Length of the content is not ok",
				asset.getAllAssetContent().length, 3);
	}
}
