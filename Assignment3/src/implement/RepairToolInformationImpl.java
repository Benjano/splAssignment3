package implement;


import interfaces.RepairToolInformation;

public class RepairToolInformationImpl implements RepairToolInformation {

	private String fName;
	private int fQuantity;

	public RepairToolInformationImpl(String name, int quantity) {
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
		builder.append("Tool Name: ");
		builder.append(fName);
		builder.append(". Tool Quantity: ");
		builder.append(fQuantity);
		return builder.toString();
	}
}
