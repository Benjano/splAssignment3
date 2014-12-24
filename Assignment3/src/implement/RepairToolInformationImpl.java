package implement;

import java.util.logging.Logger;

import interfaces.RepairToolInformation;

public class RepairToolInformationImpl implements RepairToolInformation {

	private String fName;
	private int fQuantity;
	private Logger fLogger;
	
	public RepairToolInformationImpl(String name, int quantity){
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
		builder.append("Tool Name: ");
		builder.append(fName);
		builder.append(". Tool Quantity: ");
		builder.append(fQuantity);
		return builder.toString();
	}
}
