package interfaces;

import consts.VandalismType;

public interface Customer {
	
	/**
	 * @return The name of the Customer
	 */
	String getName();
	
	/**
	 * @return The vandalism type
	 */
	VandalismType getVandalismType();
	
	/**
	 * @return The calculate damage percentage
	 */
	double calculateDamagePercentage();
	

}
