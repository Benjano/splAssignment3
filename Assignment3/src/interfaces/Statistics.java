package interfaces;


public interface Statistics {

	/**
	 * Money gained from rented asset
	 * 
	 * @param fCostPerNight
	 */
	void addIncome(double fCostPerNight);

	/**
	 * Adds a new rental request to collection
	 * 
	 * @param rentalRequest
	 */
	void addRentalRequest(RentalRequest rentalRequest);

	/**
	 * Add tools in process
	 * 
	 * @param repairTools
	 */
	void addToolInProcess(RepairTool repairTools);

	/**
	 * Remove tools in progress
	 * 
	 * @param repairTools
	 */
	void releaseTool(RepairTool repairTools);

	/**
	 * add materials to consumed materials
	 * 
	 * @param takeMaterialsFromWarehouse
	 */
	void consumeMaterial(RepairMaterial repairMaterial);

}
