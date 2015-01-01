package tests;

import static org.junit.Assert.assertEquals;
import implement.AssetContentImpl;
import interfaces.AssetContent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AssetContentTesting {
	private AssetContent assetContent;
	private final double DELTA = 0.001;

	private int fRepairMultiplier = 2;
	private int fHealth = 100;

	@Before
	public void setUp() throws Exception {
		assetContent = new AssetContentImpl("Table", fRepairMultiplier);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateRepairTime() {
		assertEquals("The repair time is wrong",
				assetContent.calculateRepairTime(), (100 - fHealth)
						* fRepairMultiplier, DELTA);

		assetContent.damageAssetContent(0.3);
		int newHealth = 70;
		assertEquals("The repair time is wrong",
				assetContent.calculateRepairTime(), ((100d - newHealth)/100d)
						* fRepairMultiplier, DELTA);

	}

	@Test
	public void testDamageAssetContent() {
		assetContent.damageAssetContent(40);
		int newHealth = 60;
		assertEquals("The health is wrong", newHealth,
				assetContent.getHealth(), DELTA);

		assetContent.damageAssetContent(-0.4);
		assertEquals("The health is wrong", newHealth,
				assetContent.getHealth(), DELTA);

		assetContent.damageAssetContent(100);
		assertEquals("The health is wrong", 0, assetContent.getHealth(), DELTA);
	}

	@Test
	public void testFixAsset() {
		assetContent.damageAssetContent(0.3);
		assetContent.fixAssetContent();

		assertEquals("The assetcontent should be fixed",
				assetContent.getHealth(), 100, DELTA);
	}

	@Test
	public void testGetName() {
		assertEquals(assetContent.getName(), "Table");
	}
}
