package implement;

import interfaces.Asset;
import interfaces.CustomerGroupDetails;
import interfaces.Managment;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import consts.VandalismType;

public class Driver {

	public static void main(String[] args) {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		Managment managment = createManagment("InitialData.xml", logger,
				builderFactory);

		readXmlInitialData("InitialData.xml", logger, builderFactory, managment);
		readXmlAssetsContentRepairDetails("AssetContentsRepairDetails.xml",
				logger, builderFactory, managment);
		readXmlAssets("Assets.xml", logger, builderFactory, managment);
		readXmlCustomerGroup("CustomersGroups.xml", logger, builderFactory,
				managment);
		
		
//		Managment managment = createManagment(args[0], logger,
//				builderFactory);
//
//		readXmlInitialData(args[0], logger, builderFactory, managment);
//		readXmlAssetsContentRepairDetails(args[1],
//				logger, builderFactory, managment);
//		readXmlAssets(args[2], logger, builderFactory, managment);
//		readXmlCustomerGroup(args[3], logger, builderFactory,
//				managment);

		System.out.println("Driver Working");
		managment.start();
		System.out.println("Driver Done");

	}

	// ***************** Read initial data ****************
	private static Managment createManagment(String fileLocation,
			Logger logger, DocumentBuilderFactory builderFactory) {
		Managment managment = null;
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			int numberOfMaintenancePersons = Integer.parseInt(document
					.getElementsByTagName("NumberOfMaintenancePersons").item(0)
					.getTextContent());

			int totalNumberOfRentalRequests = Integer.parseInt(document
					.getElementsByTagName("TotalNumberOfRentalRequests")
					.item(0).getTextContent());

			managment = new ManagmentImpl(totalNumberOfRentalRequests,
					numberOfMaintenancePersons);
			logger.log(Level.FINE, "Managment created");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return managment;
	}

	private static void readXmlInitialData(String fileLocation, Logger logger,
			DocumentBuilderFactory builderFactory, Managment managment) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

			readClerks(document.getElementsByTagName("Clerk"), managment);
			readTools(document.getElementsByTagName("Tool"), managment);
			readMaterials(document.getElementsByTagName("Material"), managment);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readTools(NodeList tools, Managment managment) {

		for (int i = 0; i < tools.getLength(); i++) {
			Node node = tools.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				managment.addItemRepairTool(new RepairToolImpl(name, quantity));
			}
		}
	}

	private static void readMaterials(NodeList materials, Managment managment) {
		for (int i = 0; i < materials.getLength(); i++) {
			Node node = materials.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				managment.addItemRepairMaterial(new RepairMaterialImpl(name,
						quantity));
			}
		}
	}

	private static void readClerks(NodeList clerks, Managment managment) {
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

				managment.addClerk(new ClerkDetailsImpl(name, location));
			}
		}
	}

	// ******** Read assets content repair details ********
	public static void readXmlAssetsContentRepairDetails(String fileLocation,
			Logger logger, DocumentBuilderFactory builderFactory,
			Managment managment) {
		logger.log(
				Level.FINEST,
				new StringBuilder()
						.append("Reading asset content repair detials from \"")
						.append(fileLocation).append("\"").toString());
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
					logger.log(
							Level.FINEST,
							new StringBuilder()
									.append("Current asset content ")
									.append(assetContentName).toString());
					readToolsInformation(assetContentName,
							assetContentElement.getElementsByTagName("Tool"),
							logger, managment);
					readMaterialsInformation(assetContentName,
							assetContentElement
									.getElementsByTagName("Material"), logger,
							managment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readToolsInformation(String assetContentName,
			NodeList tools, Logger logger, Managment managment) {
		for (int i = 0; i < tools.getLength(); i++) {
			Node node = tools.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				managment.addRepairToolInformation(assetContentName,
						new RepairToolInformationImpl(name, quantity));
				logger.log(
						Level.FINEST,
						new StringBuilder().append(assetContentName)
								.append(" needs ").append(quantity).append(" ")
								.append(name).append("s").toString());
			}
		}
	}

	public static void readMaterialsInformation(String assetContentName,
			NodeList materials, Logger logger, Managment managment) {
		for (int i = 0; i < materials.getLength(); i++) {
			Node node = materials.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				int quantity = Integer.parseInt(element
						.getElementsByTagName("Quantity").item(0)
						.getTextContent());
				managment.addRepairMaterialInformation(assetContentName,
						new RepairMaterialInformationImpl(name, quantity));
				logger.log(
						Level.FINEST,
						new StringBuilder().append(assetContentName)
								.append(" needs ").append(quantity).append(" ")
								.append(name).append("s").toString());
			}
		}
	}

	// **************** Read assets details ***************
	public static void readXmlAssets(String fileLocation, Logger logger,
			DocumentBuilderFactory builderFactory, Managment managment) {
		logger.log(
				Level.FINEST,
				new StringBuilder()
						.append("Reading asset content repair detials from \"")
						.append(fileLocation).append("\"").toString());
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
							costPerNight, size);
					readAssetContent(asset,
							assetElement.getElementsByTagName("AssetContent"),
							logger);
					managment.addAsset(asset);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readAssetContent(Asset asset, NodeList assetContent,
			Logger logger) {
		for (int i = 0; i < assetContent.getLength(); i++) {
			Node node = assetContent.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String name = element.getElementsByTagName("Name").item(0)
						.getTextContent();
				double repairCostMultiplier = Double.parseDouble(element
						.getElementsByTagName("RepairMultiplier").item(0)
						.getTextContent());
				asset.addAssetContent(name, repairCostMultiplier);
				logger.log(
						Level.FINEST,
						new StringBuilder().append("Adding asset content ")
								.append(name)
								.append(" repair cost multiplier ")
								.append(repairCostMultiplier).toString());
			}
		}
	}

	// **************** Read customer group ***************
	public static void readXmlCustomerGroup(String fileLocation, Logger logger,
			DocumentBuilderFactory builderFactory, Managment managment) {
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
					managment.addCustomerGroup(customerGroupDetails);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readCustomers(
			CustomerGroupDetails customerGroupDetails, NodeList customers) {

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

	private static void readRentalRequests(
			CustomerGroupDetails customerGroupDetails, NodeList rentalRequests) {

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

}
