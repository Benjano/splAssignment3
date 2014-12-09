package Main;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class Warehouse implements I_Warehouse {

	private AtomicReferenceArray<I_RepairTool> fRepairTools;
	private AtomicReferenceArray<I_RepairMaterial> fRepairMaterials;

	public Warehouse(AtomicReferenceArray<I_RepairTool> repairTools,
			AtomicReferenceArray<I_RepairMaterial> repairMaterials) {
		fRepairTools = repairTools;
		fRepairMaterials = repairMaterials;
	}

	@Override
	public RepairTool takeRepairTool(String name, int quantity) {
		return null;
	}

	@Override
	public RepairMaterial takeRepairMaterial(String name, int quantity) {
		return null;
	}

	@Override
	public void putToolBack(I_RepairTool repairTool) {

	}

	@Override
	public int countToolInWarehouse(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
