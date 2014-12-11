package implement;

import interfaces.RepairMaterial;

public class RepairMaterialImpl implements RepairMaterial {

	private String fName;
	private int fQuantity;
	
	
	
	public RepairMaterialImpl(String name, int quantity) {
		this.fName=name;
		this.fQuantity=quantity;
	}
	
	public String getName() {
		return fName;
	}
	
	public void ReduceMaterial(int quantity){
		if (this.fQuantity >= quantity){
		this.fQuantity = this.fQuantity-quantity;
		}
	}
	
	public void IncreaseMaterial(int quantity) {
		this.fQuantity = this.fQuantity + quantity;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Material Name: ");
		builder.append(fName);
		builder.append(". Quantity: ");
		builder.append(fQuantity);
		return builder.toString();
	}

	public int getQuantity() {
		return fQuantity;
	}

	

}
