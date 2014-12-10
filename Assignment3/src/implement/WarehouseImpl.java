package implement;

import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WarehouseImpl implements Warehouse {
	private Map<String, RepairTool> fRepairTools;
	private Map<String, RepairMaterial> fRepairMaterials;

	public WarehouseImpl() {
		this.fRepairTools = new ConcurrentHashMap<String, RepairTool>();
		this.fRepairMaterials = new ConcurrentHashMap<String, RepairMaterial>();

	}

	@Override
	public RepairMaterial takeRepairMaterial(String name, int quantity) {
		RepairMaterial tempRepairMaterial = fRepairMaterials.get(name);
		if (tempRepairMaterial.getQuantity() >= quantity) {
			tempRepairMaterial.ReduceMaterial(quantity);
			fRepairMaterials.put(name, tempRepairMaterial);
			return new RepairMaterialImpl(name, quantity);
		} else {
			return null;
		}
	}

	@Override
	public RepairTool takeRepairTool(String name, int quantity) {
		RepairTool tempRepairTool = fRepairTools.get(name);
		if (tempRepairTool.getQuantity() >= quantity) {
			tempRepairTool.ReduceTool(quantity);
			fRepairTools.put(name, tempRepairTool);
			return new RepairToolImpl(name, quantity);
		} else {
			return null;
		}
	}

	@Override
	public void putToolBack(RepairTool repairTool) {
		RepairTool tempRepairTool = fRepairTools.get(repairTool.getName());
		tempRepairTool.IncreaseTool(repairTool.getQuantity());
		fRepairTools.put(repairTool.getName(), tempRepairTool);

	}

	@Override
	public void addTool(String name, int quantity) {
		if (fRepairTools.containsKey(name)) {
			RepairTool tempRepairTool = fRepairTools.get(name);
			tempRepairTool.IncreaseTool(quantity);
		} else {
			fRepairTools.put(name, new RepairToolImpl(name, quantity));
		}
	}

	@Override
	public void addMaterial(String name, int quantity) {
		if (fRepairMaterials.containsKey(name)) {
			RepairMaterial tempRepairMaterial = fRepairMaterials.get(name);
			tempRepairMaterial.IncreaseMaterial(quantity);
		} else {
			fRepairMaterials.put(name, new RepairMaterialImpl(name, quantity));
		}

	}

	@Override
	public int countRepairToolInWarehouse(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countRepairMaterialInWarehouse(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

}
