package implement;

import java.util.concurrent.Semaphore;

import interfaces.RepairTool;

public class RepairToolImpl implements RepairTool {

	private String fName;
	private Semaphore fQuantity;

	public RepairToolImpl(String name, int quantity) {
		this.fName = name;
		this.fQuantity = new Semaphore(quantity);
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public int getQuantity() {
		return fQuantity.availablePermits();
	}

	@Override
	public boolean Acquire(int quantity) {
		return fQuantity.tryAcquire(quantity);
	}

	@Override
	public void Release(int quantity) {
		this.fQuantity.release(quantity);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tool Name: ");
		builder.append(fName);
		builder.append(". Quantity: ");
		builder.append(fQuantity);
		return builder.toString();
	}

}
