package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.AssetStatus;
import consts.RequestStatus;
import implement.AssetImpl;
import implement.Location;
import interfaces.Asset;
import interfaces.DamageReport;

public class RentalRequestTesting {

	private RentalRequestTest rentalRequest;
	private final double DELTA = 0.001;
	private Asset asset;

	@Before
	public void setUp() throws Exception {
		rentalRequest = new RentalRequestTest("1", "Hut", 5, 5);
		asset = new AssetImpl("Aviv's House", "Appartment",
				new Location(10, 15), 100, 2);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getDurationOfStay() {
		assertEquals("The duration is wrong", 5,
				rentalRequest.getDurationOfStay());
	}

	@Test
	public void getAssetType() {
		assertEquals("The type is wrong", "Hut", rentalRequest.getAssetType());
	}

	@Test
	public void getSize() {
		assertEquals("The size is wrong", 5, rentalRequest.getSize());

	}

	@Test
	public void getCostPerNight() {
		assertEquals("The cost per night is wrong", 0,
				rentalRequest.getCostPerNight(), DELTA);
	}

	@Test
	public void getID() {
		assertEquals("The id is wrong", 5, rentalRequest.getSize());

	}

	@Test
	public void getStatus() {
		assertEquals("The status is wrong", RequestStatus.Incomplete,
				rentalRequest.getStatus());
	}

	@Test
	public void setRentalRequestStatus() {
		rentalRequest.setRentalRequestStatus(RequestStatus.InProgress);
		assertEquals("Set rental status is wrong", RequestStatus.InProgress,
				rentalRequest.getStatus());
	}

	@Test
	public void assetOcupied() {
		rentalRequest.setFoundAsset(asset);
		rentalRequest.assetOcupied();
		assertEquals("Asset was not ocupied", AssetStatus.Occupied,
				asset.getStatus());

	}

	@Test
	public void setFoundAsset() {
		rentalRequest.setFoundAsset(asset);
		Asset asset2 = new AssetImpl("Asset 2", "House", new Location(1, 1),
				100, 3);
		rentalRequest.setFoundAsset(asset2);

		assertEquals("Found asset is wrong", rentalRequest.getAsset(), asset);

	}

	@Test
	public void releaseAsset() {
		rentalRequest.setFoundAsset(asset);
		rentalRequest.setRentalRequestStatus(RequestStatus.InProgress);
		DamageReport damageReport = rentalRequest.releaseAsset(0.5);

		assertEquals("Rental was not complete", rentalRequest.getStatus(),
				RequestStatus.Complete);
		assertEquals("Damage report created wrong", damageReport.getAsset(),
				asset);
	}

}
