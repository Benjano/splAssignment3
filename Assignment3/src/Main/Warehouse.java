package Main;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class Warehouse implements I_Warehouse {

	private AtomicReferenceArray<I_RepairTool> fRepairTools;
	private AtomicReferenceArray<I_RepairMaterial> fRepairMaterial;

	@Override
	public I_RepairTool takeRepairTool(String name, int quantity) {
		return null;
	}

	@Override
	public I_RepairMaterial takeRepairMaterial(String name, int quantity) {
		return null;
	}

	@Override
	public void putToolBack(I_RepairMaterial tool) {

	}

}
