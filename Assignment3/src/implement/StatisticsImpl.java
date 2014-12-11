package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interfaces.RentalRequest;
import interfaces.RepairMaterial;
import interfaces.RepairTool;
import interfaces.Statistics;

public class StatisticsImpl implements Statistics {
	
	private double fMoneyGained;
	private List<RentalRequest> fRentalRequests;
	private List<RepairTool> fRepairTools;
	private List<RepairMaterial> fRepairMaterials;
	
	public StatisticsImpl(){
		fMoneyGained= 0;
		fRentalRequests = Collections.synchronizedList(new ArrayList<RentalRequest>());
		fRepairTools = Collections.synchronizedList(new ArrayList<RepairTool>());
		fRepairMaterials = Collections.synchronizedList(new ArrayList<RepairMaterial>());
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Money Gained: ");
		builder.append(fMoneyGained);
		builder.append("\n");
		builder.append("Rental Request: ");
		for (RentalRequest rentalRequest : fRentalRequests) {
			builder.append("\n").append(rentalRequest).append("\n");
		}
		builder.append("Repair Tool: ");
		for (RepairTool repairTool : fRepairTools) {
			builder.append("\n").append(repairTool).append("\n");
		}
		builder.append("Repair Material: ");
		for (RepairMaterial repairMaterial : fRepairMaterials) {
			builder.append("\n").append(repairMaterial).append("\n");
		}
		
		return builder.toString();
	}
	
}
