package interfaces;

public interface CustomerGroupDetails {

	/**
	 * Add a new RentalRequest to collection
	 * 
	 * @param request
	 */
	public void addRentalRequest(RentalRequest request);

	/**
	 * * Add a new Customer to collection
	 * 
	 * @param customer
	 */
	public void addCustomer(Customer customer);

	public Customer getCustomer(int i);
	
	public String getName();

	public RentalRequest getRentalRequest(int i);

	/**
	 * 
	 * @return The number of rental requests
	 */
	public int getNumberOfRentalRequests();

}
