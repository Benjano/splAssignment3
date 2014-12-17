package implement;

import interfaces.RentalRequest;
import consts.RequestStatus;

public class RentalRequestImpl implements RentalRequest {
	
	private String fId, fAssetType;
	private int fSize, fDurationOfStay;
	private RequestStatus fStatus;
	
	/**
	 * @param fId
	 * @param fAssetType
	 * @param fSize
	 * @param fDurationOfStay
	 * @param fStatus
	 */
	public RentalRequestImpl(String id, String type, int size,
			int durationOfStay, RequestStatus status) {
		super();
		this.fId = id;
		this.fAssetType = type;
		this.fSize = size;
		this.fDurationOfStay = durationOfStay;
		this.fStatus = status;
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

}
