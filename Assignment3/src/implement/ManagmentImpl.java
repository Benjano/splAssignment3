package implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import runnable.Messenger;
import runnable.RunnableCustomerGroupManager;
import runnable.RunnableMaintenaceRequest;
import runnable.RunnebleClerk;
import interfaces.Asset;
import interfaces.Assets;
import interfaces.ClerkDetails;
import interfaces.CustomerGroupDetails;
import interfaces.DamageReport;
import interfaces.Managment;
import interfaces.RentalRequest;
import interfaces.RepairMaterial;
import interfaces.RepairMaterialInformation;
import interfaces.RepairTool;
import interfaces.RepairToolInformation;
import interfaces.Statistics;
import interfaces.Warehouse;

public class ManagmentImpl implements Managment {

	private List<ClerkDetails> fClerksDetails;
	private List<CustomerGroupDetails> fCustomerGroupDetails;
	private List<DamageReport> fDamageReports;
	private BlockingQueue<RentalRequest> fRentalRequests;
	private Assets fAssets;
	private Warehouse fWarehouse;
	private Map<String, List<RepairToolInformation>> fRepairToolInformations;
	private Map<String, List<RepairMaterialInformation>> fRepairMaterialInformations;
	private Statistics fStatistics;
	private Logger fLogger;
	private AtomicInteger fNumberOfRentalRequests;
	private int fNumberOfMaintenancePersons;

