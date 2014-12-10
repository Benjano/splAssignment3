package Tests;

import static org.junit.Assert.*;
import implement.RepairMaterialImpl;
import implement.RepairToolImpl;
import implement.WarehouseImpl;
import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WarehouseTesting {

	Warehouse warehouse;

	@Before
	public void setUp() throws Exception {
		warehouse = new WarehouseImpl();
	}

	@After
	public void tearDown() throws Exception {
		setUp();
	}

	@Test
	public void testTakeRepairTool() {
		RepairTool repairTool = warehouse.takeRepairTool("Hummer", 5);
		assertEquals(repairTool, new RepairToolImpl("Hummer", 5));
	}

	@Test
	public void testTakeRepairMaterial() {
		RepairMaterial repairMaterial = warehouse
				.takeRepairMaterial("Nail", 10);
		assertEquals(repairMaterial, new RepairMaterialImpl("Nail", 10));
	}

	@Test
	public void testPutToolsBack() {
		int sumToolsBegin = warehouse.countToolInWarehouse("Hummer");
		RepairTool repairTool = new RepairToolImpl("Hummer", 2);
		warehouse.putToolBack(repairTool);
		int sumToolsAfter = warehouse.countToolInWarehouse("Hummer");
		assertEquals(sumToolsBegin, sumToolsAfter);
	}

}
