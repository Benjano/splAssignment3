package runnable;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import consts.AssetStatus;
import consts.RequestStatus;
import consts.Timing;
import implement.RentalRequestImpl;
import interfaces.Asset;
import interfaces.Assets;
import interfaces.ClerkDetails;
import interfaces.RentalRequest;

public class RunnebleClerk implements Runnable {
	private ClerkDetails fClerkDetails;
	private BlockingQueue<RentalRequest> fRentalRequest;
	private Assets fAssets;
	private AtomicInteger fNumberOfRentalRequests;
	private int fWorkedTime;
	private CyclicBarrier fCyclicBarrier, fCyclicBarrierShift;
	private CustomerClerkMessenger fCustomerClerkMessenger;
	private Logger fLogger;

	/**
	 * @param clerkDetails
	 * @param rentalRequest
	 * @param numberOfRentalRequests
	 * @param assets
	 */
	public RunnebleClerk(ClerkDetails clerkDetails,
			BlockingQueue<RentalRequest> rentalRequest,
			AtomicInteger numberOfRentalRequests, Assets assets,
			CyclicBarrier cyclicBarrier, CyclicBarrier cyclicBarrierShift,
			CustomerClerkMessenger customerClerkMessenger) {
		this.fClerkDetails = clerkDetails;
		this.fRentalRequest = rentalRequest;
		this.fNumberOfRentalRequests = numberOfRentalRequests;
		this.fAssets = assets;
		this.fCyclicBarrier = cyclicBarrier;
		this.fCyclicBarrierShift = cyclicBarrierShift;
		this.fCustomerClerkMessenger = customerClerkMessenger;
		this.fWorkedTime = 0;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public void run() {
		try {
			fLogger.log(
					Level.FINE,
					new StringBuilder().append("Clerk ")
							.append(fClerkDetails.getName())
							.append(" is waiting for other clerks to start")
							.toString());
			fCyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}

		while (fWorkedTime < 8 & fNumberOfRentalRequests.get() > 0) {
			RentalRequest rentalRequest = null;
			try {
				fLogger.log(
						Level.FINE,
						new StringBuilder()
								.append("Clerk ")
								.append(fClerkDetails.getName())
								.append(" is trying to take a new rental request from queue")
								.toString());
				rentalRequest = fRentalRequest.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (rentalRequest != null && rentalRequest.getID() != null) {
				fLogger.log(
						Level.FINE,
						new StringBuilder().append("Clerk ")
								.append(fClerkDetails.getName())
								.append(" took rental request id: ")
								.append(rentalRequest.getID()).toString());

				Asset matchingAsset = findMatchingAsset(rentalRequest);
				fLogger.log(
						Level.FINE,
						new StringBuilder().append("Clerk ")
								.append(fClerkDetails.getName())
								.append(" took found matching asset: ")
								.append(matchingAsset.getName()).toString());

				int distanceInSeconds = (int) (matchingAsset.getLocation()
						.calculateDistance(fClerkDetails.getLocation()) * 2);
				fWorkedTime += distanceInSeconds;
				fLogger.log(
						Level.FINE,
						new StringBuilder().append("Clerk ")
								.append(fClerkDetails.getName())
								.append(" is going to sleep for ")
								.append(distanceInSeconds).append(" seconds")
								.toString());
				try {
					Thread.sleep((long) (distanceInSeconds * Timing.SECOND));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				fLogger.log(
						Level.FINE,
						new StringBuilder().append("Clerk ")
								.append(fClerkDetails.getName())
								.append(" woke up from sleeping ").toString());

				rentalRequest.setFoundAsset(matchingAsset);
				rentalRequest.setRentalRequestStatus(RequestStatus.Fulfilled);
				notifyCustomerGroup();
			}
			System.out.println(fNumberOfRentalRequests.get());
			if (fNumberOfRentalRequests.get() == 0) {
				try {
					synchronized (fRentalRequest) {
						fRentalRequest.put(new RentalRequestImpl());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		fLogger.log(
				Level.FINE,
				new StringBuilder().append("Clerk ")
						.append(fClerkDetails.getName())
						.append(" finished his shift").toString());
		synchronized (this) {
			notifyAll();
			try {
				fCyclicBarrierShift.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		fWorkedTime = 0;
	}

	private Asset findMatchingAsset(RentalRequest rentalRequest) {
		Asset matchingAsset = null;
		List<Asset> searchResualt = fAssets.findAssetByTypeAndSize(
				rentalRequest.getAssetType(), rentalRequest.getSize());
		while (matchingAsset == null) {
			for (Asset asset : searchResualt) {
				matchingAsset = checkAsset(asset);
				if (matchingAsset != null)
					break;
			}
			if (matchingAsset == null) {
				System.out.println(fClerkDetails.getName()
						+ " is waiting for asset to be available");
				System.out.println(rentalRequest);
				synchronized (fCustomerClerkMessenger) {
					try {
						fCustomerClerkMessenger.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				searchResualt = fAssets.findAssetByTypeAndSize(
						rentalRequest.getAssetType(), rentalRequest.getSize());
			}
		}
		return matchingAsset;
	}

	private void notifyCustomerGroup() {
		synchronized (fCustomerClerkMessenger) {
			fCustomerClerkMessenger.notify();
		}
	}

	private Asset checkAsset(Asset asset) {
		if (asset.getStatus() == AssetStatus.Available) {
			asset.setStatus(AssetStatus.Booked);
			fNumberOfRentalRequests.decrementAndGet();
			return asset;
		}
		return null;
	}
}
