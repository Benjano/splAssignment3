package implement;


import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import java.util.concurrent.ConcurrentHashMap;


public class WarehouseImpl implements Warehouse {
	private ConcurrentHashMap<String, RepairToolImpl> fRepairTools;
	private ConcurrentHashMap<String, RepairMaterialImpl> fRepairMaterials;

	public WarehouseImpl(ConcurrentHashMap<String, RepairToolImpl> repairTools,
			ConcurrentHashMap<String, RepairMaterialImpl> repairMaterials) {
		fRepairTools = repairTools;
		fRepairMaterials = repairMaterials;
	}

	
	@Override
	public RepairMaterial takeRepairMaterial(String name, int quantity) {
		
		return null;
	}
	
	
	@Override
	public RepairTool takeRepairTool(String name, int quantity) {
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
