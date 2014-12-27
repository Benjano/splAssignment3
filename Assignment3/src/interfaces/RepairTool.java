package interfaces;

public interface RepairTool {

	/**
	 * @param name
	 *            - the name of the tool
	 * @param quantity
	 *            - the quantity of the tool
	 */

	public String getName();

	/**
	 * @return The quantity of the tool
	 */
	public int getQuantity();

	/**
	 * Try to acquire <quantity> tools.
	 * 
	 * @param quantity
	 * @return true - acquire <quantity> tools. false - failed to acquire.
	 */
	public boolean Acquire(int quantity);

	/**
	 * Release <quantity> tools back.
	 * 
	 * @param quantity
	 */
	public void Release(int quantity);

}
