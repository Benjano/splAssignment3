package interfaces;

import consts.RequestStatus;

public interface RentalRequest {

	/**
	 * Update the Status of the rental request
	 * 
	 * @param status
	 */
	public void setRentalRequestStatus(RequestStatus status);
	
	/**
	 * @return durationOfStay
	 */
	public int getDurationOfStay();
}
