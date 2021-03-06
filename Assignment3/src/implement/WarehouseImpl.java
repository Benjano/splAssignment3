package implement;

import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Warehouse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarehouseImpl implements Warehouse {

	private Map<String, RepairTool> fRepairTools;
	private Map<String, RepairMaterial> fRepairMaterials;
	private Logger fLogger;

	public WarehouseImpl() {
		this.fRepairTools = new ConcurrentHashMap<String, RepairTool>();
		this.fRepairMaterials = new ConcurrentHashMap<String, RepairMaterial>();
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	}

	@Override
	public RepairMaterial takeRepairMaterial(String name, int quantity) {
		if (isRepairMaterialEnough(name, quantity)) {
			aquireRepairMaterial(name, quantity);
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

	private void aquireRepairMaterial(String name, int quantity) {
		fRepairMaterials.get(name).Acquire(quantity);
	}

	@Override
	public synchronized RepairTool takeRepairTool(String name, int quantity) {
		if (fRepairTools.get(name).Acquire(quantity)) {
			return new RepairToolImpl(name, quantity);
		} else {
			fLogger.log(
					Level.WARNING,
					new StringBuilder().append("Could not take tool ")
							.append(name).append(" quantity: ")
							.append(quantity).append(" from warehouse")
							.toString());
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

	@Override
	public void addTool(RepairTool repairTool) {
		String repairToolName = repairTool.getName();
		if (fRepairTools.containsKey(repairToolName)) {
			fRepairTools.get(repairToolName).Release(repairTool.getQuantity());
		} else {
			fRepairTools.put(repairToolName, repairTool);
		}
	}

	@Override
	public synchronized void addMaterial(RepairMaterial repairMaterial) {
		String repairToolName = repairMaterial.getName();
		if (fRepairMaterials.containsKey(repairToolName)) {
			fRepairMaterials.get(repairToolName).Release(
					repairMaterial.getQuantity());
			;
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
