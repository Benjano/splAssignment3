package interfaces;

public interface RepairTool {
	
	/**
	 * @param name - the name of the tool
	 * @param quantity - the quantity of the tool
	 */
	
	public String getName();
	
	public int getQuantity();
	
	public void Acquire(int quantity);
	
	public void Release(int quantity);
	
}
