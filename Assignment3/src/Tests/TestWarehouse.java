package Tests;

import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.Test;

import Main.I_RepairMaterial;
import Main.I_RepairTool;
import Main.I_Warehouse;
import Main.Warehouse;

public class TestWarehouse {

	Warehouse warehouse;
	
	// איתחול 2 טסטים  בדיקת נכונות

	@Test
	public void testTakeRepairTool() {
		warehouse.takeRepairTool("Hummer", 5);
	}


}
