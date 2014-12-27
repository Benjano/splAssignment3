package interfaces;

public interface RepairMaterial {
	/**
	 * @param name
	 *            - the name of the material
	 * @param quantity
	 *            - the quantity of the material
	 */

	public String getName();

	/**
	 * @return The quantity of the material
	 */
	public int getQuantity();

	

	/**
	 * Try to acquire <quantity> materials.
	 * 
	 * @param quantity
	 * @return true - acquire <quantity> tools. false - failed to acquire.
	 */
	public void Acquire(int quantity);

	
	/**
	 * Release <quantity> material back.
	 * 
	 * @param quantity
	 */
	public void Release(int quantity);
}
