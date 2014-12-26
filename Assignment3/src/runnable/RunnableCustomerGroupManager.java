package runnable;

import interfaces.Customer;
import interfaces.CustomerGroupDetails;
import interfaces.Managment;
import interfaces.RentalRequest;
import interfaces.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import consts.RequestStatus;

public class RunnableCustomerGroupManager implements Runnable {

	private CustomerGroupDetails fCustomerGroupDetails;
	private Managment fManagment;
	private Statistics fStatistics;
	private CyclicBarrier fCyclicBarrier;
	private CustomerClerkMessenger fCustomerClerkMessenger;
	private Logger fLogger;

	public RunnableCustomerGroupManager() {
	}

	public RunnableCustomerGroupManager(
			CustomerGroupDetails customerGroupDetails, Managment managment,
			Statistics statistics, CyclicBarrier cyclicBarrier,
			CustomerClerkMessenger customerClerkMessenger) {
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

			while (rentalRequest.getStatus() != RequestStatus.Fulfilled) {
				synchronized (fCustomerClerkMessenger) {
					try {
						fCustomerClerkMessenger.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// Customer stay in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.InProgress);
			rentalRequest.assetOcupied();

			double damagePercentage = simulateStayInAsset(rentalRequest);

			fManagment.submitDamageReport(rentalRequest
					.releaseAsset(damagePercentage));

			synchronized (fCustomerClerkMessenger) {
				fCustomerClerkMessenger.notifyAll();
			}

			// Customer done staying in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.Complete);

			i++;
			rentalRequest = fCustomerGroupDetails.getRentalRequest(i);
		}
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
