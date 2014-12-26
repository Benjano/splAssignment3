package Tests;

import static org.junit.Assert.assertEquals;
import implement.ClerkDetailsImpl;
import implement.Location;
import interfaces.ClerkDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClerkDetailsTesting {
	ClerkDetails clerkDetails;
	
	@Before
	public void setUp() throws Exception {
		clerkDetails = new ClerkDetailsImpl("Avi", new Location(1.5, 1.7));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		assertEquals("blabla",  "Avi", clerkDetails.getName() );
	}
	
	@Test
	public void testGetLocatin() {
		Location location2 = new Location(1.5, 1.7);
		assertEquals("blabla",  location2, clerkDetails.getLocation() );
	}
}
