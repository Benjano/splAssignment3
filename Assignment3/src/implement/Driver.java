package implement;

import interfaces.Asset;
import interfaces.CustomerGroupDetails;
import interfaces.Managment;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import consts.AssetStatus;
import consts.VandalismType;

public class Driver {

	private DocumentBuilderFactory builderFactory;
	private static Managment fManagment;
	private Logger fLogger;
	
	public Driver(String initialDataSource, String customersGroupsSource,
			String assetsSource, String assetContentRepairDetailsSource) {
		fLogger = Logger.getLogger(this.getClass().getSimpleName());
		fManagment = new ManagmentImpl();
		builderFactory = DocumentBuilderFactory.newInstance();

		readXmlAssetsContentRepairDetails(assetContentRepairDetailsSource);
		readXmlAssets(assetsSource);
		readXmlCustomerGroup(customersGroupsSource);
		readXmlInitialData(initialDataSource);

	}

	public static void main(String[] args) {
		new Driver("InitialData.xml", "CustomersGroups.xml", "Assets.xml",
				"AssetContentsRepairDetails.xml");
		System.out.println("Driver Working");
//		System.out.println(fManagment);
		fManagment.start();
		System.out.println("Driver Done");

	}

	// ******** Read assets content repair details ********
	public void readXmlAssetsContentRepairDetails(String fileLocation) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			NodeList assetContentList = document
					.getElementsByTagName("AssetContent");

