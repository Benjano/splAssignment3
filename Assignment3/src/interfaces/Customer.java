package interfaces;

import consts.VandalismType;

public interface Customer {

	/**
	 * @return The vandalism type
	 */
	VandalismType getVandalismType();

	/**
	 * @return The calculate damage percentage
	 */
	double getDamagePercentage();

}
