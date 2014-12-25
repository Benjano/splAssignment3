package implement;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaces.AssetContent;

public class AssetContentImpl implements AssetContent {

	private Logger fLogger;
	private String fName;
	private AtomicReference<Double> fHealth;
	private double fRepairCostMultiplier;

	/**
	 * @param fName
	 * @param fHealth
	 * @param fRepairCostMultiplier
	 */
	public AssetContentImpl(String name, double repairCostMultiplier) {
		super();
		this.fName = name;
		this.fHealth = new AtomicReference<Double>(100d);
		this.fRepairCostMultiplier = repairCostMultiplier;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public double getHealth() {
		return fHealth.get();
	}

	@Override
	public String getName() {
		return fName;
	}

	@Override
	public void damageAssetContent(double damagePrecentage) {
		double oldHealth = fHealth.get();
		if (damagePrecentage >= 0) {
			double newHealth = fHealth.get() - fHealth.get() * damagePrecentage
					/ 100;
			if (newHealth < 0) {
				fHealth.set(0d);
			} else {
				fHealth.set(newHealth);
			}
			fLogger.log(Level.FINE,
					new StringBuilder().append("Damageing the asset content ")
							.append(fName).append(" from ").append(oldHealth)
							.append("% to ").append(newHealth).append("%")
							.toString());
		} else {
			fLogger.log(
					Level.WARNING,
					new StringBuilder().append(
							"Cannot damage the asset by nagative number")
							.toString());
		}
	}

	@Override
	public void fixAssetContent() {
		fHealth.set(100d);
		fLogger.log(Level.FINE, new StringBuilder().append("AssetContent ")
				.append(fName).append(" health is 100%").toString());
	}

	@Override
	public double calculateRepairTime() {
		double result = (100 - fHealth.get()) * fRepairCostMultiplier;
		fLogger.log(Level.FINE, "Calculating the repair time for " + fName
				+ " which is " + result);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset content name: ").append(fName)
				.append("\nHealth: ").append(fHealth)
				.append("% \nRepair cost multiplier: ")
				.append(fRepairCostMultiplier);
		return builder.toString();
	}

}
