package implement;

import java.util.concurrent.atomic.AtomicReference;
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
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}

	@Override
	public double calculateRepairTime() {
		return ((100 - fHealth.get()) * fRepairCostMultiplier);
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Asset content name: ").append(fName)
				.append("\nHealth: ").append(fHealth)
				.append("% \nRepair cost multiplier: ")
				.append(fRepairCostMultiplier);
		return builder.toString();
	}

	@Override
	public void damageAssetContent(double damagePrecentage) {
		if (damagePrecentage >= 0) {
			double newHealth = fHealth.get() - fHealth.get() * damagePrecentage/100;
			if (newHealth < 0) {
				fHealth.set(0d);
			} else {
				fHealth.set(newHealth);
			}
		}
	}

	@Override
	public void fixAssetContent() {
		fHealth.set(100d);
	}

}
