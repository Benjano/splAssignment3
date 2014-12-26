package implement;

import interfaces.Asset;
import interfaces.DamageReport;

public class DamageReportImpl implements DamageReport {

	private Asset fAsset;
	private double fDamagePrecentage;

	public DamageReportImpl(Asset asset, double damagePercentage) {
		this.fAsset = asset;
		this.fDamagePrecentage = damagePercentage;
	}

	@Override
	public Asset getAsset() {
		return fAsset;
	}

	@Override
	public double getDamagePercentage() {
		return fDamagePrecentage;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof DamageReport)
			return ((DamageReport) other).getAsset().equals(this.fAsset);
		return false;
	}

}
