package laba3.ex4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class BestNames {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введіть етнічну группу: ");
            String ethnicity = scanner.nextLine();

            String fileName = "src/main/java/laba3/Children_Names_NY.xml";
            int topNamesCount = 3;

            List<Child> topNames = getTopNames(fileName, ethnicity, topNamesCount);
            createXMLFile(topNames, "best_names.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Child> getTopNames(String fileName, String ethnicity, int topNamesCount) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        NodeList rows = doc.getElementsByTagName("row");
        List<Child> childList = new ArrayList<>();

        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);
            String ethcty = row.getElementsByTagName("ethcty").item(0).getTextContent();
            if (ethcty.equals(ethnicity)) {
                String name = row.getElementsByTagName("nm").item(0).getTextContent();
                String gender = row.getElementsByTagName("gndr").item(0).getTextContent();
                int count = Integer.parseInt(row.getElementsByTagName("cnt").item(0).getTextContent());
                int rank = Integer.parseInt(row.getElementsByTagName("rnk").item(0).getTextContent());
                childList.add(new Child(name, gender, count, rank));
            }
        }

        Collections.sort(childList, Comparator.comparingInt(Child::getRank));
        List<Child> topNames = new ArrayList<>();
        Set<String> seenNames = new HashSet<>();
        for (Child child : childList) {
            if (topNames.size() >= topNamesCount) {
                break;
            }
            if (!seenNames.contains(child.getName())) {
                topNames.add(child);
                seenNames.add(child.getName());
            }
        }

        return topNames;
    }

    public static void createXMLFile(List<Child> childList, String fileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("best_names");
        doc.appendChild(rootElement);

        for (Child child : childList) {
            Element nameElement = doc.createElement("child");
            rootElement.appendChild(nameElement);

            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(child.getName()));
            nameElement.appendChild(name);

            Element gender = doc.createElement("gender");
            gender.appendChild(doc.createTextNode(child.getGender()));
            nameElement.appendChild(gender);

            Element count = doc.createElement("count");
            count.appendChild(doc.createTextNode(String.valueOf(child.getCount())));
            nameElement.appendChild(count);

            Element rank = doc.createElement("rank");
            rank.appendChild(doc.createTextNode(String.valueOf(child.getRank())));
            nameElement.appendChild(rank);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));
        transformer.transform(source, result);
    }
}
