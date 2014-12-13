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
	public synchronized RepairMaterial takeRepairMaterial(String name,
			int quantity) {
		if (isRepairMaterialEnough(name, quantity)) {
			removeRepairMaterial(name, quantity);
			return new RepairMaterialImpl(name, quantity);
		} else {
			return null;
		}
	}

	@Override
	public boolean isRepairMaterialEnough(String name, int quantity) {
		RepairMaterial repairMaterial = fRepairMaterials.get(name);
		if (repairMaterial == null)
			return false;
		return repairMaterial.getQuantity() >= quantity;
	}

	private void removeRepairMaterial(String name, int quantity) {
		fRepairMaterials.get(name).ReduceMaterial(quantity);
	}

	@Override
	public synchronized RepairTool takeRepairTool(String name, int quantity) {
		if (isRepairToolEnough(name, quantity)) {
			removeRepairTool(name, quantity);
			return new RepairToolImpl(name, quantity);
		} else {
			return null;
		}
	}

	@Override
	public boolean isRepairToolEnough(String name, int quantity) {
		RepairTool repairTool = fRepairTools.get(name);
		if (repairTool == null)
			return false;
		return repairTool.getQuantity() >= quantity;
	}

	private void removeRepairTool(String name, int quantity) {
		fRepairTools.get(name).ReduceTool(quantity);
	}

	@Override
	public synchronized void addTool(RepairTool repairTool) {
		String repairToolName = repairTool.getName();
		if (fRepairTools.containsKey(repairToolName)) {
			RepairTool tempRepairTool = fRepairTools.get(repairToolName);
			tempRepairTool.IncreaseTool(repairTool.getQuantity());
		} else {
			fRepairTools.put(repairToolName, repairTool);
		}
	}

	@Override
	public synchronized void addMaterial(RepairMaterial repairMaterial) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tools In Warehouse: ");

		for (Map.Entry<String, RepairTool> repairTool : fRepairTools.entrySet()) {
			builder.append("\n").append(repairTool.getValue());
		}

		builder.append("Materials In Warehouse: ");

		for (Map.Entry<String, RepairMaterial> materialTool : fRepairMaterials
				.entrySet()) {
			builder.append("\n").append(materialTool.getValue());
		}
		return builder.toString();

	}

}
