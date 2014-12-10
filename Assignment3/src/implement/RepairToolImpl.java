package implement;

import interfaces.RepairTool;

public class RepairToolImpl implements RepairTool {

	private String fName;
	private int fQuantity;

	public RepairToolImpl(String name, int quantity) {
		this.fName = name;
		this.fQuantity = quantity;
	}

	public String getName() {
		return fName;
	}
	
	public int getQuantity() {
		return fQuantity;
	}

	public void ReduceTool(int quantity) {
		if (this.fQuantity >= quantity) {
			this.fQuantity = this.fQuantity - quantity;
		}
	}

	public void IncreaseTool(int quantity) {
		this.fQuantity = this.fQuantity + quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tool Name: ");
		builder.append(fName);
		builder.append(" Tool Quantity ");
		builder.append(fQuantity);
		return builder.toString();
	}

}
