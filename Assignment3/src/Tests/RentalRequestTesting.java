package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import consts.AssetStatus;
import consts.RequestStatus;
import implement.AssetImpl;
import implement.Location;
import implement.RentalRequestImpl;
import interfaces.Asset;
import interfaces.RentalRequest;

public class RentalRequestTesting {

	private RentalRequest rentalRequest;
	private final double DELTA = 0.001;
	private Asset asset;

	@Before
	public void setUp() throws Exception {
		rentalRequest = new RentalRequestImpl("1", "Hut", 5, 5);
		asset = new AssetImpl("Aviv's House", "Appartment",
				new Location(10, 15), 100, 2);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getDurationOfStay() {
		assertEquals("blabla", 5, rentalRequest.getDurationOfStay());
	}

	@Test
	public void getAssetType() {
		assertEquals("blabla", "Hut", rentalRequest.getAssetType());
	}

	@Test
	public void getSize() {
		assertEquals("blabla", 5, rentalRequest.getSize());

	}

	@Test
	public void getCostPerNight() {
		assertEquals("blabla", 0, rentalRequest.getCostPerNight(), DELTA);
	}

	@Test
	public void getID() {
		assertEquals("blabla", 5, rentalRequest.getSize());

	}

	@Test
	public void getStatus() {
		assertEquals("blabla", RequestStatus.Incomplete,
				rentalRequest.getStatus());
	}

	@Test
	public void setRentalRequestStatus() {
		rentalRequest.setRentalRequestStatus(RequestStatus.InProgress);
		assertEquals("blabla", RequestStatus.InProgress,
				rentalRequest.getStatus());
	}

	@Test
	public void assetOcupied() {
		rentalRequest.setFoundAsset(asset);
		rentalRequest.assetOcupied();
		assertEquals("blabla", AssetStatus.Occupied, asset.getStatus());

	}

	// @Test
	// public void setFoundAsset(){
	// }

	// @Test
	// public void releaseAsset(){
	// }

}
