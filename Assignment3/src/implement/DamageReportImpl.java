package implement;

import java.util.logging.Logger;

import interfaces.Asset;
import interfaces.DamageReport;

public class DamageReportImpl implements DamageReport {

	private Asset fAsset;
	private double fDamagePrecentage;
	private Logger fLogger;

	public DamageReportImpl(Asset asset, double damagePercentage) {
		this.fAsset = asset;
		this.fDamagePrecentage = damagePercentage;
		this.fLogger = Logger.getLogger(this.getClass().getSimpleName());
	}

	@Override
	public Asset getAsset() {
		return fAsset;
	}

	@Override
	public double getDamagePercentage() {
		return fDamagePrecentage;
	}

}
