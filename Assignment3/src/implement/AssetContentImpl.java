package implement;

import java.util.concurrent.atomic.AtomicReference;

import interfaces.AssetContent;

public class AssetContentImpl implements AssetContent {
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
		fHealth.set(fHealth.get() * damagePrecentage);
	}

	@Override
	public void fixAssetContent() {
		fHealth.set(100d);
	}

}
