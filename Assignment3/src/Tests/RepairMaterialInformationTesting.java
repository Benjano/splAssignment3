package Tests;

import static org.junit.Assert.assertEquals;
import implement.RepairMaterialInformationImpl;
import interfaces.RepairMaterialInformation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepairMaterialInformationTesting {

	private RepairMaterialInformation RepairMaterialInformation;

	@Before
	public void setUp() throws Exception {
		RepairMaterialInformation = new RepairMaterialInformationImpl("Nail", 5);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		assertEquals("The name is wrong", "Nail",
				RepairMaterialInformation.getName());
	}

	@Test
	public void testGetQuantity() {
		assertEquals("The quantity is wrong", 5,
				RepairMaterialInformation.getQuantity());
	}

}
