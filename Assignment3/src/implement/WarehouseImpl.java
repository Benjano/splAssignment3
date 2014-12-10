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
		RepairMaterialImpl tempRepairMaterial=  fRepairMaterials.get(name);
		if (tempRepairMaterial.getQuantity()>= quantity){
			tempRepairMaterial.ReduceMaterial(quantity);
			fRepairMaterials.put(name, tempRepairMaterial);
			return new RepairMaterialImpl(name,quantity);
		}else{
			return null;
		}
	}


	@Override
	public RepairTool takeRepairTool(String name, int quantity) {
		RepairToolImpl tempRepairTool=  fRepairTools.get(name);
		if (tempRepairTool.getQuantity()>= quantity){
			tempRepairTool.ReduceTool(quantity);
			fRepairTools.put(name, tempRepairTool);
			return new RepairToolImpl(name,quantity);
		}else{
			return null;
		}
	}


	@Override
	public void putToolBack(RepairTool repairTool) {
		RepairToolImpl tempRepairTool=  fRepairTools.get(repairTool.getName());
		tempRepairTool.IncreaseTool(repairTool.getQuantity());
		fRepairTools.put(repairTool.getName(), tempRepairTool);

	}

	@Override
	public int countToolInWarehouse(String name) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void addTool(String name, int quantity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addMaterial(String name, int quantity) {
		// TODO Auto-generated method stub
		
	}

}
