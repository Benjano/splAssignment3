package implement;

import consts.VandalismType;
import interfaces.Customer;

public class CustomerImpl implements Customer {
	private String fName;
	private VandalismType fVandalismType;
	private double fMinDamage, fMaxDamage, fDamagePercentage;

	public CustomerImpl(String name, VandalismType vandalismType,
			double minDamage, double maxDamage) {
		this.fName = name;
		this.fVandalismType = vandalismType;
		this.fMinDamage = minDamage;
		this.fMaxDamage = maxDamage;
		this.fDamagePercentage = 0;
		calculateDamagePercentage();
	}

	@Override
	public VandalismType getVandalismType() {
		return fVandalismType;
	}

	// Calculate the damage percentage
	private void calculateDamagePercentage() {
		switch (fVandalismType) {
		case Arbitrary:
			fDamagePercentage = (Math.random() * (fMaxDamage - fMinDamage) + fMinDamage) / 100;
			break;
		case Fixed:
			fDamagePercentage = (fMaxDamage + fMinDamage) / 2 / 100;
			break;
		case None:
			fDamagePercentage = 0.005;
			break;

		default:
			break;
		}
	}

	@Override
	public double getDamagePercentage() {
		return fDamagePercentage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer name: ").append(fName)
				.append("\nVandalism Type: ").append(fVandalismType)
				.append("\nMinDamage: ").append(fMinDamage)
				.append("\nMaxDamage: ").append(fMaxDamage);
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Customer)
			return ((CustomerImpl) other).fName.equals(fName) & ((CustomerImpl) other).fVandalismType.equals(fVandalismType) &
					((CustomerImpl) other).fMinDamage== fMinDamage & ((CustomerImpl) other).fMaxDamage== fMaxDamage;
		return false;
	}
	

}
