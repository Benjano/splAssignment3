package implement;

import java.util.concurrent.atomic.AtomicInteger;

import interfaces.AssetContent;

public class AssetContentImpl implements AssetContent {
	private String fName;
	private AtomicInteger fHealth;
	private float fRepairCostMultiplier;

	/**
	 * @param fName
	 * @param fHealth
	 * @param fRepairCostMultiplier
	 */
	public AssetContentImpl(String name, int health, float repairCostMultiplier) {
		super();
		this.fName = name;
		this.fHealth = new AtomicInteger(health);
		this.fRepairCostMultiplier = repairCostMultiplier;
	}

	@Override
	public double calculateRepairTime() {
		return ((100 - fHealth.get()) * fRepairCostMultiplier);
	}

	@Override
	public void setHealth(int health) {
		fHealth.set(health);
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

}
