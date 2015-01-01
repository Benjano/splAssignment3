package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import implement.CustomerImpl;
import interfaces.Customer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.VandalismType;

public class CustomerTesting {
	private Customer customer1;
	private Customer customer2;
	private Customer customer3;
	private final double DELTA = 0.001;

	@Before
	public void setUp() throws Exception {
		customer1 = new CustomerImpl("Nir", VandalismType.ARBITRARY, 20, 50);
		customer2 = new CustomerImpl("Aviv", VandalismType.FIXED, 20, 50);
		customer3 = new CustomerImpl("David", VandalismType.NONE, 20, 50);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetVandalismType() {
		assertEquals("Customer " + customer1.getVandalismType(),
				VandalismType.ARBITRARY, customer1.getVandalismType());
	}

	@Test
	public void testCalculateDamagePercentage() {
		double customer1DamagePercentage = customer1.getDamagePercentage();
		assertTrue("Damage percentage is out of bounds",
				customer1DamagePercentage >= 20);
		assertTrue("Damage percentage is out of bounds",
				customer1DamagePercentage <= 50);

		assertEquals("Damage percentage is wrong",
				customer2.getDamagePercentage(), (20d + 50d) / 2d, DELTA);

		assertEquals("Damage percentage is wrong",
				customer3.getDamagePercentage(), 0.5, DELTA);

	}
}
