package implement;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import interfaces.Asset;
import interfaces.Assets;
import interfaces.ClerkDetails;
import interfaces.CustomerGroupDetails;
import interfaces.Managment;
import interfaces.RepairMaterial;
import interfaces.RepairMaterialInformation;
import interfaces.RepairTool;
import interfaces.RepairToolInformation;
import interfaces.Warehouse;

public class ManagmentImpl implements Managment {

	List<ClerkDetails> fClerksDetails;
	List<CustomerGroupDetails> fCustomerGroupDetails;
	Assets fAssets;
	Warehouse fWarehouse;
	Map<String, List<RepairToolInformation>> fRepairToolInformations;
	Map<String, List<RepairMaterialInformation>> fRepairMaterialInformations;

	public ManagmentImpl() {
		this.fClerksDetails = new Vector<ClerkDetails>();
		this.fCustomerGroupDetails = new Vector<CustomerGroupDetails>();
		this.fAssets = new AssetsImpl();
		this.fWarehouse = new WarehouseImpl();
		this.fRepairToolInformations = new ConcurrentHashMap<String, List<RepairToolInformation>>();
		this.fRepairMaterialInformations = new ConcurrentHashMap<String, List<RepairMaterialInformation>>();
	}

	@Override
	public void addClerk(ClerkDetails clerkDetails) {
		fClerksDetails.add(clerkDetails);
	}

	@Override
	public void addCustomerGroup(CustomerGroupDetails customerGroupDetails) {
		fCustomerGroupDetails.add(customerGroupDetails);
	}

	@Override
	public void addItemRepairTool(RepairTool repairTool) {
		fWarehouse.addTool(repairTool);
	}

	@Override
	public void addItemRepairMaterial(RepairMaterial repairMaterial) {
		fWarehouse.addMaterial(repairMaterial);
	}

	@Override
	public void addAsset(Asset asset) {
		fAssets.addAsset(asset);
	}

	@Override
	public void addRepairToolInformation(String assetContentName,
			RepairToolInformation repairToolInformation) {

		if (fRepairToolInformations.containsKey(assetContentName)) {
			fRepairToolInformations.get(assetContentName).add(
					repairToolInformation);
		} else {
			fRepairToolInformations.put(assetContentName,
					new Vector<RepairToolInformation>());
			fRepairToolInformations.get(assetContentName).add(
					repairToolInformation);
		}
	}

	@Override
	public void addRepairMaterialInformation(String assetContentName,
			RepairMaterialInformation materialInformation) {
		
		if (fRepairMaterialInformations.containsKey(assetContentName)) {
			fRepairMaterialInformations.get(assetContentName).add(
					materialInformation);
		} else {
			fRepairMaterialInformations.put(assetContentName,
					new Vector<RepairMaterialInformation>());
			fRepairMaterialInformations.get(assetContentName).add(
					materialInformation);
		}
	}

	@Override
	public String toString() {
		return "ManagmentImpl [fClerksDetails=" + fClerksDetails
				+ ", \nfCustomerGroupDetails=" + fCustomerGroupDetails
				+ ", \nfAssets=" + fAssets + ", \nfWarehouse=" + fWarehouse
				+ ", \nfRepairToolInformations=" + fRepairToolInformations
				+ ", \nfRepairMaterialInformations="
				+ fRepairMaterialInformations + "]";
	}
	
	

}
