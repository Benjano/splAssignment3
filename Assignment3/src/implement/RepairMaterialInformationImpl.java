package implement;

import java.util.logging.Logger;

import interfaces.RepairMaterialInformation;

public class RepairMaterialInformationImpl implements RepairMaterialInformation {

	
	private String fName;
	private int fQuantity;
	private Logger fLogger;
	
	public RepairMaterialInformationImpl(String name, int quantity){
		this.fName = name;
		this.fQuantity = quantity;
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
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
		builder.append(". Material Quantity: ");
		builder.append(fQuantity);
		return builder.toString();
	}
	
}
