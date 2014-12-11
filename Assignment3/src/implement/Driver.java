package implement;

import java.io.File;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Driver {

	DocumentBuilderFactory builderFactory;

	public Driver(String initialDataSource, String customersGroupsSource,
			String assetsSource, String assetContentRepairDetailsSource) {
		builderFactory = DocumentBuilderFactory.newInstance();

//		readXmlAssetsContentRepairDetails(assetContentRepairDetailsSource);
		readXmlAssets(assetsSource);

	}

	public static void main(String[] args) {
		Driver driver = new Driver("src1", "src1", "Assets.xml",
				"AssetContentsRepairDetails.xml");
		System.out.println("Driver Working");

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

					System.out.println("Name : "
							+ assetContentElement.getElementsByTagName("Name")
									.item(0).getTextContent());

					readTools(assetContentElement);
					readMaterials(assetContentElement);

				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readTools(Element tools) {

		NodeList toolsList = tools.getElementsByTagName("Tool");

		for (int i = 0; i < toolsList.getLength(); i++) {
			Node node = toolsList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.print(element.getElementsByTagName("Name").item(0)
						.getTextContent());
				System.out.println(element.getElementsByTagName("Quantity")
						.item(0).getTextContent());

			}
		}
	}

	public void readMaterials(Element materials) {

		NodeList toolsList = materials.getElementsByTagName("Material");

		for (int i = 0; i < toolsList.getLength(); i++) {
			Node node = toolsList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.print(element.getElementsByTagName("Name").item(0)
						.getTextContent());
				System.out.println(element.getElementsByTagName("Quantity")
						.item(0).getTextContent());

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

			NodeList assetContentList = document
					.getElementsByTagName("Asset");

			for (int i = 0; i < assetContentList.getLength(); i++) {

				Node nNode = assetContentList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element assetContentElement = (Element) nNode;

					System.out.println("Name : "
							+ assetContentElement.getElementsByTagName("Name")
									.item(0).getTextContent());
					System.out.println("Type : "
							+ assetContentElement.getElementsByTagName("Type")
									.item(0).getTextContent());
					System.out.println("Size : "
							+ assetContentElement.getElementsByTagName("Size")
									.item(0).getTextContent());
					System.out.println("Location : "
							+ assetContentElement.getElementsByTagName("Location")
									.item(0).getTextContent());


				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReadXmlCustomerGroup() {

	}

	public void ReadXmlInitialData() {

	}
}
