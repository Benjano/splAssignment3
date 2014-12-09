package Tests;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Main.I_RepairMaterial;
import Main.I_RepairTool;
import Main.I_Warehouse;
import Main.RepairMaterial;
import Main.RepairTool;
import Main.Warehouse;

public class TestWarehouse {

	I_Warehouse warehouse;

	@Before
	public void setUp() throws Exception {
		warehouse = new Warehouse(null, null);
	}
	
	@After
	public void tearDown() throws Exception {
		setUp();
	}
	
	@Test
	public void testTakeRepairTool() {
		I_RepairTool repairTool = warehouse.takeRepairTool("Hummer", 5);
		assertEquals(repairTool, new RepairTool("Hummer", 5));
	}

	@Test
	public void testTakeRepairMaterial() {
		I_RepairMaterial repairMaterial = warehouse.takeRepairMaterial("Nail",
				10);
		assertEquals(repairMaterial, new RepairMaterial("Nail", 10));
	}

	@Test
	public void putToolBack() {
		int sumToolsBegin = warehouse.countToolInWarehouse("Hummer");
		I_RepairTool repairTool = new RepairTool("Hummer", 2);
		warehouse.putToolBack(repairTool);
		int sumToolsAfter = warehouse.countToolInWarehouse("Hummer");
		assertEquals(sumToolsBegin, sumToolsAfter);
	}

}
