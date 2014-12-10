package interfaces;

public interface RepairTool {
	
	/**
	 * @param name - the name of the tool
	 * @param quantity - the quantity of the tool
	 */
	
	public String getName();
	
	public void ReduceTool(int quantity);
	
	public void IncreaseTool(int quantity);
	
}
