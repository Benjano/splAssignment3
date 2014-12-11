package Tests;

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

		int newHealth = 70;
		assetContent.setHealth(newHealth);
		assertEquals("The repair time is wrong",
				assetContent.calculateRepairTime(), (100 - newHealth)
						* fRepairMultiplier, DELTA);

	}
}
