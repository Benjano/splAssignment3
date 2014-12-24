package Tests;

import static org.junit.Assert.assertTrue;
import implement.CustomerGroupDetailsImpl;
import implement.RentalRequestImpl;
import interfaces.CustomerGroupDetails;
import interfaces.RentalRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CustomerGroupDetailsTesting {
	CustomerGroupDetails group1;
	RentalRequest rentalRequest;
	
	@Before
	public void setUp() throws Exception {
		group1 = new CustomerGroupDetailsImpl("Nir");
		rentalRequest = new RentalRequestImpl("1", "Hut", 4, 5);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testAddRentalRequest() {
		group1.addRentalRequest(rentalRequest);
		assertTrue("Customer Group Details Contains a wrong number of rental requests ",
				group1.getNumberOfRentalRequests()==1);
	}

	@Test
	public void testAddCustomer() {
		
	}

	@Test
	public void testGetRentalRequest() {
		
	}

	@Test
	public void testGetCustomer() {
		
	}
	
	@Test
	public void testGgetNumberOfRentalRequests() {
		
	}
	
}
