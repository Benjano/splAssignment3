package runnable;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import consts.AssetStatus;
import consts.RequestStatus;
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
		fWorkedTime = 0;
	}

	@Override
	public void run() {
		System.out.println(fClerkDetails.getName() + " Started working");
		try {
			fCyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}

		while (fNumberOfRentalRequests.get() > 0) {
			while (fWorkedTime < 8) {
				RentalRequest rentalRequest = null;
				try {
					rentalRequest = fRentalRequest.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Asset matchingAsset = findMatchingAsset(rentalRequest);
				fWorkedTime += matchingAsset.getLocation().calculateDistance(
						fClerkDetails.getLocation()) * 2;
				try {
					Thread.sleep((long) (fWorkedTime * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rentalRequest.setFoundAsset(matchingAsset);
				rentalRequest.setRentalRequestStatus(RequestStatus.Fulfilled);
				notifyCustomerGroup();

			}
			try {
				fCyclicBarrierShift.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

			fWorkedTime = 0;
		}
	}

	private Asset findMatchingAsset(RentalRequest rentalRequest) {
		Asset matchingAsset = null;
		Vector<Asset> searchResualt = fAssets.findAssetByTypeAndSize(
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
				waitNow();
			}
		}
		return matchingAsset;
	}

	private synchronized void waitNow() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
