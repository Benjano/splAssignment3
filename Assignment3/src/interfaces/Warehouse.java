package interfaces;

import interfaces.RepairMaterial;

public interface Warehouse {

	/**
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @return The RepairTool from the Warehouse
	 * 
	 * @pre: isRepairToolEnough(<name>,<quantity>) == true
	 * @post: Tool <name> quantity is reduced by <quantity>
	 * @post: return new RepairTool (<name>,<quantity>)
	 * 
	 * 
	 * **/
	RepairTool takeRepairTool(String name, int quantity);

	/**
	 * @param name
	 * @param quantity
	 * @return true if there is enough RepairTools <name> in the warehouse else
	 *         false
	 * 
	 * @pre: none
	 * @post: return RepairTool <name> <quantity> >= the quantity of RepairTool
	 *        <name> in the warehouse
	 */
	boolean isRepairToolEnough(String name, int quantity);

	/**
	 * Return the repair material and reduce the amount of the <name>
	 * RepairMaterial in the warehouse by the asked quantity
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @return The RepairMaterial from the Warehouse
	 * 
	 * @pre: isRepairMaterialEnough(<name>,<quantity>) == true
	 * @post: Material <name> quantity is reduced by <quantity>
	 * @post: return new RepairMaterial (<name>,<quantity>)
	 * 
	 * **/
	RepairMaterial takeRepairMaterial(String name, int quantity);

	/**
	 * @param name
	 * @param quantity
	 * @return true if there is enough RepairMaterial <name> in the warehouse
	 *         else false
	 * 
	 * @pre: none
	 * @post: return RepairMaterial <name> <quantity> >= the quantity of
	 *        RepairMaterial <name> in the warehouse
	 */
	boolean isRepairMaterialEnough(String name, int quantity);

	/**
	 * Adds a new Repair Tool to the the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @pre: none
	 * @post: increase the amount of repairTool in the warehouse by
	 *        repairTool.getQuantity() or add a new RepairTool if not exists
	 */
	void addTool(RepairTool repairTool);

	/**
	 * Add a new Repair Material to the Warehouse
	 * 
	 * @param name
	 * @param quantity
	 * 
	 * @pre: none
	 * @post: increase the amount of repairMaterial in the warehouse by
	 *        repairMaterial.getQuantity() or add a new RepairMaterial if not
	 *        exists
	 */
	void addMaterial(RepairMaterial repairMaterial);

	/**
	 * Return the sum of all <name> tools in the Warehouse
	 * 
	 * @param name
	 * 
	 * @return The number of <name> in the Warehouse
	 * 
	 * @pre: none
	 * @post: return the quantity of RepairTool <name> in the warehouse
	 * **/
	int countRepairToolInWarehouse(String name);

	/**
	 * Return the sum of all <name> materials in the Warehouse
	 * 
	 * @param name
	 * 
	 * @return The number of <name> in the Warehouse
	 * 
	 * @pre: none
	 * @post: return the quantity of RepairMaterial <name> in the warehouse
	 * **/
	int countRepairMaterialInWarehouse(String name);

}
