package runnable;

import interfaces.Customer;
import interfaces.CustomerGroupDetails;
import interfaces.Managment;
import interfaces.RentalRequest;
import interfaces.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import consts.RequestStatus;

public class RunnableCustomerGroupManager implements Runnable {

	private CustomerGroupDetails fCustomerGroupDetails;
	private Managment fManagment;
	private Statistics fStatistics;
	private CyclicBarrier fCyclicBarrier;
	private Logger fLogger;
	private Messenger fCustomerClerkMessenger;

	public RunnableCustomerGroupManager() {
	}

	public RunnableCustomerGroupManager(
			CustomerGroupDetails customerGroupDetails, Managment managment,
			Statistics statistics, CyclicBarrier cyclicBarrier,
			Messenger customerClerkMessenger) {
		this.fCustomerGroupDetails = customerGroupDetails;
		this.fManagment = managment;
		this.fStatistics = statistics;
		this.fCyclicBarrier = cyclicBarrier;
		this.fCustomerClerkMessenger = customerClerkMessenger;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public void run() {
		try {
			fCyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}

		int i = 0;
		RentalRequest rentalRequest = fCustomerGroupDetails.getRentalRequest(i);
		while (rentalRequest != null) {

			fManagment.submitRentalRequest(rentalRequest);
			fStatistics.addRentalRequest(rentalRequest);

			while (rentalRequest.getStatus() != RequestStatus.FULFILLED) {
				fLogger.log(
						Level.INFO,
						new StringBuilder().append("Group runned by ")
								.append(fCustomerGroupDetails.getName())
								.append(" is waiting for request to be filled")
								.toString());

				synchronized (rentalRequest) {
					try {
						rentalRequest.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			fLogger.log(
					Level.INFO,
					new StringBuilder().append("Group runned by ")
							.append(fCustomerGroupDetails.getName())
							.append(" request been filled").toString());

			// Customer stay in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.IN_PROGRESS);
			rentalRequest.assetOcupied();

			double damagePercentage = simulateStayInAsset(rentalRequest);

			fManagment.submitDamageReport(rentalRequest
					.releaseAsset(damagePercentage));

			synchronized (fManagment) {
				fManagment.notifyAll();
			}

			fLogger.log(
					Level.INFO,
					new StringBuilder().append("Group runned by ")
							.append(fCustomerGroupDetails.getName())
							.append(" realse asset.").toString());

			// Customer done staying in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.COMPLETE);

			synchronized (fCustomerClerkMessenger) {
				fCustomerClerkMessenger.notifyAll();
			}

			i++;
			rentalRequest = fCustomerGroupDetails.getRentalRequest(i);
		}
		
		fLogger.log(
				Level.INFO,
				new StringBuilder()
						.append("Customer group ")
						.append(fCustomerGroupDetails.getName()).append(" is done").toString());
	}

	private double simulateStayInAsset(RentalRequest rentalRequest) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<Double>> futureList = new ArrayList<Future<Double>>();
		int j = 0;
		Customer customer = fCustomerGroupDetails.getCustomer(j);
		while (customer != null) {
			CallableSimulateStayInAsset callableSimulateStayInAsset = new CallableSimulateStayInAsset(
					customer, rentalRequest.getDurationOfStay(),
					rentalRequest.getCostPerNight(), fStatistics);
			Future<Double> future = executorService
					.submit(callableSimulateStayInAsset);
			futureList.add(future);
			j++;
			customer = fCustomerGroupDetails.getCustomer(j);
		}

		double damagePercentage = 0;
		for (Future<Double> future : futureList) {
			try {
				damagePercentage += future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		executorService.shutdown();
		return damagePercentage;
	}
}
