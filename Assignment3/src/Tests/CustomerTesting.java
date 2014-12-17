package Tests;

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
		customer1 = new CustomerImpl("Nir", VandalismType.Arbitary, 20, 50);
		customer2 = new CustomerImpl("Aviv", VandalismType.Fixed, 20, 50);
		customer3 = new CustomerImpl("David", VandalismType.None, 20, 50);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateDamagePercentage() {
		double customer1DamagePercentage = customer1
				.calculateDamagePercentage();
		assertTrue("Customer " + customer1.getName()
				+ " damage percentage is out of bounds",
				customer1DamagePercentage >= 0.2);
		assertTrue("Customer " + customer1.getName()
				+ " damage percentage is out of bounds",
				customer1DamagePercentage <= 0.5);

		assertEquals("Customer " + customer2.getName()
				+ " damage percentage is wrong",
				customer2.calculateDamagePercentage(), (20d + 50d) / 2d / 100d, DELTA);
		
		assertEquals("Customer " + customer3.getName()
				+ " damage percentage is wrong",
				customer3.calculateDamagePercentage(), 0.05, DELTA);
	}
}
