package interfaces;

import interfaces.RepairMaterial;

public interface Warehouse {

	/**
	 * Return the repair tool and reduce the amount of the <name> RepairTool in
	 * the warehouse by the asked quantity
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @return The RepairTool from the Warehouse
	 * 
	 * **/
	RepairTool takeRepairTool(String name, int quantity);

	/**
	 * Return the repair material and reduce the amount of the <name>
	 * RepairMaterial in the warehouse by the asked quantity
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @return The RepairMaterial from the Warehouse
	 * 
	 * **/
	RepairMaterial takeRepairMaterial(String name, int quantity);


	/**
	 * Adds a new Repair Tool to the the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 */
	void addTool(RepairTool repairTool);

	/**
	 * Add a new Repair Material to the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 */
	void addMaterial(RepairMaterial repairMaterial);

	/**
	 * Return the sum of all <name> tools in the Warehouse
	 * 
	 * @param name
	 * 
	 * @return The number of <name> in the Warehouse
	 * 
	 * **/
	int countRepairToolInWarehouse(String name);

	/**
	 * Return the sum of all <name> materials in the Warehouse
	 * 
	 * @param name
	 * 
	 * @return The number of <name> in the Warehouse
	 * 
	 * **/
	int countRepairMaterialInWarehouse(String name);

}
