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

	public String getAssetType();

	public int getSize();

	/**
	 * Set the asset of the request to the found asset by the clerk. (can happen
	 * only once)
	 * 
	 * @param asset
	 * 
	 * @pre foundAsset = null
	 * @post foundAsset = asset
	 */
	public void setFoundAsset(Asset asset);

	/**
	 * Update asset content damage
	 * 
	 * @param damagePercentage
	 * @return 
	 * @pre rentalRequest status = InProgress
	 * @post rentalRequest status = Complete
	 * @post asset status = Available
	 * 
	 */
	public DamageReport releaseAsset(double damagePercentage);

	/**
	 * Ocupy the asset
	 */
	public void assetOcupied();

	/**
	 * 
	 * @return the status of the asset
	 */
	public RequestStatus getStatus();

	public double getCostPerNight();
}
