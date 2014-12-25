package Tests;

import static org.junit.Assert.assertEquals;
import implement.RepairToolInformationImpl;
import interfaces.RepairToolInformation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepairToolInformationTesting {
	private RepairToolInformation repairToolInformation;
	
	@Before
	public void setUp() throws Exception {
		repairToolInformation = new RepairToolInformationImpl("Hammer", 5);	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetName() {
		assertEquals("blabla","Hammer", repairToolInformation.getName() );
	}

	@Test
	public void testGetQuantity() {
		assertEquals("blabla",5, repairToolInformation.getQuantity() );
	}
}
