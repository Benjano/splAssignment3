package implement;

import interfaces.Managment;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Driver {

	private DocumentBuilderFactory builderFactory;
	private static Managment fManagment;

	public Driver(String initialDataSource, String customersGroupsSource,
			String assetsSource, String assetContentRepairDetailsSource) {

		fManagment = new ManagmentImpl();
		builderFactory = DocumentBuilderFactory.newInstance();

		readXmlAssetsContentRepairDetails(assetContentRepairDetailsSource);
		// readXmlAssets(assetsSource);
		// readXmlCustomerGroup(customersGroupsSource);
		// readXmlInitialData(initialDataSource);

	}

	public static void main(String[] args) {
		Driver driver = new Driver("InitialData.xml", "CustomersGroups.xml",
				"Assets.xml", "AssetContentsRepairDetails.xml");
		System.out.println("Driver Working");
		
		System.out.println(fManagment);

	}

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
	
	public void readMaterialsInformation(String assetContentName, NodeList materials) {
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

					System.out.println("Name : "
							+ assetElement.getElementsByTagName("Name").item(0)
									.getTextContent());
					System.out.println("Type : "
							+ assetElement.getElementsByTagName("Type").item(0)
									.getTextContent());
					System.out.println("Size : "
							+ assetElement.getElementsByTagName("Size").item(0)
									.getTextContent());
					System.out.println("Location : "
							+ assetElement.getElementsByTagName("Location")
									.item(0).getAttributes().getNamedItem("x")
									.getTextContent()
							+ ","
							+ assetElement.getElementsByTagName("Location")
									.item(0).getAttributes().getNamedItem("y")
									.getTextContent());
					System.out.println("CostPerNight : "
							+ assetElement.getElementsByTagName("CostPerNight")
									.item(0).getTextContent());

					readAssetContent(assetElement
							.getElementsByTagName("AssetContent"));

				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readAssetContent(NodeList assetContent) {
		for (int i = 0; i < assetContent.getLength(); i++) {
			Node node = assetContent.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.print(element.getElementsByTagName("Name").item(0)
						.getTextContent());
				System.out.println(element
						.getElementsByTagName("RepairMultiplier").item(0)
						.getTextContent());

			}
		}
	}

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

	public void readXmlInitialData(String fileLocation) {
		try {
			File xmlFile = new File(fileLocation);
			DocumentBuilder documentBuilder = builderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile);

			document.getDocumentElement().normalize();

//			readTools(document.getElementsByTagName("Tool"));
//			readMaterials(document.getElementsByTagName("Material"));
			readClerks(document.getElementsByTagName("Clerk"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readTools(String assetContentName, NodeList tools) {

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

	public void readMaterials(String assetContentName, NodeList materials) {
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
	
	private void readClerks(NodeList clerks) throws Exception {
		for (int i = 0; i < clerks.getLength(); i++) {
			Node node = clerks.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println(element.getElementsByTagName("Name").item(0)
						.getTextContent());
				System.out.println("Location : "
						+ element.getElementsByTagName("Location").item(0)
								.getAttributes().getNamedItem("x")
								.getTextContent()
						+ ","
						+ element.getElementsByTagName("Location").item(0)
								.getAttributes().getNamedItem("y")
								.getTextContent());
			}
		}
	}
}
