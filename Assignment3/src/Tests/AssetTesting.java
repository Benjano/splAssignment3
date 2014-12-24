package Tests;

import static org.junit.Assert.assertEquals;

import implement.AssetContentImpl;
import implement.Location;
import interfaces.AssetContent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.AssetStatus;

public class AssetTesting {
	private AssetTest asset;
	private final double DELTA = 0.001;

	private AssetContent content1, content2, content3;

	@Before
	public void setUp() throws Exception {
		asset = new AssetTest("Aviv House", "appartment", new Location(10, 15),
				AssetStatus.Available, 100, 2);
		content1 = new AssetContentImpl("Table", 1.5f);
		content2 = new AssetContentImpl("Chairs", 1.5f);
		content3 = new AssetContentImpl("Bed", 1.5f);

		asset.addAssetContent(content1);
		asset.addAssetContent(content2);
		asset.addAssetContent(content3);
	}

	@After
	public void tearDown() throws Exception {
		setUp();
	}

	@Test
	public void testCalculateRepairTime() {

		content2.damageAssetContent(20);

		assertEquals("Length of the content is not ok",
				asset.getNumberOfAssetContent(), 3);
	}

	@Test
	public void testGetName() {
		assertEquals("The name is wrong", "Aviv House", asset.getName());
	}

	@Test
	public void testGetLocation() {
		Location location = new Location(10, 15);
		assertEquals("Location is wrong", location, asset.getLocation());
	}

	@Test
	public void testGetStatus() {
		assertEquals("The status is wrong", AssetStatus.Available,
				asset.getStatus());
	}

	@Test
	public void testGetType() {
		assertEquals("The type is wrong", "appartment", asset.getType());
	}

	@Test
	public void testSetStatus() {
		asset.setStatus(AssetStatus.Unavailable);
		assertEquals("The status is wrong", AssetStatus.Unavailable,
				asset.getStatus());
	}

	@Test
	public void testGetCostPerNight() {
		assertEquals("The cost per night is wrong", 100,
				asset.getCostPerNight(), DELTA);
	}

	@Test
	public void getSize() {
		assertEquals("The size is wrong", 2, asset.getSize(), DELTA);
	}

	@Test
	public void isDamaged() {
		assertEquals("The asset should be ok", false, asset.isDamaged());
		asset.damageAssetContent(80);
		assertEquals("The asset should be damaged", true, asset.isDamaged());
	}

	@Test
	public void addAssetContent() {
		assertEquals("The cost per night is wrong", 100,
				asset.getCostPerNight(), DELTA);
	}

	@Test
	public void damageAssetContent() {

		assertEquals("The asset should be ok", false, asset.isDamaged());
		asset.damageAssetContent(-20);
		assertEquals("The asset should be ok", false, asset.isDamaged());

		asset.damageAssetContent(0);
		assertEquals("The asset should be ok", false, asset.isDamaged());

		asset.damageAssetContent(30);
		assertEquals("The asset should be ok", false, asset.isDamaged());

		asset.damageAssetContent(40);
		assertEquals("The asset should be damaged", true, asset.isDamaged());

	}

	@Test
	public void getDamagedAssetContent() {

		assertEquals("No damaged content ", 0, asset.getDamagedAssetContent()
				.size());
		content1.damageAssetContent(80);
		assertEquals("No damaged content ", 1, asset.getDamagedAssetContent()
				.size());
		content2.damageAssetContent(90);
		assertEquals("No damaged content ", 2, asset.getDamagedAssetContent()
				.size());
		content3.damageAssetContent(100);
		assertEquals("No damaged content ", 3, asset.getDamagedAssetContent()
				.size());
	}

}
