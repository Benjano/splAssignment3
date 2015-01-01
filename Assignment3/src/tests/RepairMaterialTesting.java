package tests;

import static org.junit.Assert.*;
import implement.RepairMaterialImpl;
import interfaces.RepairMaterial;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepairMaterialTesting {

	private RepairMaterial repairMaterial;

	@Before
	public void setUp() throws Exception {
		repairMaterial = new RepairMaterialImpl("Nail", 5);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		assertEquals("The name is wrong", "Nail", repairMaterial.getName());
	}

	@Test
	public void testGetQuantity() {
		assertEquals("The queantity is wrong", 5, repairMaterial.getQuantity());
	}

	@Test
	public void testAcquire() {
		repairMaterial.Acquire(2);
		assertEquals("Did not acuire the right amount", 3,
				repairMaterial.getQuantity());
	}

	@Test
	public void testRelease() {
		repairMaterial.Acquire(2);
		repairMaterial.Release(1);
		assertEquals("Did not release the right amount", 4,
				repairMaterial.getQuantity());
	}

}
