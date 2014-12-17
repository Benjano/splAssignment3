package implement;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import consts.AssetStatus;
import interfaces.Asset;
import interfaces.Assets;
import interfaces.ClerkDetails;
import interfaces.RentalRequest;

public class RunnebleClerk implements Runnable {
	ClerkDetails fClerkDetails;
	Vector<RentalRequest> fRentalRequest;
	Assets fAssets;
	AtomicInteger fNumberOfRentalRequests;

	/**
	 * @param clerkDetails
	 * @param rentalRequest
	 * @param numberOfRentalRequests
	 * @param assets
	 */
	public RunnebleClerk(ClerkDetails clerkDetails,
			Vector<RentalRequest> rentalRequest,
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
		for (RentalRequest rentalRequest : fRentalRequest) {

			Vector<Asset> searchResualt = fAssets.findAssetByTypeAndSize(
					rentalRequest.getAssetType(), rentalRequest.getSize());
			for (Asset asset : searchResualt) {
				if (asset.getStatus() == AssetStatus.Available) {
					workedTime += asset.getLocation().calculateDistance(
							fClerkDetails.getLocation()) * 2;

					try {
						Thread.sleep((long) (workedTime * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					asset.setStatus(AssetStatus.Booked);
					// TODO: Notify customer
					break;
				}
			}

			if (workedTime >= 8) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				workedTime = 0;
			}
		}
	}
}
