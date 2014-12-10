package implement;

import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import java.util.concurrent.atomic.AtomicReferenceArray;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;

public class WarehouseImpl implements Warehouse {

	private AtomicReferenceArray<RepairTool> fRepairTools;
	private AtomicReferenceArray<RepairMaterial> fRepairMaterials;

	public WarehouseImpl(AtomicReferenceArray<RepairTool> repairTools,
			AtomicReferenceArray<RepairMaterial> repairMaterials) {
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
	public void putToolBack(RepairTool repairTool) {

	}

	@Override
	public int countToolInWarehouse(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
