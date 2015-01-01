package tests;

import static org.junit.Assert.*;
import implement.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocationTesting {

	private Location location;
	private final double DELTA = 0.001;

	@Before
	public void setUp() throws Exception {
		location = new Location(2, 3);
	}

	@After
	public void tearDown() throws Exception {
		setUp();
	}

	@Test
	public void testcalculateDistance() {
		Location loc1 = new Location(1, 6);
		Location loc2 = new Location(3, 2);
		Location loc3 = new Location(0, 0);
		assertEquals("loc1 distance is wrong",
				location.calculateDistance(loc1),
				Math.sqrt(Math.pow(2 - 1, 2) + Math.pow(3 - 6, 2)), DELTA);
		assertEquals("loc2 distance is wrong",
				location.calculateDistance(loc2),
				Math.sqrt(Math.pow(2 - 3, 2) + Math.pow(3 - 2, 2)), DELTA);
		assertEquals("loc3 distance is wrong",
				location.calculateDistance(loc3),
				Math.sqrt(Math.pow(2 - 0, 2) + Math.pow(3 - 0, 2)), DELTA);

	}

}
