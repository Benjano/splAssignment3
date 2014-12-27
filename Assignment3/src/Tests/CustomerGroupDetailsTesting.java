package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import implement.CustomerGroupDetailsImpl;
import implement.CustomerImpl;
import implement.RentalRequestImpl;
import interfaces.Customer;
import interfaces.CustomerGroupDetails;
import interfaces.RentalRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.VandalismType;

public class CustomerGroupDetailsTesting {
	CustomerGroupDetails group1;
	RentalRequest rentalRequest;
	Customer customer;

	@Before
	public void setUp() throws Exception {
		group1 = new CustomerGroupDetailsImpl("Nir");
		rentalRequest = new RentalRequestImpl("1", "Hut", 4, 5);
		customer = new CustomerImpl("Aviv", VandalismType.None, 20, 60);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddRentalRequest() {
		group1.addRentalRequest(rentalRequest);
		assertTrue(
				"Customer Group Details Contains a wrong number of rental requests ",
				group1.getNumberOfRentalRequests() == 1);
	}

	@Test
	public void testAddCustomer() {
		group1.addCustomer(customer);
		Customer customer2 = new CustomerImpl("Aviv", VandalismType.None, 20,
				60);
		assertEquals(
				"Customer Group Details Contains a wrong number of rental requests ",
				group1.getCustomer(0), customer2);
	}

	@Test
	public void testGetRentalRequest() {
		group1.addRentalRequest(rentalRequest);
		RentalRequest rentalRequest2 = new RentalRequestImpl("1", "Hut", 4, 5);
		assertEquals("the rental Requests are not the same",
				group1.getRentalRequest(0), rentalRequest2);

	}

	@Test
	public void testGetCustomer() {
		group1.addCustomer(customer);
		Customer customer2 = new CustomerImpl("Aviv", VandalismType.None, 20,
				60);
		assertEquals(
				"Customer Group Details Contains a wrong number of rental requests ",
				group1.getCustomer(0), customer2);

	}

	@Test
	public void testGgetNumberOfRentalRequests() {
		RentalRequest rentalRequest2 = new RentalRequestImpl("2", "Hut", 6, 5);
		group1.addRentalRequest(rentalRequest);
		group1.addRentalRequest(rentalRequest2);
		assertTrue(
				"Customer Group Details Contains a wrong number of rental requests ",
				group1.getNumberOfRentalRequests() == 2);
	}
}
