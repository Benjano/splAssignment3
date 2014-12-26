package runnable;

import interfaces.Customer;
import interfaces.Statistics;

import java.util.concurrent.Callable;

import consts.Timing;

public class CallableSimulateStayInAsset implements Callable<Double> {

	private Customer fCustomer;
	private int fDaysInAsset;
	private double fCostPerNight;
	private Statistics fStatistics;

	/**
	 * @param fAsset
	 * @param fCustomer
	 * @param fdaysInAsset
	 */
	public CallableSimulateStayInAsset(Customer customer, int daysInAsset,
			double costPerNight, Statistics statistics) {
		this.fCustomer = customer;
		this.fDaysInAsset = daysInAsset;
		this.fCostPerNight = costPerNight;
		this.fStatistics = statistics;
	}

	@Override
	public Double call() throws Exception {
		int daysLeft = fDaysInAsset;
		while (daysLeft > 0) {
			Thread.sleep(24 * Timing.SECOND);
			fStatistics.addIncome(fCostPerNight);
			daysLeft--;
		}

		return fCustomer.getDamagePercentage();
	}

}
