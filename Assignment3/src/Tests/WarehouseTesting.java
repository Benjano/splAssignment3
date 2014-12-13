package Tests;

import static org.junit.Assert.*;
import implement.RepairMaterialImpl;
import implement.RepairToolImpl;
import implement.WarehouseImpl;
import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WarehouseTesting {

	Warehouse warehouse;

	@Before
	public void setUp() throws Exception {
		warehouse = new WarehouseImpl();
		RepairTool repairTool = new RepairToolImpl("Hummer", 5);
		RepairMaterial repairMaterial = new RepairMaterialImpl("Nail", 10);
		warehouse.addMaterial(repairMaterial);
		warehouse.addTool(repairTool);
	}

	@After
	public void tearDown() throws Exception {
		setUp();
	}

	
	@Test
	public void testTakeRepairTool() {
		RepairTool repairTool = warehouse.takeRepairTool("Hummer", 3);
		assertEquals("Wrong RepairTool quantity", 2,
				warehouse.countRepairToolInWarehouse("Hummer"));
		warehouse.addTool(repairTool);
		assertEquals("Wrong RepairTool quantity", 5,
				warehouse.countRepairToolInWarehouse("Hummer"));
	}

	@Test
	public void testTakeRepairMaterial() {
		RepairMaterial repairMaterial = warehouse.takeRepairMaterial("Nail", 3);
		assertEquals("Wrong RepairMaterial quantity", 7,
				warehouse.countRepairMaterialInWarehouse("Nail"));
		repairMaterial = warehouse.takeRepairMaterial("Nail", 10);
		assertEquals("Wrong RepairMaterial quantity", null, repairMaterial);
	}

	@Test
	public void testAddTool() {
		RepairTool addRepairTool = new RepairToolImpl("Screwdriver", 3);
		warehouse.addTool(addRepairTool);
		assertEquals("Wrong RepairTool quantity", 3,
				warehouse.countRepairToolInWarehouse("Screwdriver"));
	}

	@Test
	public void testAddMaterial() {
		RepairMaterial addRepairMaterial = new RepairMaterialImpl("Superglue",
				4);
		warehouse.addMaterial(addRepairMaterial);
		assertEquals("Wrong RepairMaterial quantity", 4,
				warehouse.countRepairMaterialInWarehouse("Superglue"));
	}

}
