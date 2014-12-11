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



	@Override
	public int getDurationOfStay() {
		return fDurationOfStay;
	}

}
