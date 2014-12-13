package implement;

import consts.VandalismType;
import interfaces.Customer;

public class CustomerImpl implements Customer {
	private String fName;
	private VandalismType fVandalismType;
	private double fMinDamage, fMaxDamage;

	public CustomerImpl(String name, VandalismType vandalismType,
			double minDamage, double maxDamage) {
		this.fName = name;
		this.fVandalismType = vandalismType;
		this.fMinDamage = minDamage;
		this.fMaxDamage = maxDamage;
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public VandalismType getVandalismType() {
		return fVandalismType;
	}

	@Override
	public double calculateDamagePercentage() {
		double percentage = 0;
		switch (fVandalismType) {
		case Arbitary:
			percentage = (Math.random() * (fMaxDamage - fMinDamage) + fMinDamage)/100;
			break;
		case Fixed:
			percentage = (fMaxDamage + fMinDamage) / 2 / 100;
			break;
		case None:
			percentage = 0.05;
			break;

		default:
			break;
		}
		return percentage;
	}
}
