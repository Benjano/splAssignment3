package Tests;


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
		assertEquals("blabla","Nail", repairMaterial.getName() );
	}

	@Test
	public void testGetQuantity() {
		assertEquals("blabla",5, repairMaterial.getQuantity() );
	}
	
	@Test
	public void testAcquire() {
		repairMaterial.Acquire(2);
		assertEquals("blabla",3, repairMaterial.getQuantity() );
	}
	
	@Test
	public void testRelease() {
		repairMaterial.Acquire(2);
		repairMaterial.Release(1);
		assertEquals("blabla",4, repairMaterial.getQuantity() );
	}
	
}