	public ManagmentImpl(int numberOfRentalRequests, int numberOfMententance) {
		this.fClerksDetails = new Vector<ClerkDetails>();
		this.fCustomerGroupDetails = new Vector<CustomerGroupDetails>();
		this.fDamageReports = new Vector<DamageReport>();
		this.fAssets = new AssetsImpl();
		this.fWarehouse = new WarehouseImpl();
		this.fRepairToolInformations = new ConcurrentHashMap<String, List<RepairToolInformation>>();
		this.fRepairMaterialInformations = new ConcurrentHashMap<String, List<RepairMaterialInformation>>();
		this.fStatistics = new StatisticsImpl();
		this.fNumberOfRentalRequests = new AtomicInteger(numberOfRentalRequests);
		this.fNumberOfMaintenancePersons = numberOfMententance;
		this.fLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public void addClerk(ClerkDetails clerkDetails) {
		fClerksDetails.add(clerkDetails);
	}

	@Override
	public void addCustomerGroup(CustomerGroupDetails customerGroupDetails) {
		fCustomerGroupDetails.add(customerGroupDetails);
	}

	@Override
	public void addItemRepairTool(RepairTool repairTool) {
		fWarehouse.addTool(repairTool);
	}

	@Override
	public void addItemRepairMaterial(RepairMaterial repairMaterial) {
		fWarehouse.addMaterial(repairMaterial);
	}

	@Override
	public void addAsset(Asset asset) {
		fAssets.addAsset(asset);
	}

	@Override
	public void addRepairToolInformation(String assetContentName,
			RepairToolInformation repairToolInformation) {

		if (fRepairToolInformations.containsKey(assetContentName)) {
			fRepairToolInformations.get(assetContentName).add(
					repairToolInformation);
		} else {
			fRepairToolInformations.put(assetContentName,
					new Vector<RepairToolInformation>());
			fRepairToolInformations.get(assetContentName).add(
					repairToolInformation);
		}
	}

	@Override
	public void addRepairMaterialInformation(String assetContentName,
			RepairMaterialInformation materialInformation) {

		if (fRepairMaterialInformations.containsKey(assetContentName)) {
			fRepairMaterialInformations.get(assetContentName).add(
					materialInformation);
		} else {
			fRepairMaterialInformations.put(assetContentName,
					new Vector<RepairMaterialInformation>());
			fRepairMaterialInformations.get(assetContentName).add(
					materialInformation);
		}
	}

	@Override
	public synchronized void submitDamageReport(DamageReport damageReport) {
		this.fDamageReports.add(damageReport);
	}

	private DamageReport takeDamageReport() {
		if (fDamageReports.size() != 0) {
			DamageReport result = fDamageReports.get(0);
			fDamageReports.remove(0);
			return result;
		}
		return null;
	}

	@Override
	public synchronized void submitRentalRequest(RentalRequest rentalRequest) {
		this.fRentalRequests.add(rentalRequest);
	}

	@Override
	public void start() {

		fLogger.log(Level.FINE, "Simulation Started");

		fRentalRequests = new ArrayBlockingQueue<RentalRequest>(
				fCustomerGroupDetails.size());

		fLogger.log(Level.FINE,
				new StringBuilder().append("Rental requests queue size is : ")
						.append(fCustomerGroupDetails.size()).toString());

		Messenger messengerClerkCustomerGroup = new Messenger();
		Messenger messengerShift = new Messenger();
		Messenger messengerMentenance = new Messenger();

		CountDownLatch clerkLatch = new CountDownLatch(1);
		CyclicBarrier cyclicBarrierShift = new CyclicBarrier(
				fClerksDetails.size() + 1);

		createRunnableCustomerGroups(messengerClerkCustomerGroup);
		createRunnableClerks(clerkLatch, fNumberOfRentalRequests,
				cyclicBarrierShift, messengerClerkCustomerGroup, messengerShift);

		// Make sure all clerks start together
		clerkLatch.countDown();
		while (fNumberOfRentalRequests.get() > 0) {

			fLogger.log(
					Level.FINE,
					new StringBuilder().append(
							"All clerks are starting a new shift").toString());

			try {
				cyclicBarrierShift.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

			fLogger.log(
					Level.FINE,
					new StringBuilder().append(
							"All clerks finished their shift").toString());

			// Repair all damaged assets and wait for maintenance guys to finish
			repairDamagedAssets(messengerMentenance);

			fLogger.log(Level.FINE,
					new StringBuilder().append("All assets been fixed")
							.toString());

			synchronized (messengerShift) {
				messengerShift.notifyAll();
			}
		}

		fLogger.log(Level.FINE, "Simulation Ended");

	}

	private void repairDamagedAssets(Messenger messengerMentenance) {
		DamageReport damageReport = takeDamageReport();
		AtomicInteger workingMaintenance = new AtomicInteger(
				fNumberOfMaintenancePersons);
		while (damageReport != null) {
			if (damageReport.getAsset().isDamaged()) {
				new Thread(new RunnableMaintenaceRequest(
						fRepairToolInformations, fRepairMaterialInformations,
						damageReport.getAsset(), fWarehouse, fStatistics,
						messengerMentenance)).start();
			}
			if (workingMaintenance.get() == 0) {
				synchronized (messengerMentenance) {
					try {
						messengerMentenance.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					workingMaintenance.incrementAndGet();
				}
			}
			damageReport = takeDamageReport();
		}

		while (workingMaintenance.get() != fNumberOfMaintenancePersons) {
			synchronized (messengerMentenance) {
				try {
					messengerMentenance.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				workingMaintenance.incrementAndGet();
			}
		}
	}

	private void createRunnableCustomerGroups(Messenger messenger) {
		CyclicBarrier cyclicBarrierCustomerGroup = new CyclicBarrier(
				fCustomerGroupDetails.size());
		for (CustomerGroupDetails customerGroupDetails : fCustomerGroupDetails) {
			new Thread(new RunnableCustomerGroupManager(customerGroupDetails,
					this, fStatistics, cyclicBarrierCustomerGroup, messenger))
					.start();
		}
	}

	private void createRunnableClerks(CountDownLatch clerkLatch,
			AtomicInteger numberOfRentalRequests,
			CyclicBarrier cyclicBarrierShift,
			Messenger messengerClerkCustomerGroup,
			Messenger messengerClerkMentenance) {

		for (ClerkDetails clerkDetails : fClerksDetails) {
			new Thread(new RunnebleClerk(clerkDetails, fRentalRequests,
					numberOfRentalRequests, fAssets, clerkLatch,
					cyclicBarrierShift, messengerClerkCustomerGroup,
					messengerClerkMentenance)).start();
		}
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("Clerk Details: ");

		for (ClerkDetails clerkDetails : fClerksDetails) {
			builder.append("\n").append(clerkDetails);
		}

		builder.append("\n\nCustomer Group Details: ");

		for (CustomerGroupDetails customerGroupDetails : fCustomerGroupDetails) {
			builder.append("\n").append(customerGroupDetails);
		}

		builder.append("\n\nAssets: ");
		builder.append(fAssets);

		builder.append("\n\nWarehouse: ");
		builder.append(fWarehouse);

		builder.append("\n\nRepair Tool Informations: ");

		for (Map.Entry<String, List<RepairToolInformation>> repairToolInformations : fRepairToolInformations
				.entrySet()) {
			builder.append("\n").append(repairToolInformations.getKey())
					.append(": ").append(repairToolInformations.getValue());
		}

		builder.append("\n\nRepair Material Informations: ");

		for (Map.Entry<String, List<RepairMaterialInformation>> repairMaterialInformations : fRepairMaterialInformations
				.entrySet()) {
			builder.append("\n").append(repairMaterialInformations.getKey())
					.append(": ").append(repairMaterialInformations.getValue());
		}

		return builder.toString();
	}
}
