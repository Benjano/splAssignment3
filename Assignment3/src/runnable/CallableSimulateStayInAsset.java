package runnable;

import interfaces.Customer;
import java.util.concurrent.Callable;

public class CallableSimulateStayInAsset implements Callable<Double> {

	private Customer fCustomer;
	private int fDaysInAsset;

	/**
	 * @param fAsset
	 * @param fCustomer
	 * @param fdaysInAsset
	 */
	public CallableSimulateStayInAsset(Customer customer, int daysInAsset) {
		this.fCustomer = customer;
		this.fDaysInAsset = daysInAsset;
	}

	@Override
	public Double call() throws Exception {
		int hoursInAsset = fDaysInAsset * 24;
		while (hoursInAsset > 0) {
			Thread.sleep(1000);
			hoursInAsset--;
		}

		return fCustomer.calculateDamagePercentage();
	}

}
