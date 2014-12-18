package runnable;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import consts.AssetStatus;
import interfaces.Asset;
import interfaces.Assets;
import interfaces.ClerkDetails;
import interfaces.RentalRequest;

public class RunnebleClerk implements Runnable {
	ClerkDetails fClerkDetails;
	BlockingQueue<RentalRequest> fRentalRequest;
	Assets fAssets;
	AtomicInteger fNumberOfRentalRequests;

	/**
	 * @param clerkDetails
	 * @param rentalRequest
	 * @param numberOfRentalRequests
	 * @param assets
	 */
	public RunnebleClerk(ClerkDetails clerkDetails,
			BlockingQueue<RentalRequest> rentalRequest,
			AtomicInteger numberOfRentalRequests, Assets assets) {
		super();
		this.fClerkDetails = clerkDetails;
		this.fRentalRequest = rentalRequest;
		this.fNumberOfRentalRequests = numberOfRentalRequests;
		this.fAssets = assets;
	}

	@Override
	public void run() {
		double workedTime = 0;
		while (workedTime < 8) {
			RentalRequest rentalRequest = null;
			try {
				rentalRequest = fRentalRequest.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Asset matchingAsset = findMatchingAsset(rentalRequest);
			workedTime += matchingAsset.getLocation().calculateDistance(
					fClerkDetails.getLocation()) * 2;
			try {
				Thread.sleep((long) (workedTime * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			RunnableCustomerGroupManager.class.notifyAll();

		}
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		run();
	}

	private Asset findMatchingAsset(RentalRequest rentalRequest) {
		fNumberOfRentalRequests.incrementAndGet();
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
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return matchingAsset;
	}

	private synchronized Asset checkAsset(Asset asset) {
		if (asset.getStatus() == AssetStatus.Available) {
			asset.setStatus(AssetStatus.Booked);
			fNumberOfRentalRequests.decrementAndGet();
		}
		return asset;
	}
}
