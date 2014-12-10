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
	public void addTool(RepairTool repairTool) {
		String repairToolName = repairTool.getName();
		if (fRepairTools.containsKey(repairToolName)) {
			RepairTool tempRepairTool = fRepairTools.get(repairToolName);
			tempRepairTool.IncreaseTool(repairTool.getQuantity());
		} else {
			fRepairTools.put(repairToolName, repairTool);
		}
	}

	@Override
	public void addMaterial(RepairMaterial repairMaterial) {
		String repairToolName = repairMaterial.getName();
		if (fRepairMaterials.containsKey(repairToolName)) {
			RepairMaterial tempRepairMaterial = fRepairMaterials
					.get(repairToolName);
			tempRepairMaterial.IncreaseMaterial(repairMaterial.getQuantity());
		} else {
			fRepairMaterials.put(repairToolName, repairMaterial);
		}

	}

	@Override
	public int countRepairToolInWarehouse(String name) {

		if (fRepairTools.containsKey(name)) {
			return (fRepairTools.get(name)).getQuantity();
		} else {
			return 0;
		}
	}

	@Override
	public int countRepairMaterialInWarehouse(String name) {
		if (fRepairMaterials.containsKey(name)) {
			return (fRepairMaterials.get(name)).getQuantity();
		} else {
			return 0;
		}
	}

}
