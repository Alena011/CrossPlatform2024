package sixth.third.ex5;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class BestNamesReader {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("best_names.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList nameList = doc.getElementsByTagName("child");

            for (int i = 0; i < nameList.getLength(); i++) {
                Node nameNode = nameList.item(i);
                if (nameNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nameElement = (Element) nameNode;
                    String name = getTextContent(nameElement, "name");
                    String gender = getTextContent(nameElement, "gender");
                    int count = Integer.parseInt(getTextContent(nameElement, "count"));
                    int rank = Integer.parseInt(getTextContent(nameElement, "rank"));

                    System.out.println("Name: " + name);
                    System.out.println("Gender: " + gender);
                    System.out.println("Count: " + count);
                    System.out.println("Rank in their group: " + rank);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
