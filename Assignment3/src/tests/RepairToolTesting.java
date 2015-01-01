package tests;

import static org.junit.Assert.assertEquals;
import implement.RepairToolImpl;
import interfaces.RepairTool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepairToolTesting {
	
	private RepairTool repairTool;
	
	@Before
	public void setUp() throws Exception {
		repairTool = new RepairToolImpl("Hammer", 5);	
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetName() {
		assertEquals("The name is wrong","Hammer", repairTool.getName() );
	}

	@Test
	public void testGetQuantity() {
		assertEquals("The queantity is wrong",5, repairTool.getQuantity() );
	}
	
	@Test
	public void testAcquire() {
		repairTool.Acquire(2);
		assertEquals("Did not acuire the right amount",3, repairTool.getQuantity() );
	}
	
	@Test
	public void testRelease() {
		repairTool.Acquire(2);
		repairTool.Release(1);
		assertEquals("Did not release the right amount",4, repairTool.getQuantity() );
	}
}
