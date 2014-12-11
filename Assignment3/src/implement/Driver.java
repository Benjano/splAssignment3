package implement;

import interfaces.Asset;
import interfaces.Managment;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import consts.AssetStatus;

public class Driver {

	private DocumentBuilderFactory builderFactory;
	private static Managment fManagment;

	public Driver(String initialDataSource, String customersGroupsSource,
			String assetsSource, String assetContentRepairDetailsSource) {

		fManagment = new ManagmentImpl();
		builderFactory = DocumentBuilderFactory.newInstance();

		// readXmlAssetsContentRepairDetails(assetContentRepairDetailsSource);
		// readXmlAssets(assetsSource);
		// readXmlCustomerGroup(customersGroupsSource);
		readXmlInitialData(initialDataSource);

	}

	public static void main(String[] args) {
		Driver driver = new Driver("InitialData.xml", "CustomersGroups.xml",
				"Assets.xml", "AssetContentsRepairDetails.xml");
		System.out.println("Driver Working");

		System.out.println(fManagment);

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

					System.out.println("Group Manager Name : "
							+ customerGroupDetailsElement
									.getElementsByTagName("GroupManagerName")
									.item(0).getTextContent());

					readCustomers(customerGroupDetailsElement
							.getElementsByTagName("Customer"));

				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readCustomers(NodeList customers) {

		for (int i = 0; i < customers.getLength(); i++) {
			Node node = customers.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println("Customer name : "
						+ element.getElementsByTagName("Name").item(0)
								.getTextContent());
				System.out.println("Vandalism type : "
						+ element.getElementsByTagName("Vandalism").item(0)
								.getTextContent());
				System.out.println("Min damage : "
						+ element.getElementsByTagName("MinimumDamage").item(0)
								.getTextContent());
				System.out.println("Max damage : "
						+ element.getElementsByTagName("MaximumDamage").item(0)
								.getTextContent());

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
