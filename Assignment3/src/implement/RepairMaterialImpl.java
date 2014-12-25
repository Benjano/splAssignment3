package implement;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import interfaces.RepairMaterial;

public class RepairMaterialImpl implements RepairMaterial {

	private String fName;
	private Semaphore fQuantity;
	private Logger fLogger;

	public RepairMaterialImpl(String name, int quantity) {
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
		try {
			fQuantity.acquire(quantity);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void Release(int quantity) {
		fQuantity.release(quantity);
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

}
