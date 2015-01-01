package tests;

import implement.RentalRequestImpl;
import interfaces.Asset;

public class RentalRequestTest extends RentalRequestImpl {

	public RentalRequestTest(String id, String type, int size,
			int durationOfStay) {
		super(id, type, size, durationOfStay);
	}

	public Asset getAsset() {
		return fAssetFound;
	}
}
