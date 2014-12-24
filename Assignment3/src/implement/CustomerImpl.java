package implement;

import java.util.logging.Logger;
import consts.VandalismType;
import interfaces.Customer;

public class CustomerImpl implements Customer {
	private String fName;
	private VandalismType fVandalismType;
	private double fMinDamage, fMaxDamage;
	private Logger fLogger;

	public CustomerImpl(String name, VandalismType vandalismType,
			double minDamage, double maxDamage) {
		this.fName = name;
		this.fVandalismType = vandalismType;
		this.fMinDamage = minDamage;
		this.fMaxDamage = maxDamage;
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}

	@Override
	public VandalismType getVandalismType() {
		return fVandalismType;
	}

	@Override
	public double calculateDamagePercentage() {
		double percentage = 0;
		switch (fVandalismType) {
		case Arbitrary:
			percentage = (Math.random() * (fMaxDamage - fMinDamage) + fMinDamage) / 100;
			break;
		case Fixed:
			percentage = (fMaxDamage + fMinDamage) / 2 / 100;
			break;
		case None:
			percentage = 0.005;
			break;

		default:
			break;
		}
		return percentage;
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

}
