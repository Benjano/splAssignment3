package runnable;

import interfaces.Asset;
import interfaces.AssetContent;
import interfaces.RepairMaterial;
import interfaces.RepairMaterialInformation;
import interfaces.RepairTool;
import interfaces.RepairToolInformation;
import interfaces.Statistics;
import interfaces.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import consts.AssetStatus;
import consts.Timing;

public class RunnableMaintenaceRequest implements Runnable {

	private Map<String, List<RepairToolInformation>> fRepairToolsInformation;
	private Map<String, List<RepairMaterialInformation>> fRepairMaterialsInformation;
	private Asset fAsset;
	private Warehouse fWarehouse;
	private Statistics fStatistics;
	private Logger fLogger;
	private Messenger fMessengerMentenance;

	/**
	 * @param fRepairToolsInformation
	 * @param fRepairMaterialsInformation
	 * @param fAsset
	 * @param fWarehouse
	 */
	public RunnableMaintenaceRequest(
			Map<String, List<RepairToolInformation>> repairToolsInformation,
			Map<String, List<RepairMaterialInformation>> repairMaterialsInformation,
			Asset asset, Warehouse warehouse, Statistics statistics,
			Messenger messengerMentenance) {
		this.fRepairToolsInformation = repairToolsInformation;
		this.fRepairMaterialsInformation = repairMaterialsInformation;
		this.fAsset = asset;
		this.fWarehouse = warehouse;
		this.fStatistics = statistics;
		this.fMessengerMentenance = messengerMentenance;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public void run() {
		fLogger.log(
				Level.FINE,
				new StringBuilder()
						.append("New maintenance insinuate for asset ")
						.append(fAsset.getName()).toString());
		fAsset.setStatus(AssetStatus.Unavailable);
		List<AssetContent> damagedContent = fAsset.getDamagedAssetContent();

		Map<String, Integer> neededTools = getNeededToolsInformation(damagedContent);
		Vector<RepairTool> repairTools = takeToolsFromWarehouse(neededTools);

		Map<String, Integer> neededMaterials = getNeededMaterialsInformation(damagedContent);
		takeMaterialsFromWarehouse(neededMaterials);

		fLogger.log(
				Level.FINE,
				new StringBuilder().append("Maintenance for asset ")
						.append(fAsset.getName()).append(" started").toString());

		for (AssetContent assetContent : damagedContent) {
			try {
				Thread.sleep((long) assetContent.calculateRepairTime()
						* Timing.SECOND);
				assetContent.fixAssetContent();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		returnTools(repairTools);
		fLogger.log(
				Level.FINE,
				new StringBuilder().append("Maintenance for asset ")
						.append(fAsset.getName())
						.append(" is done and returning tools").toString());

		synchronized (this) {
			notifyAll();
		}

		fAsset.setStatus(AssetStatus.Available);

		synchronized (fMessengerMentenance) {
			fMessengerMentenance.notifyAll();
		}
	}

	// return the tools to the warehouse
	private void returnTools(Vector<RepairTool> repairTools) {
		for (RepairTool repairTool : repairTools) {
			fWarehouse.addTool(repairTool);
			fStatistics.releaseTool(repairTool);
		}
	}

	private Vector<RepairTool> takeToolsFromWarehouse(
			Map<String, Integer> neededTools) {
		Vector<RepairTool> repairTools = new Vector<RepairTool>();
		for (Map.Entry<String, Integer> neededTool : neededTools.entrySet()) {
			RepairTool repairTool = fWarehouse.takeRepairTool(
					neededTool.getKey(), neededTool.getValue());
			if (repairTool == null) {
				try {
					returnTools(repairTools);
					synchronized (this) {
						fLogger.log(
								Level.FINE,
								new StringBuilder()
										.append("Maintenance for asset ")
										.append(fAsset.getName())
										.append(" is waiting for tools")
										.toString());
						wait();
					}
					return takeToolsFromWarehouse(neededTools);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				repairTools.add(repairTool);
				fStatistics.addToolInProcess(repairTool);
			}
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
				repairMaterial = fWarehouse.takeRepairMaterial(
						neededTool.getKey(), neededTool.getValue());
			}
			repairMaterials.add(repairMaterial);
			fStatistics.consumeMaterial(repairMaterial);
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
