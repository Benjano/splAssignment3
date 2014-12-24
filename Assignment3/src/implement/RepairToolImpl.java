package implement;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import interfaces.RepairTool;

public class RepairToolImpl implements RepairTool {

	private String fName;
	private Semaphore fQuantity;
	private Logger fLogger;

	public RepairToolImpl(String name, int quantity) {
		this.fName = name;
		this.fQuantity = new Semaphore(quantity);
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}

	public String getName() {
		return fName;
	}

	public int getQuantity() {
		return fQuantity.availablePermits();
	}

	public void Acquire(int quantity) {
		// if (this.fQuantity >= quantity) {
		// this.fQuantity = this.fQuantity - quantity;
		// }
		try {
			fQuantity.acquire(quantity);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
