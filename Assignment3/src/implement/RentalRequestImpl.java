package implement;

import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.Asset;
import interfaces.RentalRequest;
import consts.AssetStatus;
import consts.RequestStatus;

public class RentalRequestImpl implements RentalRequest {

	private String fId, fAssetType;
	private int fSize, fDurationOfStay;
	private RequestStatus fStatus;
	protected Asset fAssetFound;
	private Logger fLogger;

	public RentalRequestImpl() {
		this.fId = null;
	}

	/**
	 * @param fId
	 * @param fAssetType
	 * @param fSize
	 * @param fDurationOfStay
	 * @param fStatus
	 */
	public RentalRequestImpl(String id, String type, int size,
			int durationOfStay) {
		this.fId = id;
		this.fAssetType = type;
		this.fSize = size;
		this.fDurationOfStay = durationOfStay;
		this.fStatus = RequestStatus.Incomplete;
		this.fAssetFound = null;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public void setRentalRequestStatus(RequestStatus status) {
		fStatus = status;
	}

	public String getAssetType() {
		return fAssetType;
	}

	public int getSize() {
		return fSize;
	}

	public RequestStatus getStatus() {
		return fStatus;
	}

	@Override
	public int getDurationOfStay() {
		return fDurationOfStay;
	}

	@Override
	public synchronized void setFoundAsset(Asset asset) {
		if (fAssetFound == null) {
			this.fAssetFound = asset;
			fLogger.log(
					Level.FINE,
					new StringBuilder()
							.append("Asset set to rental request id:")
							.append(fId).append(" Asset name: ")
							.append(asset.getName()).toString());
		} else {
			fLogger.log(
					Level.WARNING,
					new StringBuilder()
							.append("Asset is already set to rental request id: ")
							.append(fId).toString());
		}
	}

	@Override
	public synchronized DamageReportImpl releaseAsset(double damagePercentage) {
		if (fStatus == RequestStatus.InProgress) {
			fStatus = RequestStatus.Complete;
			fLogger.log(Level.FINE,
					new StringBuilder().append("Rental request id:")
							.append(fId).append(" is now Complete ").toString());

			return new DamageReportImpl(fAssetFound, damagePercentage);
		}
		fLogger.log(
				Level.WARNING,
				new StringBuilder()
						.append("Cannot release asset from rental id: ")
						.append(fId).toString());
		return null;
	}

	@Override
	public void assetOcupied() {
		this.fAssetFound.setStatus(AssetStatus.Occupied);
	}

	@Override
	public double getCostPerNight() {
		if (fAssetFound != null)
			return fAssetFound.getCostPerNight();
		else
			return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rental Request: ");
		builder.append("\n");
		builder.append("Id: ");
		builder.append(fId);
		builder.append(", Asset Type: ");
		builder.append(fAssetType);
		builder.append(", Size: ");
		builder.append(fSize);
		builder.append(", Duration Of Stay: ");
		builder.append(fDurationOfStay);
		builder.append(", Status: ");
		builder.append(fStatus);
		return builder.toString();
	}

	@Override
	public String getID() {
		return fId;
	}

	public boolean equals(Object other) {
		if (other instanceof RentalRequest)
			return ((RentalRequestImpl) other).fId.equals(fId)
					& ((RentalRequestImpl) other).fAssetType.equals(fAssetType)
					& ((RentalRequestImpl) other).fSize == fSize
					& ((RentalRequestImpl) other).fDurationOfStay == fDurationOfStay;
		return false;
	}

}
