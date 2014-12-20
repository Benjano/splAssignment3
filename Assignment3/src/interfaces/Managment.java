package interfaces;

public interface Managment {

	/**
	 * Adds a new clerk to collection
	 * 
	 * @param clerkDetails
	 */
	void addClerk(ClerkDetails clerkDetails);

	/**
	 * Adds a new customer group to collection
	 * 
	 * @param customerGroupDetails
	 */
	void addCustomerGroup(CustomerGroupDetails customerGroupDetails);

	/**
	 * Adds a new repair tool to warehouse
	 * 
	 * @param repairTool
	 */
	void addItemRepairTool(RepairTool repairTool);

	/**
	 * Adds a new repair tool information to collection
	 * 
	 * @param assetContentName
	 * @param repairToolInformation
	 */
	void addRepairToolInformation(String assetContentName,
			RepairToolInformation repairToolInformation);

	/**
	 * Adds a new repair material to warehouse
	 * 
	 * @param repairMaterial
	 */
	void addItemRepairMaterial(RepairMaterial repairMaterial);

	/**
	 * Adds a new repair material information to collection
	 * 
	 * @param assetContentName
	 * @param materialInformation
	 */
	void addRepairMaterialInformation(String assetContentName,
			RepairMaterialInformation materialInformation);

	/**
	 * Adds a new asset to assets
	 * 
	 * @param asset
	 */
	void addAsset(Asset asset);

	/**
	 * Adds a new damageReport to collection
	 * 
	 * @param damageReport
	 */
	void submitDamageReport(DamageReport damageReport);

	/**
	 * Adds a new rental request for clerks to handle
	 * 
	 * @param rentalRequest
	 */
	void submitRentalRequest(RentalRequest rentalRequest);
	
	void start();
}
