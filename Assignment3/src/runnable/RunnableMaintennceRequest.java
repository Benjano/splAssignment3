package runnable;

import interfaces.Asset;
import interfaces.AssetContent;
import interfaces.RepairMaterial;
import interfaces.RepairMaterialInformation;
import interfaces.RepairTool;
import interfaces.RepairToolInformation;
import interfaces.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import consts.AssetStatus;

public class RunnableMaintennceRequest implements Runnable {

	Map<String, List<RepairToolInformation>> fRepairToolsInformation;
	Map<String, List<RepairMaterialInformation>> fRepairMaterialsInformation;
	Asset fAsset;
	Warehouse fWarehouse;

	/**
	 * @param fRepairToolsInformation
	 * @param fRepairMaterialsInformation
	 * @param fAsset
	 * @param fWarehouse
	 */
	public RunnableMaintennceRequest(
			Map<String, List<RepairToolInformation>> repairToolsInformation,
			Map<String, List<RepairMaterialInformation>> repairMaterialsInformation,
			Asset asset, Warehouse warehouse) {
		this.fRepairToolsInformation = repairToolsInformation;
		this.fRepairMaterialsInformation = repairMaterialsInformation;
		this.fAsset = asset;
		this.fWarehouse = warehouse;
	}

	@Override
	public void run() {
		fAsset.setStatus(AssetStatus.Unavailable);
		List<AssetContent> damagedContent = fAsset.getDamagedAssetContent();

		Map<String, Integer> neededTools = getNeededToolsInformation(damagedContent);
		Vector<RepairTool> repairTools = takeToolsFromWarehouse(neededTools);
		Map<String, Integer> neededMaterials = getNeededMaterialsInformation(damagedContent);
		takeMaterialsFromWarehouse(neededMaterials);

		for (AssetContent assetContent : damagedContent) {
			try {
				Thread.sleep((long) assetContent.calculateRepairTime() * 1000);
				assetContent.fixAssetContent();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// return the tools to the warehouse
		for (RepairTool repairTool : repairTools) {
			fWarehouse.addTool(repairTool);
		}

		fAsset.setStatus(AssetStatus.Available);
	}

	private Vector<RepairTool> takeToolsFromWarehouse(
			Map<String, Integer> neededTools) {
		Vector<RepairTool> repairTools = new Vector<RepairTool>();
		for (Map.Entry<String, Integer> neededTool : neededTools.entrySet()) {
			RepairTool repairTool = fWarehouse.takeRepairTool(
					neededTool.getKey(), neededTool.getValue());
			while (repairTool == null) {
				try {
					wait();
					repairTool = fWarehouse.takeRepairTool(neededTool.getKey(),
							neededTool.getValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			repairTools.add(repairTool);
		}
		return repairTools;
	}

	private Vector<RepairMaterial> takeMaterialsFromWarehouse(
			Map<String, Integer> neededMaterials) {
		Vector<RepairMaterial> repairMaterials = new Vector<RepairMaterial>();
		for (Map.Entry<String, Integer> neededTool : neededMaterials.entrySet()) {
			RepairMaterial repairMaterial = fWarehouse.takeRepairMaterial(
					neededTool.getKey(), neededTool.getValue());
			while (repairMaterial == null) {
				try {
					wait();
					repairMaterial = fWarehouse.takeRepairMaterial(
							neededTool.getKey(), neededTool.getValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			repairMaterials.add(repairMaterial);
		}
		return repairMaterials;
	}

	private Map<String, Integer> getNeededToolsInformation(
			List<AssetContent> damagedContent) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for (AssetContent assetContent : damagedContent) {
			List<RepairToolInformation> repairToolsInformation = fRepairToolsInformation
					.get(assetContent.getName());
			for (RepairToolInformation repairToolInformation : repairToolsInformation) {
				if (result.containsKey(repairToolInformation.getName())) {
					int currentQuantity = result.get(repairToolInformation
							.getName());
					if (currentQuantity < repairToolInformation.getQuantity()) {
						result.put(repairToolInformation.getName(),
								repairToolInformation.getQuantity());
					}
				} else {
					result.put(repairToolInformation.getName(),
							repairToolInformation.getQuantity());
				}
			}
		}
		return result;
	}

	private Map<String, Integer> getNeededMaterialsInformation(
			List<AssetContent> damagedContent) {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for (AssetContent assetContent : damagedContent) {
			List<RepairMaterialInformation> repairMaterialInformations = fRepairMaterialsInformation
					.get(assetContent.getName());
			for (RepairMaterialInformation repairMaterialInformation : repairMaterialInformations) {
				if (result.containsKey(repairMaterialInformation.getName())) {
					int currentQuantity = result.get(repairMaterialInformation
							.getName());
					result.put(repairMaterialInformation.getName(),
							repairMaterialInformation.getQuantity()
									+ currentQuantity);
				} else {
					result.put(repairMaterialInformation.getName(),
							repairMaterialInformation.getQuantity());
				}
			}
		}
		return result;
	}

}
