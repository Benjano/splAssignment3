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
	 * Put a tool back to the Warehouse. Increase the amount of the <name>
	 * RepairTool in the Warehouse by the taken quantity.
	 * 
	 * @param repairTool
	 * 
	 * **/
	void putToolBack(RepairTool repairTool);

	/**
	 * Adds a new Repair Tool to the the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 */
	void addTool(String name, int quantity);

	/**
	 * Add a new Repair Material to the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 */
	void addMaterial(String name, int quantity);

}
