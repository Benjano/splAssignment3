package runnable;

import interfaces.Customer;
import interfaces.Statistics;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import consts.Timing;

public class CallableSimulateStayInAsset implements Callable<Double> {

	private Customer fCustomer;
	private int fDaysInAsset;
	private double fCostPerNight;
	private Statistics fStatistics;
	private Logger fLogger;

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
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public Double call() throws Exception {
		int daysLeft = fDaysInAsset;
		while (daysLeft > 0) {
			Thread.sleep(24 * Timing.SECOND);
			fStatistics.addIncome(fCostPerNight);
			daysLeft--;
		}

		fLogger.log(Level.FINE,
				new StringBuilder().append("Customer ").append(fCustomer)
						.append(" is done").toString());

		return fCustomer.getDamagePercentage();
	}

}
