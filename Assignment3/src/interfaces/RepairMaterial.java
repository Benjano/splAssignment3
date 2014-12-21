package interfaces;

public interface RepairMaterial {
	/**
	 * @param name
	 *            - the name of the material
	 * @param quantity
	 *            - the quantity of the material
	 */

	public String getName();

	public int getQuantity();

	public void Aquire(int quantity);

	public void Release(int quantity);
}
