package implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.plaf.SliderUI;

import runnable.CustomerClerkMessenger;
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

	public ManagmentImpl() {
		this.fClerksDetails = new Vector<ClerkDetails>();
		this.fCustomerGroupDetails = new Vector<CustomerGroupDetails>();
		this.fDamageReports = new Vector<DamageReport>();
		this.fAssets = new AssetsImpl();
		this.fWarehouse = new WarehouseImpl();
		this.fRepairToolInformations = new ConcurrentHashMap<String, List<RepairToolInformation>>();
		this.fRepairMaterialInformations = new ConcurrentHashMap<String, List<RepairMaterialInformation>>();
		this.fStatistics = new StatisticsImpl();
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

	@Override
	public synchronized void submitRentalRequest(RentalRequest rentalRequest) {
		this.fRentalRequests.add(rentalRequest);
	}

	@Override
	public void start() {

		fRentalRequests = new ArrayBlockingQueue<RentalRequest>(
				fCustomerGroupDetails.size());
		CustomerClerkMessenger messenger = new CustomerClerkMessenger();

		final AtomicInteger numberOfRentalRequests = countTotalRentalRequests();
		CyclicBarrier cyclicBarrierShift = new CyclicBarrier(
				fClerksDetails.size() + 1);

		createRunnableCustomerGroups(messenger);

		ArrayList<Thread> clerks = createRunnableClerks(numberOfRentalRequests,
				cyclicBarrierShift, messenger);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("********"
							+ numberOfRentalRequests.get());
				}
			}
		}).start();
		;

		while (numberOfRentalRequests.get() > 0) {
			waitForClerksToFinishShift(cyclicBarrierShift);
			ArrayList<Thread> maintenance = createRunnableMentenance();
			waitForMentenanceToFinish(clerks, maintenance);
			cyclicBarrierShift.reset();
		}
		
		System.out.println("DONE");

	}

	private ArrayList<Thread> createRunnableMentenance() {
		ArrayList<Thread> maintenance = new ArrayList<Thread>();
		for (DamageReport damageReport : fDamageReports) {
			Thread thread = new Thread(new RunnableMaintenaceRequest(
					fRepairToolInformations, fRepairMaterialInformations,
					damageReport.getAsset(), fWarehouse, fStatistics));
			thread.start();
			maintenance.add(thread);
		}
		return maintenance;
	}

	private void waitForMentenanceToFinish(ArrayList<Thread> clerks,
			ArrayList<Thread> maintenance) {
		int sumOfMaintenanceDone = 0;
		while (sumOfMaintenanceDone < maintenance.size()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sumOfMaintenanceDone = 0;
			for (Thread thread : clerks) {
				if (thread.isAlive()) {
					sumOfMaintenanceDone++;
				}
			}
		}
	}

	private void createRunnableCustomerGroups(CustomerClerkMessenger messenger) {
		ArrayList<Thread> customerGroups = new ArrayList<Thread>();
		CyclicBarrier cyclicBarrierCustomerGroup = new CyclicBarrier(
				fCustomerGroupDetails.size());
		for (CustomerGroupDetails customerGroupDetails : fCustomerGroupDetails) {
			Thread thread = new Thread(new RunnableCustomerGroupManager(
					customerGroupDetails, this, fStatistics,
					cyclicBarrierCustomerGroup, messenger));
			thread.start();
			customerGroups.add(thread);
		}
	}

	private void waitForClerksToFinishShift(CyclicBarrier cyclicBarrierShift) {
		while (cyclicBarrierShift.getNumberWaiting() < fClerksDetails.size()) {
			try {
				// System.out.println(cyclicBarrierShift.getNumberWaiting());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<Thread> createRunnableClerks(
			AtomicInteger numberOfRentalRequests,
			CyclicBarrier cyclicBarrierShift, CustomerClerkMessenger messenger) {
		ArrayList<Thread> clerks = new ArrayList<Thread>();
		CyclicBarrier cyclicBarrierClerks = new CyclicBarrier(
				fClerksDetails.size());

		for (ClerkDetails clerkDetails : fClerksDetails) {
			Thread thread = new Thread(new RunnebleClerk(clerkDetails,
					fRentalRequests, numberOfRentalRequests, fAssets,
					cyclicBarrierClerks, cyclicBarrierShift, messenger));
			thread.start();
			clerks.add(thread);
		}
		return clerks;
	}

	private AtomicInteger countTotalRentalRequests() {
		AtomicInteger numberOfRentalRequests = new AtomicInteger(0);
		for (CustomerGroupDetails customerGroupDetails : fCustomerGroupDetails) {
			numberOfRentalRequests.addAndGet(customerGroupDetails
					.getNumberOfRentalRequests());
		}
		return numberOfRentalRequests;
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
