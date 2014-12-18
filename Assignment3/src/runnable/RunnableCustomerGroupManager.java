package runnable;

import interfaces.Customer;
import interfaces.CustomerGroupDetails;
import interfaces.RentalRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import consts.AssetStatus;
import consts.RequestStatus;

public class RunnableCustomerGroupManager implements Runnable {

	private CustomerGroupDetails fCustomerGroupDetails;
	private BlockingQueue<RentalRequest> fSharedRentalRequests;

	public RunnableCustomerGroupManager(
			CustomerGroupDetails customerGroupDetails,
			BlockingQueue<RentalRequest> sharedRentalRequests) {
		this.fCustomerGroupDetails = customerGroupDetails;
		this.fSharedRentalRequests = sharedRentalRequests;
	}

	@Override
	public void run() {
		int i = 0;
		RentalRequest rentalRequest = fCustomerGroupDetails.getRentalRequest(i);
		while (rentalRequest != null) {
			try {
				fSharedRentalRequests.put(rentalRequest);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while (rentalRequest.getStatus() != RequestStatus.Fulfilled)
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// Customer stay in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.InProgress);
			rentalRequest.getFoundAsset().setStatus(AssetStatus.Occupied);

			ExecutorService executorService = Executors.newCachedThreadPool();
			int j = 0;
			Customer customer = fCustomerGroupDetails.getCustomer(j);
			while (customer != null) {
				CallableSimulateStayInAsset callableSimulateStayInAsset = new CallableSimulateStayInAsset(
						customer, rentalRequest.getDurationOfStay());
				executorService.submit(callableSimulateStayInAsset);
				j++;
				customer = fCustomerGroupDetails.getCustomer(j);
			}

			// TODO: Until all CallableSimulateStayInAsset are done
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double damagePrecentage = 0;

			// Customer done staying in asset
			rentalRequest.setRentalRequestStatus(RequestStatus.Complete);

			// Calculate total damage precentage and update asset content
			rentalRequest.getFoundAsset().damageAssetContent(damagePrecentage);

			// TODO: FIX?
			rentalRequest.getFoundAsset().setStatus(AssetStatus.Available);

			i++;
			rentalRequest = fCustomerGroupDetails.getRentalRequest(i);
		}
	}
}
