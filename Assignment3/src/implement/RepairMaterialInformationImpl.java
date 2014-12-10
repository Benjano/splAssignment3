package implement;

import interfaces.RepairMaterialInformation;

public class RepairMaterialInformationImpl implements RepairMaterialInformation {

	
	private String fName;
	private int fQuantity;
	
	public RepairMaterialInformationImpl(String name, int quantity){
		this.fName = name;
		this.fQuantity = quantity;
	}
	
	
	public String getName() {
		return fName;
	}


	public int getQuantity() {
		return fQuantity;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Material Name: ");
		builder.append(fName);
		builder.append(" Material Quantity ");
		builder.append(fQuantity);
		return builder.toString();
	}
	
}
