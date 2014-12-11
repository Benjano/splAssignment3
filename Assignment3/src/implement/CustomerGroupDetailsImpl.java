package implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interfaces.Customer;
import interfaces.CustomerGroupDetails;
import interfaces.RentalRequest;

public class CustomerGroupDetailsImpl implements CustomerGroupDetails {

	private String fGroupManagerName;
	List<Customer> fCustomers;
	List<RentalRequest> fRentalRequests;

	public CustomerGroupDetailsImpl(String groupManagerName) {
		this.fGroupManagerName = groupManagerName;
		this.fCustomers = Collections
				.synchronizedList(new ArrayList<Customer>());
		this.fRentalRequests = Collections
				.synchronizedList(new ArrayList<RentalRequest>());
	}

	@Override
	public void addRentalRequest(RentalRequest request) {
		fRentalRequests.add(request);
	}

	@Override
	public void addCustomer(Customer customer) {
		fCustomers.add(customer);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group manager name: ").append(fGroupManagerName).append(" \nCustomers in Group:");
		
		for(Customer customer: fCustomers) {
			builder.append("\n").append(customer);
		}
		builder.append("\nRental requests:");
		for(RentalRequest request: fRentalRequests) {
			builder.append("\n").append(request);
		}
		
		return builder.toString();
	}
	
	

}
