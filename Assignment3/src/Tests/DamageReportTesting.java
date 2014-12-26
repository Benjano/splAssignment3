package Tests;

import static org.junit.Assert.assertEquals;
import implement.AssetImpl;
import implement.DamageReportImpl;
import implement.Location;
import interfaces.Asset;
import interfaces.DamageReport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DamageReportTesting {
	
	private Asset asset;
	private DamageReport damgeReport;
	private final double DELTA = 0.001;
	
	@Before
	public void setUp() throws Exception {
		asset = new AssetImpl("Aviv's House", "Appartment", new Location(10,15), 100, 2);
		damgeReport = new DamageReportImpl(asset, 3.2);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetAsset() {
		assertEquals("blabla",  "Aviv's House", damgeReport.getAsset().getName() );
	}
	
	@Test
	public void testGetDamagePercentage() {
		assertEquals("blabla",  3.2, damgeReport.getDamagePercentage(), DELTA );
	}
	
	
}