			for (int i = 0; i < assetContentList.getLength(); i++) {

				Node nNode = assetContentList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element assetContentElement = (Element) nNode;

					String assetContentName = assetContentElement
							.getElementsByTagName("Name").item(0)
							.getTextContent();

					readToolsInformation(assetContentName,
							assetContentElement.getElementsByTagName("Tool"));
					readMaterialsInformation(assetContentName,
							assetContentElement
									.getElementsByTagName("Material"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readToolsInformation(String assetContentName, NodeList tools) {

		for (int i = 0; i < tools.getLength(); i++) {
			Node node = tools.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				fManagment.addRepairToolInformation(assetContentName,
						new RepairToolInformationImpl(name, quantity));
			}
		}
	}

	public void readMaterialsInformation(String assetContentName,
			NodeList materials) {
		for (int i = 0; i < materials.getLength(); i++) {
			Node node = materials.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				fManagment.addRepairMaterialInformation(assetContentName,
						new RepairMaterialInformationImpl(name, quantity));
			}
		}
	}

	// **************** Read assets details ***************
	public void readXmlAssets(String fileLocation) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			NodeList assetContentList = document.getElementsByTagName("Asset");

			for (int i = 0; i < assetContentList.getLength(); i++) {

				Node nNode = assetContentList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element assetElement = (Element) nNode;

					String name = assetElement.getElementsByTagName("Name")
							.item(0).getTextContent();
					String type = assetElement.getElementsByTagName("Type")
							.item(0).getTextContent();
					int size = Integer.parseInt(assetElement
							.getElementsByTagName("Size").item(0)
							.getTextContent());
					int x = Integer
							.parseInt(assetElement
									.getElementsByTagName("Location").item(0)
									.getAttributes().getNamedItem("x")
									.getTextContent());
					int y = Integer
							.parseInt(assetElement
									.getElementsByTagName("Location").item(0)
									.getAttributes().getNamedItem("y")
									.getTextContent());
					Location location = new Location(x, y);
					int costPerNight = Integer.parseInt(assetElement
							.getElementsByTagName("CostPerNight").item(0)
							.getTextContent());

					Asset asset = new AssetImpl(name, type, location,
							AssetStatus.Available, costPerNight, size);

					readAssetContent(asset,
							assetElement.getElementsByTagName("AssetContent"));

					fManagment.addAsset(asset);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readAssetContent(Asset asset, NodeList assetContent) {
		for (int i = 0; i < assetContent.getLength(); i++) {
			Node node = assetContent.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				double repairCostMultiplier = Double.parseDouble(element
						.getElementsByTagName("RepairMultiplier").item(0)
						.getTextContent());

				asset.addAssetContent(new AssetContentImpl(name,
						repairCostMultiplier));

				asset.addAssetContent(new AssetContentImpl(name,
						repairCostMultiplier));
			}
		}
	}

	// **************** Read customer group ***************
	public void readXmlCustomerGroup(String fileLocation) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			NodeList customerGroupDetailsList = document
					.getElementsByTagName("CustomerGroupDetails");

			for (int i = 0; i < customerGroupDetailsList.getLength(); i++) {

				Node nNode = customerGroupDetailsList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element customerGroupDetailsElement = (Element) nNode;

					String name = customerGroupDetailsElement
							.getElementsByTagName("GroupManagerName").item(0)
							.getTextContent();

					CustomerGroupDetails customerGroupDetails = new CustomerGroupDetailsImpl(
							name);

					readCustomers(customerGroupDetails,
							customerGroupDetailsElement
									.getElementsByTagName("Customer"));
					readRentalRequests(customerGroupDetails,
							customerGroupDetailsElement
									.getElementsByTagName("Request"));
					fManagment.addCustomerGroup(customerGroupDetails);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readCustomers(CustomerGroupDetails customerGroupDetails,
			NodeList customers) {

		for (int i = 0; i < customers.getLength(); i++) {
			Node node = customers.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String customerName = element.getElementsByTagName("Name")
						.item(0).getTextContent();
				VandalismType vandalismType = VandalismType.valueOf(element
						.getElementsByTagName("Vandalism").item(0)
						.getTextContent());
				double minDamage = Double.parseDouble(element
						.getElementsByTagName("MinimumDamage").item(0)
						.getTextContent());
				double maxDamage = Double.parseDouble(element
						.getElementsByTagName("MaximumDamage").item(0)
						.getTextContent());

				customerGroupDetails.addCustomer(new CustomerImpl(customerName,
						vandalismType, minDamage, maxDamage));

			}
		}
	}

	private void readRentalRequests(CustomerGroupDetails customerGroupDetails,
			NodeList rentalRequests) {

		for (int i = 0; i < rentalRequests.getLength(); i++) {
			Node node = rentalRequests.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String id = element.getAttribute("id");
				String type = element.getElementsByTagName("Type").item(0)
						.getTextContent();

				int size = Integer.parseInt(element
						.getElementsByTagName("Size").item(0).getTextContent());
				int duration = Integer.parseInt(element
						.getElementsByTagName("Duration").item(0)
						.getTextContent());

				customerGroupDetails.addRentalRequest(new RentalRequestImpl(id,
						type, size, duration));

			}
		}
	}

	// ***************** Read initial data ****************
	public void readXmlInitialData(String fileLocation) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			readClerks(document.getElementsByTagName("Clerk"));
			readTools(document.getElementsByTagName("Tool"));
			readMaterials(document.getElementsByTagName("Material"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readTools(NodeList tools) {

		for (int i = 0; i < tools.getLength(); i++) {
			Node node = tools.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				fManagment
						.addItemRepairTool(new RepairToolImpl(name, quantity));
			}
		}
	}

	public void readMaterials(NodeList materials) {
		for (int i = 0; i < materials.getLength(); i++) {
			Node node = materials.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				fManagment.addItemRepairMaterial(new RepairMaterialImpl(name,
						quantity));
			}
		}
	}

	private void readClerks(NodeList clerks) throws Exception {
		for (int i = 0; i < clerks.getLength(); i++) {
			Node node = clerks.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();

				int x = Integer.parseInt(element
						.getElementsByTagName("Location").item(0)
						.getAttributes().getNamedItem("x").getTextContent());
				int y = Integer.parseInt(element
						.getElementsByTagName("Location").item(0)
						.getAttributes().getNamedItem("y").getTextContent());
				Location location = new Location(x, y);

				fManagment.addClerk(new ClerkDetailsImpl(name, location));
			}
		}
	}
}
