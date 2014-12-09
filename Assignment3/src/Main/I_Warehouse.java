package Main;

public interface I_Warehouse {

	/**
	 * Return the repair tool and reduce the amount of the <name> RepairTool in
	 * the warehouse by the asked quantity
	 * 
	 * @param String
	 *            name - The name of the tool int quantity - The quantity of the
	 *            tool
	 * 
	 * **/
	I_RepairTool takeRepairTool(String name, int quantity);

	/**
	 * Return the repair material and reduce the amount of the <name>
	 * RepairMaterial in the warehouse by the asked quantity
	 * 
	 * @param String
	 *            name - The name of the tool int quantity - The quantity of the
	 *            tool
	 * 
	 * **/
	I_RepairMaterial takeRepairMaterial(String name, int quantity);

	/**
	 * Put a tool back to the Warehouse. Increase the amount of the <name>
	 * RepairTool in the Warehouse by the taken quantity.
	 * 
	 * @param String
	 *            name - The name of the tool int quantity - The quantity of the
	 *            tool
	 * 
	 * **/
	void putToolBack(I_RepairMaterial tool);

}
