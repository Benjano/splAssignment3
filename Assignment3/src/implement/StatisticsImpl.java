package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import interfaces.RentalRequest;
import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Statistics;

public class StatisticsImpl implements Statistics {

	private double fMoneyGained;
	private List<RentalRequest> fRentalRequests;
	private Map<String, Integer> fRepairTools;
	private Map<String, Integer> fRepairMaterials;

	public StatisticsImpl() {
		fMoneyGained = 0;
		fRentalRequests = Collections
				.synchronizedList(new ArrayList<RentalRequest>());
		fRepairTools = new HashMap<String, Integer>();
		fRepairMaterials = new HashMap<String, Integer>();
	}

	@Override
	public synchronized void addIncome(double fCostPerNight) {
		fMoneyGained += fCostPerNight;
	}

	@Override
	public synchronized void addRentalRequest(RentalRequest rentalRequest) {
		fRentalRequests.add(rentalRequest);
	}

	@Override
	public void addToolInProcess(RepairTool repairTool) {

		if (fRepairTools.containsKey(repairTool.getName())) {
			fRepairTools.put(
					repairTool.getName(),
					fRepairTools.get(repairTool.getQuantity())
							+ repairTool.getQuantity());
		} else {
			fRepairTools.put(repairTool.getName(), repairTool.getQuantity());
		}
	}

	@Override
	public void releaseTool(RepairTool repairTool) {
		if (fRepairTools.containsKey(repairTool.getName())) {
			fRepairTools.put(repairTool.getName(), fRepairTools.get(repairTool.getQuantity())
				- repairTool.getQuantity());
		}
	}

	@Override
	public void consumeMaterial(RepairMaterial repairMaterial) {
		if (fRepairMaterials.containsKey(repairMaterial.getName())) {
			fRepairMaterials.put(
					repairMaterial.getName(),
					fRepairMaterials.get(repairMaterial.getQuantity())
							+ repairMaterial.getQuantity());
		} else {
			fRepairMaterials.put(repairMaterial.getName(), repairMaterial.getQuantity());
		}

	}

}
